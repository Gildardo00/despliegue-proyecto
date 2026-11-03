DROP DATABASE IF EXISTS arena_uaa;
CREATE DATABASE arena_uaa CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE arena_uaa;

CREATE TABLE usuario (
    id_usuario     INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    correo         VARCHAR(120) NOT NULL UNIQUE,
    usuario        VARCHAR(50)  NOT NULL UNIQUE,
    contrasena     VARCHAR(255) NOT NULL,
    rol            VARCHAR(20)  NOT NULL DEFAULT 'ADMIN',
    fecha_registro DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE configuracion (
    clave VARCHAR(50)  PRIMARY KEY,
    valor VARCHAR(255) NOT NULL
);

CREATE TABLE palabra_prohibida (
    id_palabra INT AUTO_INCREMENT PRIMARY KEY,
    palabra    VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE juego_disciplina (
    id_juego          INT AUTO_INCREMENT PRIMARY KEY,
    nombre_disciplina VARCHAR(100) NOT NULL,
    regla_puntuacion  VARCHAR(255) NOT NULL,
    tipo_competencia  VARCHAR(30)  NOT NULL DEFAULT 'Eliminacion directa',
    modalidad         VARCHAR(20)  NOT NULL DEFAULT 'Equipos',
    activa            TINYINT(1)   NOT NULL DEFAULT 1
);

CREATE TABLE equipo (
    id_equipo      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_equipo  VARCHAR(100) NOT NULL UNIQUE,
    id_juego       INT          NULL,
    categoria      VARCHAR(40)  NOT NULL DEFAULT 'Libre',
    rama           VARCHAR(20)  NOT NULL DEFAULT 'Mixto',
    seed           INT          NOT NULL DEFAULT 0,
    fecha_registro DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_equipo_juego
        FOREIGN KEY (id_juego) REFERENCES juego_disciplina(id_juego)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE jugador (
    id_jugador      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(120) NOT NULL,
    id_equipo       INT          NULL,
    id_juego        INT          NULL,
    categoria       VARCHAR(40)  NOT NULL DEFAULT 'Libre',
    rama            VARCHAR(20)  NOT NULL DEFAULT 'Mixto',
    edad            INT          NULL,
    CONSTRAINT fk_jugador_equipo
        FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_jugador_juego
        FOREIGN KEY (id_juego) REFERENCES juego_disciplina(id_juego)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE ronda (
    id_ronda     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_ronda VARCHAR(100) NOT NULL,
    estatus      VARCHAR(30)  NOT NULL DEFAULT 'Programada'
);

CREATE TABLE jornada (
    id_jornada     INT AUTO_INCREMENT PRIMARY KEY,
    numero_jornada INT  NOT NULL,
    fecha_inicio   DATE NOT NULL,
    fecha_fin      DATE,
    id_ronda       INT  NOT NULL,
    CONSTRAINT fk_jornada_ronda
        FOREIGN KEY (id_ronda) REFERENCES ronda(id_ronda)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE partido (
    id_partido INT AUTO_INCREMENT PRIMARY KEY,
    sede       VARCHAR(120) NOT NULL,
    id_juego   INT          NOT NULL,
    id_jornada INT          NOT NULL,
    CONSTRAINT fk_partido_juego
        FOREIGN KEY (id_juego) REFERENCES juego_disciplina(id_juego)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_partido_jornada
        FOREIGN KEY (id_jornada) REFERENCES jornada(id_jornada)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE enfrentar (
    id_equipo               INT NOT NULL,
    id_partido              INT NOT NULL,
    condicion               VARCHAR(10) NOT NULL,
    goles_totales_local     INT NOT NULL DEFAULT 0,
    goles_totales_visitante INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id_equipo, id_partido),
    CONSTRAINT fk_enfrentar_equipo
        FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_enfrentar_partido
        FOREIGN KEY (id_partido) REFERENCES partido(id_partido)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE participar (
    id_jugador         INT NOT NULL,
    id_partido         INT NOT NULL,
    numero_p           INT,
    goles_individuales INT       NOT NULL DEFAULT 0,
    partida_ganada     TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id_jugador, id_partido),
    CONSTRAINT fk_participar_jugador
        FOREIGN KEY (id_jugador) REFERENCES jugador(id_jugador)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_participar_partido
        FOREIGN KEY (id_partido) REFERENCES partido(id_partido)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE enfrentamiento (
    id_enfrentamiento INT AUTO_INCREMENT PRIMARY KEY,
    id_juego          INT          NOT NULL,
    categoria         VARCHAR(40)  NOT NULL,
    rama              VARCHAR(20)  NOT NULL,
    formato           VARCHAR(30)  NOT NULL,
    fase              VARCHAR(40)  NOT NULL,
    orden             INT          NOT NULL DEFAULT 0,
    id_equipo_a       INT          NULL,
    id_equipo_b       INT          NULL,
    seed_a            INT          NOT NULL DEFAULT 0,
    seed_b            INT          NOT NULL DEFAULT 0,
    es_bye            TINYINT(1)   NOT NULL DEFAULT 0,
    fecha_generacion  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_enf_juego
        FOREIGN KEY (id_juego) REFERENCES juego_disciplina(id_juego)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_enf_equipo_a
        FOREIGN KEY (id_equipo_a) REFERENCES equipo(id_equipo)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_enf_equipo_b
        FOREIGN KEY (id_equipo_b) REFERENCES equipo(id_equipo)
        ON UPDATE CASCADE ON DELETE SET NULL
);

INSERT INTO configuracion (clave, valor) VALUES
('inscripciones_abiertas', 'true'),
('fecha_cierre_inscripciones', '');

INSERT INTO palabra_prohibida (palabra) VALUES
('idiota'), ('estupido'), ('pendejo'), ('imbecil'),
('mierda'), ('puto'), ('marica'), ('cabron'), ('perra'), ('maldito');

INSERT INTO usuario (nombre, correo, usuario, contrasena, rol) VALUES
('Administrador General', 'admin@arena.uaa.mx',  'admin',  'admin123',  'ADMIN'),
('Emilio Duran',          'emilio@arena.uaa.mx', 'emilio', 'emilio123', 'ADMIN'),
('Staff Arena',           'staff@arena.uaa.mx',  'staff',  'staff123',  'STAFF');

INSERT INTO juego_disciplina (nombre_disciplina, regla_puntuacion, tipo_competencia, modalidad, activa) VALUES
('League of Legends',         'MOBA 5 vs 5. Gana el equipo que destruye primero el Nexo enemigo; el marcador son las partidas ganadas en la serie.', 'Eliminacion directa', 'Equipos',    1),
('Valorant',                  'FPS tactico 5 vs 5. Gana el primer equipo en ganar 13 rondas.',                                                       'Eliminacion directa', 'Equipos',    1),
('Rocket League',             'Futbol con autos 3 vs 3. Gana el equipo con mas goles al terminar el tiempo.',                                        'Round Robin',         'Equipos',    1),
('EA Sports FC 25',           'Simulador de futbol 1 vs 1. Gana el equipo con mas goles en el partido.',                                            'Eliminacion directa', 'Individual', 1),
('Counter-Strike 2',          'FPS por rondas 5 vs 5. Gana el equipo que llegue primero a 13 rondas.',                                              'Eliminacion directa', 'Equipos',    1),
('Super Smash Bros Ultimate', 'Juego de pelea. Gana quien agote las vidas (stocks) del rival.',                                                     'Round Robin',         'Individual', 1);

INSERT INTO equipo (nombre_equipo, id_juego, categoria, rama, seed) VALUES
('Halcones UAA',        1, 'Universitaria', 'Mixto',   1),
('Dragones Digitales',  1, 'Universitaria', 'Mixto',   2),
('Fenix Esports',       1, 'Universitaria', 'Mixto',   3),
('Lobos Ciberneticos',  1, 'Universitaria', 'Mixto',   4),
('Toros Gaming',        1, 'Universitaria', 'Mixto',   5),
('Cohetes UAA',         1, 'Universitaria', 'Mixto',   6),
('Tacticos AGS',        1, 'Universitaria', 'Mixto',   7),
('Guerreros del Valle', 1, 'Universitaria', 'Mixto',   8),
('Nitro Squad',         3, 'Libre',         'Varonil', 1),
('Turbo Aguilas',       3, 'Libre',         'Varonil', 2),
('Boost Brigade',       3, 'Libre',         'Varonil', 3),
('Octano UAA',          3, 'Libre',         'Varonil', 4);

INSERT INTO jugador (nombre_completo, id_equipo, id_juego, categoria, rama, edad) VALUES
('Carlos Mendez',    1, 1, 'Universitaria', 'Varonil', 20),
('Luis Ramirez',     1, 1, 'Universitaria', 'Varonil', 21),
('Diego Torres',     1, 1, 'Universitaria', 'Varonil', 19),
('Pedro Gomez',      2, 1, 'Universitaria', 'Varonil', 22),
('Ivan Rios',        2, 1, 'Universitaria', 'Varonil', 20),
('Sergio Perez',     2, 1, 'Universitaria', 'Varonil', 23),
('Mariana Lopez',    3, 1, 'Universitaria', 'Femenil', 19),
('Daniela Ruiz',     3, 1, 'Universitaria', 'Femenil', 21),
('Oscar Vargas',     4, 1, 'Universitaria', 'Varonil', 22),
('Hugo Medina',      4, 1, 'Universitaria', 'Varonil', 20),
('Roberto Aguilar',  5, 1, 'Universitaria', 'Varonil', 24),
('Emilio Duran',     5, 1, 'Universitaria', 'Varonil', 21),
('Pablo Nunez',      6, 1, 'Universitaria', 'Varonil', 20),
('Emilio Sanchez',   6, 1, 'Universitaria', 'Varonil', 22),
('Victor Herrera',   7, 1, 'Universitaria', 'Varonil', 23),
('Marco Flores',     7, 1, 'Universitaria', 'Varonil', 19),
('Alan Reyes',       8, 1, 'Universitaria', 'Varonil', 21),
('Sofia Castro',     8, 1, 'Universitaria', 'Femenil', 20);

INSERT INTO jugador (nombre_completo, id_equipo, id_juego, categoria, rama, edad) VALUES
('Andres Gallardo',  NULL, 2, 'Universitaria', 'Varonil', 20),
('Bruno Salas',      NULL, 2, 'Universitaria', 'Varonil', 21),
('Cristian Mora',    NULL, 2, 'Universitaria', 'Varonil', 19),
('Diana Ponce',      NULL, 2, 'Universitaria', 'Femenil', 22),
('Erick Lara',       NULL, 2, 'Universitaria', 'Varonil', 23),
('Fernanda Rico',    NULL, 2, 'Universitaria', 'Femenil', 20),
('Gerardo Vela',     NULL, 2, 'Universitaria', 'Varonil', 21),
('Hector Pina',      NULL, 2, 'Universitaria', 'Varonil', 24),
('Itzel Navarro',    NULL, 2, 'Universitaria', 'Femenil', 19),
('Jorge Camacho',    NULL, 2, 'Universitaria', 'Varonil', 22),
('Karla Ibarra',     NULL, 2, 'Universitaria', 'Femenil', 20),
('Leonardo Quezada', NULL, 2, 'Universitaria', 'Varonil', 21);

INSERT INTO ronda (nombre_ronda, estatus) VALUES
('Ronda Regular', 'En curso'),
('Fase Final',    'Programada');

INSERT INTO jornada (numero_jornada, fecha_inicio, fecha_fin, id_ronda) VALUES
(1, '2026-03-01', '2026-03-07', 1),
(2, '2026-03-08', '2026-03-14', 1),
(3, '2026-03-15', '2026-03-21', 1),
(4, '2026-03-22', '2026-03-28', 1),
(5, '2026-03-29', '2026-04-04', 1),
(6, '2026-04-05', '2026-04-09', 2),
(7, '2026-04-10', '2026-04-13', 2),
(8, '2026-04-14', '2026-04-17', 2);

INSERT INTO partido (sede, id_juego, id_jornada) VALUES
('Arena Gallos UAA',        1, 1),
('Sala eSports UAA',        2, 1),
('Centro de Computo CCEA',  5, 1),
('Arena Gallos UAA',        1, 2),
('Laboratorio de Computo',  3, 2),
('Sala eSports UAA',        6, 2),
('Arena Gallos UAA',        1, 3),
('Centro de Computo CCEA',  4, 3),
('Laboratorio de Computo',  2, 3),
('Arena Gallos UAA',        1, 4),
('Sala eSports UAA',        3, 4),
('Auditorio Digital UAA',   5, 4),
('Arena Gallos UAA',        1, 5),
('Sala eSports UAA',        2, 5),
('Centro de Computo CCEA',  6, 5),
('Arena Gallos UAA',        1, 6),
('Auditorio Digital UAA',   2, 6),
('Laboratorio de Computo',  4, 6),
('Arena Gallos UAA',        1, 7),
('Auditorio Digital UAA',   2, 7),
('Sala eSports UAA',        3, 7),
('Centro de Computo CCEA',  5, 7),
('Arena Gallos UAA',        1, 8),
('Auditorio Digital UAA',   2, 8),
('Sala eSports UAA',        4, 8);

INSERT INTO enfrentar (id_equipo, id_partido, condicion, goles_totales_local, goles_totales_visitante) VALUES
(1, 1,  'Local',     2, 1), (2, 1,  'Visitante', 2, 1),
(3, 2,  'Local',     3, 3), (4, 2,  'Visitante', 3, 3),
(5, 3,  'Local',     1, 2), (6, 3,  'Visitante', 1, 2),
(1, 4,  'Local',     3, 1), (3, 4,  'Visitante', 3, 1),
(7, 5,  'Local',     4, 2), (8, 5,  'Visitante', 4, 2),
(2, 6,  'Local',     0, 2), (4, 6,  'Visitante', 0, 2),
(1, 7,  'Local',     2, 0), (5, 7,  'Visitante', 2, 0),
(6, 8,  'Local',     3, 2), (8, 8,  'Visitante', 3, 2),
(2, 9,  'Local',     1, 3), (7, 9,  'Visitante', 1, 3),
(1, 10, 'Local',     1, 1), (4, 10, 'Visitante', 1, 1),
(3, 11, 'Local',     2, 0), (5, 11, 'Visitante', 2, 0),
(6, 12, 'Local',     4, 3), (8, 12, 'Visitante', 4, 3),
(2, 13, 'Local',     2, 2), (1, 13, 'Visitante', 2, 2),
(4, 14, 'Local',     1, 3), (3, 14, 'Visitante', 1, 3),
(5, 15, 'Local',     0, 1), (7, 15, 'Visitante', 0, 1),
(1, 16, 'Local',     3, 0), (4, 16, 'Visitante', 3, 0),
(3, 17, 'Local',     2, 1), (7, 17, 'Visitante', 2, 1),
(2, 18, 'Local',     1, 2), (6, 18, 'Visitante', 1, 2),
(1, 19, 'Local',     2, 1), (3, 19, 'Visitante', 2, 1),
(8, 20, 'Local',     0, 2), (2, 20, 'Visitante', 0, 2),
(5, 21, 'Local',     3, 3), (6, 21, 'Visitante', 3, 3),
(4, 22, 'Local',     1, 2), (7, 22, 'Visitante', 1, 2),
(1, 23, 'Local',     2, 0), (2, 23, 'Visitante', 2, 0),
(3, 24, 'Local',     1, 3), (6, 24, 'Visitante', 1, 3),
(5, 25, 'Local',     2, 1), (7, 25, 'Visitante', 2, 1);

INSERT INTO participar (id_jugador, id_partido, numero_p, goles_individuales, partida_ganada) VALUES
(1, 1,  10, 2, 1), (2, 1,  7,  0, 1), (4, 1,  9,  1, 0), (5, 1,  4,  0, 0),
(11, 3, 5,  0, 0), (13, 3, 11, 1, 1), (14, 3, 8,  1, 1),
(15, 5, 3,  2, 1), (16, 5, 14, 2, 1), (17, 5, 6,  2, 0),
(1, 7,  10, 1, 1), (3, 7,  8,  1, 1), (11, 7, 9,  0, 0),
(13, 8, 9,  2, 1), (14, 8, 5,  1, 1), (17, 8, 7,  1, 0), (18, 8, 4,  1, 0),
(15, 9, 11, 2, 1), (16, 9, 6,  1, 1), (4, 9,  8,  1, 0), (6, 9,  4,  0, 0),
(7, 11, 9,  1, 1), (8, 11, 7,  1, 1), (11, 11, 5, 0, 0), (12, 11, 8, 0, 0),
(13, 12, 11, 3, 1), (14, 12, 8, 1, 1), (17, 12, 6, 1, 0), (18, 12, 4, 2, 0),
(1, 16, 10, 2, 1), (2, 16, 7,  1, 1), (9, 16,  6, 0, 0), (10, 16, 4, 0, 0),
(7, 17, 9,  1, 1), (8, 17, 11, 1, 1), (15, 17, 7, 1, 0), (16, 17, 5, 0, 0),
(1, 19, 10, 2, 1), (3, 19, 8,  0, 1), (7, 19,  9, 1, 0), (8, 19, 11, 0, 0),
(4, 20, 9,  1, 1), (5, 20, 7,  1, 1), (17, 20, 5, 0, 0), (18, 20, 4, 0, 0),
(15, 22, 10, 1, 1), (16, 22, 8, 1, 1), (9, 22, 6, 0, 0), (10, 22, 5, 1, 0),
(1, 23, 10, 2, 1), (2, 23, 7,  0, 1), (4, 23, 9,  0, 0), (5, 23, 4,  0, 0),
(11, 25, 9, 1, 1), (12, 25, 7, 1, 1), (15, 25, 8, 1, 0), (16, 25, 5, 0, 0);
