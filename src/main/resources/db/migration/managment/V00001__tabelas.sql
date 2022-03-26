CREATE TABLE IF NOT EXISTS usuario(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    data_cadastro date default current_date,
    telefone varchar(255) NOT NULL,
    senha varchar(255) NOT NULL,
    is_restaurante boolean default false,
    schema varchar(50),
    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);
