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
	cod_manutencao  INTEGER        NOT NULL,
	descricao       VARCHAR(500)   NOT NULL,
	cod_veiculo     VARCHAR(8)    NOT NULL,
	CONSTRAINT pk_manutencao
		PRIMARY KEY (cod_manutencao),
	CONSTRAINT fk_manutencao_veiculo
		FOREIGN KEY (cod_veiculo)
		REFERENCES veiculo(placa)
);

CREATE TABLE manutencao_servico(
	cod_manutencao INTEGER NOT NULL,
	cod_servico INTEGER NOT NULL,
	CONSTRAINT pk_manutencao_veiculo
		PRIMARY KEY (cod_manutencao, cod_servico),
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
	VALUES ('111.111.111-12', 'Claudio dos Santos', '1986-02-28', 'Cachoeiro de Itapemirim', 'ES');

-- Servicos
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (1, 'Troca de PNEU', 'Realização da troca de um PNEU antigo para um novo', 20.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (2, 'Troca do vidro', 'Troca de um vidro quebrado por um novo', 150.0);
INSERT INTO servico (cod_servico, nome, descricao, preco)
	VALUES (3, 'Revisão Elétrica', 'Revisão da parte elétrica do veículo', 50.0);

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

-- Veiculos
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-AA', 'Chevrolet Tracker', 'Chevrolet', 1, '111.111.111-11');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-AB', 'Ford Territory', 'Ford', 1, '111.111.111-12');
INSERT INTO veiculo (placa, nome, marca, cod_modelo, cod_cliente)
	VALUES ('AAAAA-AC', 'CG 160 Start', 'Honda', 5, '111.111.111-12');

-- Manutençoes
INSERT INTO manutencao(cod_manutencao, descricao, cod_veiculo)
	VALUES (1, 'Troca de PNEU', 'AAAAA-AB');
INSERT INTO manutencao(cod_manutencao, descricao, cod_veiculo)
	VALUES (2, 'Troca do vidro', 'AAAAA-AB');

-- Dados que ligam as tabelas manutencoes e servico
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (1, 1);
INSERT INTO manutencao_servico(cod_manutencao, cod_servico)
	VALUES (1, 2);
