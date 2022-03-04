-- ATIVO
INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1', 'ATIVO', false, 0, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.01', 'ATIVO CIRCULANTE', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.01.01', 'Carteira', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.01.02', 'Banco', false, 2, true);

INSERT INTO portador(nome, plano_id, possui_credito)
VALUES (
           'Carteira',
           (SELECT p.id FROM plano p WHERE p.cod_contabil = '1.01.01'),
           false);

INSERT INTO portador(nome, plano_id, possui_credito)
VALUES (
           'Banco',
           (SELECT p.id FROM plano p WHERE p.cod_contabil = '1.01.02'),
           false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.02', 'ATIVO NÃO CIRCULANTE', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.02.01', 'Investimentos', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.03', 'ATIVO IMOBILIZADO', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('1.03.01', 'Imóveis', false, 2, true);

-- PASSIVO
INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('2', 'PASSIVO', false, 0, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('2.01', 'PASSIVO NÃO CIRCULANTE', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('2.01.01', 'Veículos', false, 2, false);

-- RECEITAS
INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('3', 'RECEITAS', false, 0, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('3.01', 'SALÁRIO', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('3.01.01', 'Salário Liquído', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('3.01.02', 'Horas Extras', false, 3, true);

-- DESPESAS
INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4', 'DESPESAS', false, 0, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.01', 'DESPESAS COM MORADIA', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.01.01', 'Aluguel', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.01.02', 'Condomínio', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.01.03', 'Limpeza', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.01.04', 'Manutenção', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.02', 'DESPESAS COM TRANSPORTE', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.02.01', 'Combustível', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.02.02', 'Aplicativo de Transporte/Táxi', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.02.03', 'Passagens Aéreas', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.02.04', 'Passagens de Ônibus', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.03', 'DESPESAS COM VEÍCULOS', false, 1, false);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.03.01', 'Troca de Óleo', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.03.02', 'Pneus', false, 2, true);

INSERT INTO plano(cod_contabil, nome, listar_dre, nivel, aceita_lancamento)
VALUES ('4.03.03', 'Manutenção em Geral', false, 2, true);
