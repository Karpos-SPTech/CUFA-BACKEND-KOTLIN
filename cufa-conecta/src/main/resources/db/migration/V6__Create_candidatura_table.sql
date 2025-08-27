CREATE TABLE candidatura (
    id_candidatura BIGINT PRIMARY KEY AUTO_INCREMENT,
    fk_usuario BIGINT,
    fk_publicacao BIGINT,
    fk_empresa BIGINT,
    dt_candidatura DATE,
    FOREIGN KEY (fk_usuario) REFERENCES cadastro_usuario(id_usuario),
    FOREIGN KEY (fk_publicacao) REFERENCES publicacao(id_publicacao),
    FOREIGN KEY (fk_empresa) REFERENCES cadastro_empresa(id_empresa)
);