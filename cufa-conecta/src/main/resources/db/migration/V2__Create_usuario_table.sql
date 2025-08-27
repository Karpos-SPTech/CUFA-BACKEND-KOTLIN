CREATE TABLE cadastro_usuario (
  id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(45),
  email VARCHAR(225) UNIQUE,
  senha VARCHAR(200),
  cpf VARCHAR(14),
  telefone VARCHAR(15),
  escolaridade VARCHAR(30),
  dt_nascimento DATE,
  estado_civil VARCHAR(45),
  estado VARCHAR(45),
  cidade VARCHAR(100),
  biografia VARCHAR(400),
  curriculo_url VARCHAR(300)
);