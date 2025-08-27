CREATE TABLE experiencias (
  id_experiencia BIGINT PRIMARY KEY AUTO_INCREMENT,
  fk_usuario BIGINT,
  cargo VARCHAR(100),
  empresa VARCHAR(200),
  dt_inicio VARCHAR(45),
  dt_fim VARCHAR(45),
  FOREIGN KEY (fk_usuario) REFERENCES cadastro_usuario(id_usuario)
);