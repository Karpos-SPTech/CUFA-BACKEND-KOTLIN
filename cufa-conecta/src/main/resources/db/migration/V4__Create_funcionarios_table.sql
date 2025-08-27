CREATE TABLE funcionario (
  id_funcionario BIGINT PRIMARY KEY,
  fk_empresa BIGINT,
  nome VARCHAR(45),
  email VARCHAR(225),
  senha VARCHAR(45),
  cargo VARCHAR(40),
  FOREIGN KEY (fk_empresa) REFERENCES cadastro_empresa(id_empresa)
);
