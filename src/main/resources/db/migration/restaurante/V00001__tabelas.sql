CREATE TABLE IF NOT EXISTS endereco(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    rua varchar(255) NOT NULL,
    numero varchar(255) NOT NULL,
    bairro varchar(255) NOT NULL,
    cep varchar(255),
    CONSTRAINT endereco_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS item(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome varchar(255) NOT NULL,
    preco decimal NOT NULL,
    descricao varchar(512) NOT NULL,
    img_url varchar(512),
    CONSTRAINT item_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pedido(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    data date NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES managment.usuario (id),
    CONSTRAINT pedido_pkey PRIMARY KEY (id)
);
