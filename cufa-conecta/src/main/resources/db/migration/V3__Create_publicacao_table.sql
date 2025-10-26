CREATE TABLE publicacao (
    id_publicacao BIGINT PRIMARY KEY auto_increment,
    fk_empresa BIGINT,
    titulo VARCHAR(45),
    descricao TEXT,
    tipo_contrato VARCHAR(10),
    dt_expiracao DATETIME,
    dt_publicacao DATETIME
);