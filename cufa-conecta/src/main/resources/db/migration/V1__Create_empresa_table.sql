CREATE TABLE cadastro_empresa (
  id_empresa BIGINT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100),
  email VARCHAR(225),
  senha VARCHAR(100),
  cep VARCHAR(9),
  endereco VARCHAR(200),
  numero INT,
  cnpj VARCHAR(18),
  area VARCHAR(100),
  biografia VARCHAR(400),
  dt_cadastro DATE
);