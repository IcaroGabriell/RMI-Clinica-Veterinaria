CREATE DATABASE IF NOT EXISTS clinica_vet;
USE clinica_vet;


CREATE TABLE tutor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)
);


CREATE TABLE animal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especie VARCHAR(50),
    id_tutor INT NOT NULL,
    FOREIGN KEY (id_tutor) REFERENCES tutor(id)
);


CREATE TABLE veterinario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    crmv VARCHAR(20)
);


CREATE TABLE consulta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_consulta DATE NOT NULL,
    id_animal INT NOT NULL,
    id_veterinario INT NOT NULL,
    FOREIGN KEY (id_animal) REFERENCES animal(id),
    FOREIGN KEY (id_veterinario) REFERENCES veterinario(id)
);


CREATE TABLE tratamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(200) NOT NULL,
    id_consulta INT NOT NULL,
    FOREIGN KEY (id_consulta) REFERENCES consulta(id)
);
