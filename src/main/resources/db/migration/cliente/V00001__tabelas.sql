CREATE TABLE IF NOT EXISTS endereco(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    rua varchar(255) NOT NULL,
    numero varchar(255) NOT NULL,
    bairro varchar(255) NOT NULL,
    cep varchar(255),
    CONSTRAINT endereco_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cartao(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome_no_cartao varchar(255) NOT NULL,
    numero varchar(16) NOT NULL,
    vencimento varchar(5) NOT NULL,
    cvc varchar(3) NOT NULL,
    CONSTRAINT cartao_pkey PRIMARY KEY (id)
);
