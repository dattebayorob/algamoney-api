CREATE TABLE lancamento(
	id BIGSERIAL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	id_categoria BIGINT NOT NULL REFERENCES categoria(id),
	id_pessoa BIGINT NOT NULL REFERENCES pessoa(id)
);
INSERT INTO lancamento(descricao,data_vencimento,valor,tipo,id_categoria,id_pessoa)
	VALUES('Apenas um teste de lancamento','2018-09-18',6400.00,'RECEITA','1','1');