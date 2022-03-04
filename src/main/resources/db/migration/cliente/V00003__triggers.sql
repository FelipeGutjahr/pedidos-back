-- ATUALIZA SALDO DUPLICATA INSERT
create or replace function atualiza_saldo_duplicata_insert() returns trigger as $emp_stamp$
declare
    saldo_anterior numeric default 0.0;
begin
	if(new.duplicata_id is not null) then
		saldo_anterior = (select saldo from duplicata where duplicata.id = new.duplicata_id);
        update duplicata
        set saldo = saldo_anterior - new.valor
        where id = new.duplicata_id;
    end if;
    return null;
end;
$emp_stamp$ language plpgsql;

create trigger atualiza_saldo_duplicata_insert after insert on lancamento
for each row execute procedure atualiza_saldo_duplicata_insert();


-- ATUALIZA PLANO SALDO INSERT
CREATE FUNCTION atualiza_plano_saldo_insert() RETURNS TRIGGER AS $emp_stamp$
DECLARE
    nao_possui_lancamento_dia boolean;
	nao_possui_lancamento_anterior boolean;
	saldo_anterior numeric;
BEGIN
	-- validação da conta credito

	/* se o lançamento for um pagamento utiliando o crédito do portador, o saldo do mesmo
	   não deve ser alterado - pagamentos a crédito são faurados após um período (normalmente um mês)
	   e então é gerado uma duplicata a pagar */
	IF(NEW.is_credito = false) THEN

		-- busca o plano_saldo da conta de credito na data do lançamento, e salva na variável se há um registro
		nao_possui_lancamento_dia = (
			SELECT plano_saldo.id FROM plano_saldo
			WHERE plano_saldo.data = NEW.data
			AND plano_saldo.plano_id = NEW.plano_credito_id) is null;

		-- se não há lançamento na conta credito no dia será criado um registro em plano_saldo
		IF(nao_possui_lancamento_dia) THEN

			-- busca no plano_saldo se há algum registro para a conta em uma data anterior a do lançamento
			nao_possui_lancamento_anterior = (
				SELECT MAX(plano_saldo.id) FROM plano_saldo
				WHERE plano_saldo.plano_id = NEW.plano_credito_id
				AND plano_saldo.data < NEW.data) is null;

			/* se não há lançamento em data anterior para a conta, cria um registro
			   com o saldo igual ao do lançamento */
			IF(nao_possui_lancamento_anterior) THEN
				INSERT INTO plano_saldo(data, saldo, plano_id)
				VALUES (NEW.data, (NEW.valor - NEW.valor * 2), NEW.plano_credito_id);

			/* se há lançamento em data anterior para a conta, busca o saldo do registro mais
			   recente e soma com o valor do lançamento */
            ELSE
			    -- busca o plano_saldo mais recente em relação a data do lançamento
                saldo_anterior = (
				    SELECT plano_saldo_1.saldo - (NEW.valor) FROM plano_saldo plano_saldo_1
				    WHERE plano_saldo_1.plano_id = NEW.plano_credito_id
				    AND plano_saldo_1.data = (
					    SELECT MAX(plano_saldo_2.data)
					    FROM plano_saldo plano_saldo_2
					    WHERE plano_saldo_2.plano_id = NEW.plano_credito_id
				    )
                );

                -- insere um registro em plano_saldo com a data do lançamento
                INSERT INTO plano_saldo(data, saldo, plano_id)
                VALUES (NEW.data, saldo_anterior, NEW.plano_credito_id);
            END IF;

		    /* depois de inserir o registro atualiza o saldo dos registros que tem
		        data maior que a do lançamento, adicionando o valor do lançamento ao saldo */
            UPDATE plano_saldo
            SET saldo = saldo - NEW.valor
            WHERE plano_saldo.plano_id = NEW.plano_credito_id
            AND plano_saldo.data > NEW.data;

            /* caso haja um registro nesse dia, ele será atualizado juntamente com os registros
                com data superior ao lançamento */
        ELSE
            UPDATE plano_saldo
            SET saldo = saldo - NEW.valor
            WHERE plano_saldo.plano_id = NEW.plano_credito_id
            AND plano_saldo.data >= NEW.data;
        END IF;

	    -- validação da conta debito

        -- busca o plano_saldo da conta de debito na data do lançamento, e salva na variável se há um registro
        nao_possui_lancamento_dia = (
		    SELECT id FROM plano_saldo
		    WHERE plano_saldo.data = NEW.data
            AND plano_saldo.plano_id = NEW.plano_debito_id) is null;

        -- se não há lançamento na conta credito no dia será criado um registro em plano_saldo
        IF(nao_possui_lancamento_dia) THEN

		    -- busca o plano_saldo da conta de debito na data do lançamento, e salva na variável se há um registro
		    nao_possui_lancamento_anterior = (
			    SELECT MAX(plano_saldo.id) FROM plano_saldo
			    WHERE plano_saldo.plano_id = NEW.plano_debito_id
			    AND plano_saldo.data < NEW.data
		    ) is null;

		    /* se não há lançamento em data anterior para a conta, cria um registro
                com o saldo igual ao do lançamento */
		    IF(nao_possui_lancamento_anterior) THEN
			    INSERT INTO plano_saldo(data, saldo, plano_id)
			    VALUES (NEW.data, NEW.valor, NEW.plano_debito_id);

		    /* se há lançamento em data anterior para a conta, busca o saldo do registro mais
                recente e soma com o valor do lançamento */
            ELSE
	            saldo_anterior = (
			        SELECT plano_saldo_1.saldo + (NEW.valor) FROM plano_saldo plano_saldo_1
			        WHERE plano_saldo_1.plano_id = NEW.plano_debito_id
			        AND plano_saldo_1.data = (
				        SELECT MAX(plano_saldo_2.data)
				        FROM plano_saldo plano_saldo_2
				        WHERE plano_saldo_2.plano_id = NEW.plano_debito_id
			        )
		        );

                INSERT INTO plano_saldo(data, saldo, plano_id)
                VALUES (NEW.data, saldo_anterior, NEW.plano_debito_id);
            END IF;

            /* depois de inserir o registro atualiza o saldo dos registros que tem
                data maior que a do lançamento, adicionando o valor do lançamento ao saldo */
            UPDATE plano_saldo
            SET saldo = saldo + NEW.valor
            WHERE plano_saldo.plano_id = NEW.plano_debito_id
            AND plano_saldo.data > NEW.data;

            /* caso haja um registro nesse dia, ele será atualizado juntamente com os registros
                com data superior ao lançamento */
        ELSE
            UPDATE plano_saldo
            SET saldo = saldo + NEW.valor
            WHERE plano_saldo.plano_id = NEW.plano_debito_id
            AND plano_saldo.data >= NEW.data;
        END IF;
    END IF;
    return null;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER atualiza_plano_saldo_insert AFTER INSERT ON lancamento
FOR EACH ROW EXECUTE PROCEDURE atualiza_plano_saldo_insert();


-- ATUALIZA PLANO SALDO PAI INSERT
CREATE FUNCTION atualiza_plano_saldo_pai_insert() RETURNS TRIGGER AS $emp_stamp$
DECLARE
    qtd_planos int;
	plano_atual int;
	x int;
	nao_possui_lancamento_dia boolean;
	nao_possui_lancamento_anterior boolean;
	saldo_anterior numeric;
BEGIN
	qtd_planos = (
		SELECT COUNT(plano_pai.plano_pai_id)
		FROM plano_pai
		JOIN plano ON plano.id = plano_pai.plano_id
		WHERE plano.id = NEW.plano_credito_id);

    x = 0;
	WHILE x < qtd_planos LOOP
		plano_atual = (
			SELECT plano_pai.plano_pai_id
			FROM plano_pai
			JOIN plano
			ON plano.id = plano_pai.plano_id
			WHERE plano.id = NEW.plano_credito_id
            LIMIT 1
            OFFSET x
        );

        /* se o lançamento for um pagamento utiliando o crédito do portador, o saldo do mesmo
		   não deve ser alterado - pagamentos a crédito são faurados após um período (normalmente um mês)
		   e então é gerado uma duplicata a pagar */
        IF(NEW.is_credito = false) THEN

			nao_possui_lancamento_dia = (
				SELECT plano_saldo.id FROM plano_saldo
				WHERE plano_saldo.data = NEW.data
				AND plano_saldo.plano_id = plano_atual
			) is null;

            -- se não há lançamento na conta credito no dia será criado um registro em plano_saldo
            IF(nao_possui_lancamento_dia) THEN

				-- verifica se há lançamento em data anterior na conta
				nao_possui_lancamento_anterior = (
					SELECT MAX(plano_saldo.id) FROM plano_saldo
					WHERE plano_saldo.plano_id = plano_atual
					AND plano_saldo.data < NEW.data
				) is null;

				/* se não há lançamento em data anterior para a conta, cria um registro
				   com o saldo igual ao do lançamento */
				IF(nao_possui_lancamento_anterior) THEN
					INSERT INTO plano_saldo(data, saldo, plano_id)
					VALUES (NEW.data, (NEW.valor - NEW.valor * 2), plano_atual);

				/* se há lançamento em data anterior para a conta, busca o saldo do registro mais
				   recente e soma com o valor do lançamento */
                ELSE
				    saldo_anterior = (
						SELECT plano_saldo_1.saldo - (NEW.valor) FROM plano_saldo plano_saldo_1
						WHERE plano_saldo_1.plano_id = plano_atual
						AND plano_saldo_1.data = (
							SELECT MAX(plano_saldo_2.data)
							FROM plano_saldo plano_saldo_2
							WHERE plano_saldo_2.plano_id = plano_atual
						)
					);
                    INSERT INTO plano_saldo(data, saldo, plano_id)
                    VALUES (NEW.data, saldo_anterior, plano_atual);
                END IF;

                /* depois de inserir o registro atualiza o saldo dos registros que tem
				    data maior que a do lançamento, adicionando o valor do lançamento ao saldo */
                UPDATE plano_saldo
                SET saldo = saldo - NEW.valor
                WHERE plano_saldo.plano_id = plano_atual
                AND plano_saldo.data > NEW.data;

                /* caso haja um registro nesse dia, ele será atualizado juntamente com os registros
                    com data superior ao lançamento */
            ELSE
                UPDATE plano_saldo
                SET saldo = saldo - NEW.valor
                WHERE plano_saldo.plano_id = plano_atual
                AND plano_saldo.data >= NEW.data;
            END IF;
        END IF;
        x = x+1;
    END LOOP;

   	qtd_planos = (
		SELECT COUNT(plano_pai.plano_pai_id)
		FROM plano_pai
		JOIN plano ON plano.id = plano_pai.plano_id
		WHERE plano.id = NEW.plano_debito_id);

    x = 0;
    WHILE x < qtd_planos LOOP
		plano_atual = (
			SELECT plano_pai.plano_pai_id
			FROM plano_pai
			JOIN plano
			ON plano.id = plano_pai.plano_id
			WHERE plano.id = NEW.plano_debito_id
            LIMIT 1
            OFFSET x
        );

        nao_possui_lancamento_dia = (
			SELECT id FROM plano_saldo
			WHERE plano_saldo.data = NEW.data
			AND plano_saldo.plano_id = plano_atual
		) is null;

		-- se não há lançamento na conta credito no dia será criado um registro em plano_saldo
		IF(nao_possui_lancamento_dia) THEN

			nao_possui_lancamento_anterior = (
				SELECT MAX(plano_saldo.id) FROM plano_saldo
				WHERE plano_saldo.plano_id = plano_atual
				AND plano_saldo.data < NEW.data
			) is null;

		    /* se não há lançamento em data anterior para a conta, cria um registro
                com o saldo igual ao do lançamento */
		    IF(nao_possui_lancamento_anterior) THEN
			    INSERT INTO plano_saldo(data, saldo, plano_id)
			    VALUES (NEW.data, NEW.valor, plano_atual);

		        /* se há lançamento em data anterior para a conta, busca o saldo do registro mais
                    recente e soma com o valor do lançamento */
            ELSE
			    saldo_anterior = (
				    SELECT plano_saldo_1.saldo + (NEW.valor) FROM plano_saldo plano_saldo_1
				    WHERE plano_saldo_1.plano_id = plano_atual
				    AND plano_saldo_1.data = (
					    SELECT MAX(plano_saldo_2.data)
					    FROM plano_saldo plano_saldo_2
					    WHERE plano_saldo_2.plano_id = plano_atual
				    )
			    );

                INSERT INTO plano_saldo(data, saldo, plano_id)
                VALUES (NEW.data, saldo_anterior, plano_atual);
            END IF;

            /* depois de inserir o registro atualiza o saldo dos registros que tem
                data maior que a do lançamento, adicionando o valor do lançamento ao saldo */
            UPDATE plano_saldo
            SET saldo = saldo + NEW.valor
            WHERE plano_saldo.plano_id = plano_atual
            AND plano_saldo.data > NEW.data;

            /* caso haja um registro nesse dia, ele será atualizado juntamente com os registros
                com data superior ao lançamento */
        ELSE
            UPDATE plano_saldo
            SET saldo = saldo + NEW.valor
            WHERE plano_saldo.plano_id = plano_atual
            AND plano_saldo.data >= NEW.data;
        END IF;
        x = x+1;
    END LOOP;
    return null;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER atualiza_plano_saldo_pai_insert AFTER INSERT ON lancamento
FOR EACH ROW EXECUTE PROCEDURE atualiza_plano_saldo_pai_insert();


-- ATUALIZA PLANO SALDO DELETE
CREATE FUNCTION atualiza_plano_saldo_delete() RETURNS TRIGGER AS $emp_stamp$
BEGIN
	-- validação da conta credito

    -- se o lançamento era um pagamento em crédito, o saldo da conta crédito não deve sofrer alteração
	IF(OLD.is_credito = true) THEN

		-- atualiza o plano_saldo da conta credito na data do lançamento e nas datas futuras
        UPDATE plano_saldo
        SET saldo = saldo + OLD.valor
        WHERE plano_saldo.plano_id = OLD.plano_credito_id
        AND plano_saldo.data >= OLD.data;

        -- atualiza o plano_saldo das contas pai
        UPDATE plano_saldo
        SET saldo = saldo + OLD.valor
        WHERE plano_saldo.plano_id in (
            SELECT plano_pai.plano_pai_id
            FROM plano_pai
            WHERE plano_pai.plano_id = OLD.plano_credito_id)
        AND plano_saldo.data >= OLD.data;
    END IF;

    -- validação da conta débito

    -- atualiza o plano_saldo da conta debito na data do lançamento e nas datas futuras
    UPDATE plano_saldo
    SET saldo = saldo - OLD.valor
    WHERE plano_saldo.plano_id = OLD.plano_debito_id
    AND plano_saldo.data >= OLD.data;

    -- atualiza o plano_saldo das contas pai
    UPDATE plano_saldo
    SET saldo = saldo - OLD.valor
    WHERE plano_saldo.plano_id in (
        SELECT plano_pai.plano_pai_id
        FROM plano_pai
        WHERE plano_pai.plano_id = OLD.plano_debito_id)
    AND plano_saldo.data >= OLD.data;
    return null;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER atualiza_plano_saldo_delete AFTER DELETE ON lancamento
FOR EACH ROW EXECUTE PROCEDURE atualiza_plano_saldo_delete();


-- ATUALIZA SALDO DUPLICATA DELETE
CREATE FUNCTION atualiza_saldo_duplicata_delete() RETURNS TRIGGER AS $emp_stamp$
BEGIN
	-- se o lançamento possuí uma duplicata referenciada, é alterado o saldo da duplicata
    IF(OLD.duplicata_id is not null) THEN
        UPDATE duplicata
        SET saldo = saldo + OLD.valor
        WHERE duplicata.id = OLD.duplicata_id;
    END IF;
    return null;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER atualiza_saldo_duplicata_delete AFTER DELETE ON lancamento
FOR EACH ROW EXECUTE PROCEDURE atualiza_saldo_duplicata_delete();


-- INSERT PLANO PAI
CREATE FUNCTION insert_plano_pai() RETURNS TRIGGER AS $emp_stamp$
DECLARE
    x int;
	plano_atual int;
BEGIN
	-- se o nível for 0, é uma conta que não possuí conta pai -- ATIVO/PASSIVO
    IF(NEW.nivel > 0) THEN

        -- é inserido o primeiro registro de conta pai, para qual grupo a conta pertence -- ATIVO/PASSIVO
		INSERT INTO plano_pai(plano_id, plano_pai_id)
		VALUES(NEW.id, (SELECT id FROM plano WHERE plano.cod_contabil = SUBSTRING(NEW.cod_contabil from 1 for 1)));

        /* se o nível da conta for superior a 1, ela é uma conta para lançamento,
		    conta filha de ATIVO CIRCULANTE/PASSIVO CIRCULANTE */
        IF(NEW.nivel > 1) THEN
			x = 1;
			WHILE x < NEW.nivel LOOP
			    plano_atual = (SELECT id FROM plano WHERE plano.cod_contabil = SUBSTRING(NEW.cod_contabil from 1 for (x * 3) + 1));
                INSERT INTO plano_pai(plano_id, plano_pai_id)
                VALUES(NEW.id, plano_atual);
                x = x+1;
            END LOOP;
        END IF;
    END IF;
    return null;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER insert_plano_pai AFTER INSERT ON plano
FOR EACH ROW EXECUTE PROCEDURE insert_plano_pai();


-- UPDATE PLANO PAI
CREATE FUNCTION update_plano_pai() RETURNS TRIGGER AS $emp_stamp$
DECLARE
    x int;
	plano_atual int;
BEGIN
	IF(NEW.cod_contabil <> OLD.cod_contabil) THEN

        DELETE FROM plano_pai
        WHERE plano_pai.plano_id = OLD.id;

        -- se o nível for 0, é uma conta que não possuí conta pai -- ATIVO/PASSIVO
        IF(NEW.nivel > 0) THEN

			-- é inserido o primeiro registro de conta pai, para qual grupo a conta pertence -- ATIVO/PASSIVO
			INSERT INTO plano_pai(plano_id, plano_pai_id)
			VALUES(NEW.id, (SELECT id FROM plano WHERE plano.cod_contabil = SUBSTRING(NEW.cod_contabil from 1 for 1)));

			/* se o nível da conta for superior a 1, ela é uma conta para lançamento,
			conta filha de ATIVO CIRCULANTE/PASSIVO CIRCULANTE */
			IF(NEW.nivel > 1) THEN
				x = 1;
				WHILE x < NEW.nivel LOOP
					plano_atual = (SELECT id FROM plano WHERE plano.cod_contabil = SUBSTRING(NEW.cod_contabil from 1 for (x * 3) + 1));
                    INSERT INTO plano_pai(plano_id, plano_pai_id)
                    VALUES(NEW.id, plano_atual);
                    x = x+1;
                END LOOP;
            END IF;
        END IF;
    END IF;
    return null;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER update_plano_pai AFTER UPDATE ON plano
FOR EACH ROW EXECUTE PROCEDURE update_plano_pai();
