CREATE TABLE IF NOT EXISTS endereco(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    rua varchar(255) NOT NULL,
    numero varchar(255) NOT NULL,
    bairro varchar(255) NOT NULL,
    cep varchar(255),
    CONSTRAINT endereco_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS item(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome varchar(255) NOT NULL,
    preco decimal NOT NULL,
    descricao varchar(512) NOT NULL,
    img_url varchar(512),
    CONSTRAINT item_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cartao(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome_no_cartao varchar(255) NOT NULL,
    numero varchar(16) NOT NULL,
    data_vencimento date,
    cvc varchar(3),
    CONSTRAINT cartao_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ajustes(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    is_restaurante boolean not null default false,
    CONSTRAINT ajustes_pkey PRIMARY KEY (id)
);
