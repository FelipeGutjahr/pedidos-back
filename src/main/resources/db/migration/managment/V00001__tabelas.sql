CREATE TABLE IF NOT EXISTS usuario(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    data_cadastro date default current_date,
    telefone varchar(255) NOT NULL,
    senha varchar(255) NOT NULL,
    schema varchar(50),
    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);