CREATE TABLE usuario(
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
	--id_permissao BIGINT NOT NULL REFERENCES permissao ON(
		--usuario.id_permissao = permissao.id
	--)
);
CREATE TABLE permissao(
	id BIGSERIAL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
);
CREATE TABLE usuario_permissao(
	id_usuario BIGINT NOT NULL REFERENCES usuario(id),
	id_permissao BIGINT NOT NULL REFERENCES permissao(id)
);
INSERT INTO permissao(descricao) VALUES('ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao(descricao) VALUES('ROLE_PESQUISAR_CATEGORIA');
INSERT INTO permissao(descricao) VALUES('ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao(descricao) VALUES('ROLE_REMOVER_PESSOA');
INSERT INTO permissao(descricao) VALUES('ROLE_PESQUISAR_PESSOA');
INSERT INTO permissao(descricao) VALUES('ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao(descricao) VALUES('ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao(descricao) VALUES('ROLE_PESQUISAR_LANCAMENTO');

--Senha admin encodada com Bcrypt
INSERT INTO usuario(nome,email,senha) VALUES(
	'Administrador','admin@dtb.com','$2a$10$EEum9QQniz9XYO7XPF3oTeSyjtURaBh9FmedRtjWfGbOGgj.R/q5i'
);

--Senha qwe123 encodada com Bcrypt
INSERT INTO usuario(nome,email,senha) VALUES(
	'Robson William','robson.william@dtb.com','$2a$10$znh2rY.6JRK8kRqnb6fUyOO3HoCpybkyAYsm1xOo6LdSVQKdRZsFW'
);
--Usuario admin tem todas as permissoes
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,1);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,2);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,3);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,4);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,5);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,6);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,7);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,8);

--Usuario robson.william sem moral

INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(2,2);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(2,5);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(2,8);