CREATE TABLE duplicata_lancamento_fixo(
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
	valor NUMERIC NOT NULL,
	pessoa_id BIGINT NOT NULL,
	is_credito BOOLEAN DEFAULT FALSE,
	plano_credito_id BIGINT NOT NULL,
	plano_debito_id BIGINT NOT NULL,
	is_duplicata BOOLEAN DEFAULT FALSE,
	is_duplicata_receber BOOLEAN DEFAULT FALSE,
	portador_duplicata_id BIGINT,
	dias_vencimento_duplicata INT,
	ultimo_mes_gerado INT,
	CONSTRAINT duplicata_lancamento_fixo_pkey PRIMARY KEY (id),
	CONSTRAINT fk_duplicata_lancamento_fixo_pessoa FOREIGN KEY (pessoa_id)
	REFERENCES pessoa(id),
	CONSTRAINT fk_duplicata_lancamento_fixo_plano_credito FOREIGN KEY (plano_credito_id)
	REFERENCES plano(id),
	CONSTRAINT fk_duplicata_lancamento_fixo_plano_debito FOREIGN KEY (plano_debito_id)
	REFERENCES plano(id),
	CONSTRAINT fk_duplicata_lancamento_fixo_portador FOREIGN KEY (portador_duplicata_id)
	REFERENCES portador(id)
);