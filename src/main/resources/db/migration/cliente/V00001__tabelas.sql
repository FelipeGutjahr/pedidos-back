CREATE TABLE IF NOT EXISTS ajustes(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    primeiro_dia_mes integer DEFAULT 1,
    CONSTRAINT ajustes_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pessoa(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS plano(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    cod_contabil character varying(255) COLLATE pg_catalog."default" NOT NULL,
    listar_dre boolean NOT NULL DEFAULT false,
    nivel int NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    aceita_lancamento boolean NOT NULL DEFAULT true,
    CONSTRAINT plano_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS plano_saldo(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    plano_id bigint NOT NULL,
    data date NOT NULL,
    saldo numeric NOT NULL DEFAULT 0.00,
    CONSTRAINT plano_saldo_pkey PRIMARY KEY (id),
    CONSTRAINT fk_plano_saldo_plano FOREIGN KEY (plano_id)
    REFERENCES plano (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS portador(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    limite numeric DEFAULT 0.00,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    dia_fechamento_fatura integer NOT NULL DEFAULT 1,
    possui_credito boolean DEFAULT false,
    plano_id bigint NOT NULL,
    CONSTRAINT portador_pkey PRIMARY KEY (id),
    CONSTRAINT fk_portador_plano FOREIGN KEY (plano_id)
    REFERENCES plano (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS duplicata(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    data_inclusao date NOT NULL DEFAULT CURRENT_DATE,
    data_vencimento date DEFAULT CURRENT_DATE,
    observacao text COLLATE pg_catalog."default" NOT NULL,
    valor numeric NOT NULL DEFAULT 0.00,
    saldo numeric NOT NULL DEFAULT 0.00,
    is_receber boolean NOT NULL DEFAULT true,
    contabilizar_inclusao boolean NOT NULL DEFAULT false,
    ano_vencimento_duplicata integer,
    mes_vencimento_duplicata integer,
    plano_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    portador_id bigint NOT NULL,
    CONSTRAINT duplicata_pkey PRIMARY KEY (id),
    CONSTRAINT fk_duplicata_pessoa FOREIGN KEY (pessoa_id)
    REFERENCES pessoa (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_duplicata_plano FOREIGN KEY (plano_id)
    REFERENCES plano (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_duplicata_portador FOREIGN KEY (portador_id)
    REFERENCES portador (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS lancamento(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    data date NOT NULL DEFAULT CURRENT_DATE,
    historico text COLLATE pg_catalog."default" NOT NULL,
    valor numeric NOT NULL DEFAULT 0.00,
    ano_lancamento integer NOT NULL,
    mes_lancamento integer NOT NULL,
    is_credito boolean NOT NULL DEFAULT false,
    is_faturado boolean NOT NULL DEFAULT false,
    plano_credito_id bigint NOT NULL,
    plano_debito_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    duplicata_id bigint,
    CONSTRAINT lancamento_pkey PRIMARY KEY (id),
    CONSTRAINT fk_lancamento_duplicata FOREIGN KEY (duplicata_id)
    REFERENCES duplicata (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_lancamento_pessoa FOREIGN KEY (pessoa_id)
    REFERENCES pessoa (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_lancamento_plano_credito_plano FOREIGN KEY (plano_credito_id)
    REFERENCES plano (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_lancamento_plano_debito_plano FOREIGN KEY (plano_credito_id)
    REFERENCES plano (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS plano_pai(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    plano_id bigint,
    plano_pai_id bigint,
    CONSTRAINT plano_pai_pkey PRIMARY KEY (id),
    CONSTRAINT fk_plano_pai_plano FOREIGN KEY (plano_id)
    REFERENCES plano (id),
    CONSTRAINT fk_plano_pai_plano_pai FOREIGN KEY (plano_pai_id)
    REFERENCES plano_pai (id)
);

INSERT INTO ajustes(primeiro_dia_mes) VALUES (1);