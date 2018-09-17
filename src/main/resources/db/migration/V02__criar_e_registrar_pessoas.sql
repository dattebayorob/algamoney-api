CREATE TABLE pessoa(
	id BIGSERIAL PRIMARY KEY,
	nome varchar(30) NOT NULL,
	ativo BOOLEAN NOT NULL,
	logradouro varchar(25),
	numero int,
	complemento varchar(50),
	bairro varchar(15),
	cep varchar(9),
	cidade varchar(15),
	estado varchar(2)
);

INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
		VALUES('Nome do Cidadão',true,'Rua rua','100','Torre torre, Apto apto','bairro','00000-000','cidade','ES');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
		VALUES('Nome do Cidadão 2',true,'Rua rua','100','Torre torre, Apto apto','bairro','00000-000','cidade','ES');
INSERT INTO pessoa(nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
		VALUES('Nome do Cidadão 3',true,'Rua rua','100','Torre torre, Apto apto','bairro','00000-000','cidade','ES');