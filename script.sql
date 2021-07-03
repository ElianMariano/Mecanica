-- Criação das tabelas
CREATE TABLE cliente(
	cpf             VARCHAR(14)    NOT NULL,
	nome            VARCHAR(100)   NOT NULL,
	nascimento      DATE           NOT NULL,
	cidade          VARCHAR(100)   NOT NULL,
	uf              VARCHAR(2)     NOT NULL,
	CONSTRAINT pk_cliente
		PRIMARY KEY (cpf)
);

CREATE TABLE servico(
	cod_servico     INTEGER        NOT NULL,
	nome            VARCHAR(100)   NOT NULL,
	descricao       VARCHAR(500)   NOT NULL,
	preco           NUMERIC(8, 2)  NOT NULL,
	CONSTRAINT pk_servico
		PRIMARY KEY (cod_servico)
);

CREATE TABLE modelo_veiculo(
	cod_modelo      INTEGER       NOT NULL,
	moto            BOOLEAN       NOT NULL,
	nome            VARCHAR(100)  NOT NULL,
	descricao       VARCHAR(500)  NOT NULL,
	CONSTRAINT pk_modelo_veiculo
		PRIMARY KEY (cod_modelo)
);

CREATE TABLE veiculo(
	placa           VARCHAR(8)   NOT NULL,
	nome            VARCHAR(100)  NOT NULL,
	marca           VARCHAR(100)  NOT NULL,
	cod_modelo      INTEGER       NOT NULL,
	cod_cliente     VARCHAR(14)   NOT NULL,
	CONSTRAINT pk_placa
		PRIMARY KEY (placa),
	CONSTRAINT fk_veiculo_modeloveiculo
		FOREIGN KEY (cod_modelo)
		REFERENCES modelo_veiculo(cod_modelo),
	CONSTRAINT fk_veiculo_cliente
		FOREIGN KEY (cod_cliente)
		REFERENCES cliente(cpf)
);

CREATE TABLE manutencao(
	cod_manutencao  SERIAL			NOT NULL,
	descricao		VARCHAR(500)	NOT NULL,
	dia				DATE			NOT NULL,
	inicio			TIME			NOT NULL,
	fim				TIME			NOT NULL,
	cod_veiculo     VARCHAR(8)		NOT NULL,
	CONSTRAINT pk_manutencao
		PRIMARY KEY (cod_manutencao),
	CONSTRAINT fk_manutencao_veiculo
		FOREIGN KEY (cod_veiculo)
		REFERENCES veiculo(placa),
	CONSTRAINT erro_horario
		CHECK (inicio < fim)
);

CREATE TABLE manutencao_servico(
	codigo_ms		SERIAL			NOT NULL,
	cod_manutencao 	INTEGER 		NOT NULL,
	cod_servico		INTEGER			NOT NULL,
	CONSTRAINT pk_manutencao_veiculo
		PRIMARY KEY (codigo_ms),
	CONSTRAINT fk_manutencao_mv
		FOREIGN KEY (cod_manutencao)
		REFERENCES manutencao(cod_manutencao),
	CONSTRAINT fk_servico_mv
		FOREIGN KEY (cod_servico)
		REFERENCES servico(cod_servico)
);

-- Inserção dos Dados
-- Clientes
INSERT INTO cliente (cpf, nome, nascimento, cidade, uf)
	VALUES ('111.111.111-11', 'João da Silva', '1998-04-05', 'Cachoeiro de Itapemirim', 'ES');
INSERT INTO cliente (cpf, nome, nascimento, cidade, uf)
	VALUES ('111.111.111-12', 'Claudio dos Santos', '1986-04-13', 'Vila Velha', 'ES');
INSERT INTO cliente (cpf, nome, nascimento, cidade, uf)
	VALUES ('111.111.111-22', 'Maria Sousa', '1987-09-29', 'Vitória', 'ES');
INSERT INTO cliente (cpf, nome, nascimento, cidade, uf)
	VALUES ('111.111.111-33', 'Paulo Ferreira', '1994-02-25', 'Marataízes', 'ES');

-- Servicos
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (1, 'Troca de PNEU', 'Realização da troca de um PNEU antigo para um novo', 20.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (2, 'Troca do vidro', 'Troca de um vidro quebrado por um novo', 150.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (3, 'Revisão Elétrica', 'Revisão da parte elétrica do veículo', 50.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (4, 'Troca de Bateria', 'Troca da bateria do veículo', 200.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (5, 'Conserto do Câmbio', 'Conserto do Câmbio do veículo', 265.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (6, 'Troca da Vela da moto', 'Troca de uma vela que apresenta falhas por uma nova', 328.0);

-- Modelos veículo
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (1, 'false', 'SUV', 'Estilo esportivo e com muitas utilidades');
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (2, 'false', 'Esportivo', 'Carro pequeno, veloz e ágil');
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (3, 'false', 'Conversivel', 'Carros com teto removível');
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (4, 'true', 'Custom', 'Motos para o público que gosta de viajar em estradas');
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (5, 'true', 'Street', 'Motos para as tarefas do dia a dia');
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (6, 'true', 'Big Trail', 'Moto de trilha');
INSERT INTO modelo_veiculo (cod_modelo, moto, nome, descricao)
	VALUES (7, 'true', 'Naked', 'Média ou alta cilindrada e sem carenagem');

-- Veiculos
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-AA', 'Chevrolet Tracker', 'Chevrolet', 1, '111.111.111-11');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-AB', 'Ford Territory', 'Ford', 1, '111.111.111-12');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-AC', 'CG 160 Start', 'Honda', 5, '111.111.111-33');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-DD', 'Chery Tiggo 2', 'Chery', 1, '111.111.111-12');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('GGGGG-DD', 'BMW Série 4', 'BMW', 3, '111.111.111-22');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('LLLLL-DD', 'Honda CB Twister', 'Honda', 7, '111.111.111-33');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('PPPPP-DD', 'Dafra Kansas 250', 'Honda', 5, '111.111.111-33');

-- Manutençoes
INSERT INTO manutencao(descricao, dia, inicio, fim, cod_veiculo)
	VALUES ('Troca de PNEU e vidro', '2020-06-15', '9:00', '10:30', 'AAAAA-AB');
INSERT INTO manutencao(descricao, dia, inicio, fim, cod_veiculo)
	VALUES ('Revisão Elétrica', '2020-07-04', '14:30', '15:15', 'LLLLL-DD');
INSERT INTO manutencao(descricao, dia, inicio, fim, cod_veiculo)
	VALUES ('Troca da vela e revisão elétrica', '2020-06-01', '16:00', '16:40', 'AAAAA-DD');
INSERT INTO manutencao(descricao, dia, inicio, fim, cod_veiculo)
	VALUES ('Troca da Bateria', '2020-08-29', '11:30', '14:15', 'GGGGG-DD');
INSERT INTO manutencao(descricao, dia, inicio, fim, cod_veiculo)
	VALUES ('Troca do PNEU e Vela', '2020-09-14', '14:30', '15:00', 'PPPPP-DD');

-- Dados que ligam as tabelas manutencoes e servico
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (1, 1);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (1, 2);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (2, 3);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (3, 6);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (3, 3);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (4, 4);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (5, 6);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (5, 1);
