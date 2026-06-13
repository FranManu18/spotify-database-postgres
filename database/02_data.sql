-- INSERTAR USUARIOS
INSERT INTO Usuario (ID, Nombre, Seguidores) VALUES
(1, 'Juan Pérez', 10),
(2, 'Drake(Fan)', 50),
(3, 'Estudiante', 500);

INSERT INTO Usuario (Nombre, Seguidores) VALUES
('Francisco', 1000); --ID=4 (AUTOINCREMENTAL)


-- INSERTAR ARTISTAS
INSERT INTO Artista (ID, Nombre, Oyentes, Verificado, Cancion_Mas_Escuchada, Seguidores) VALUES
(1, 'Drake', 8000000, TRUE, NULL, 150000),
(2, 'Bad Bunny', 5000000, TRUE, NULL, 3000000),
(3, 'Banda De Garage', 50, FALSE, NULL, 12),
(4, 'Artista Fantasma', 0, FALSE, NULL, 0);

-- INSERTAR CANCIONES
INSERT INTO Cancion (Nombre, ID_Artista, Reproducciones, Duracion, Portada) VALUES
('MIA', 2, 1200000, '00:03:30', 'http://imagen1.jpg'),
('One dance', 1, 600000, '00:02:53', 'http://imagen2.jpg'),
('Summers Over Interlude', 1, 40000, '00:01:46', 'http://imagen3.jpg'),
('Tema de Garage', 3, 15, '00:04:10', 'http://imagen4.jpg'),
('DtMF', 2, 300000, '00:03:57', 'http://imagen5.jpg'),
('NUEVAYoL', 2, 600000, '00:03:03', 'http://imagen6.jpg'),
('Gently', 1, 700000, '00:03:24', 'http://imagen6.jpg');


-- INSERTAR LISTAS DE REPRODUCCIÓN
INSERT INTO Lista_Reproduccion (ID, ID_Usuario, Nombre, Publica, Aleatorio, Descripcion,Fecha_Creacion) VALUES
(1, 1, 'Canciones 2026', TRUE, FALSE, 'Para escuchar programando','01/12/24'),
(2, 2, 'Para mi', FALSE, TRUE, 'Privada','01/12/23')
(3, 3, 'Solo Bad bunny', TRUE, FALSE, '');


-- ASOCIAR CANCIONES A LAS LISTAS (Lista_Cancion)
INSERT INTO Lista_Cancion (ID_Lista, ID_Usuario, Nombre_cancion, ID_Artista) VALUES
(1, 1, 'One dance', 1),
(1, 1, 'MIA', 2),
(2, 2, 'Tema de Garage', 3),
(3, 3, 'DtMF', 2),
(3, 3, 'NUEVAYoL', 2),
(3, 3, 'MIA', 2);

-- INSERTAR COLABORACIÓN (Feat_Cancion)
INSERT INTO Feat_Cancion (Nombre_cancion, ID_Artista, ID_Artista_feat) VALUES
('MIA', 2, 1),
('Gently', 1, 2);

--INSERTAMOS CANCIONES MAS ESCUCHADAS DE LOS ARTISTAS
UPDATE ARTISTA SET CANCION_MAS_ESCUCHADA=(SELECT NOMBRE FROM CANCION WHERE NOMBRE='One dance') 
WHERE ID=1;

UPDATE ARTISTA SET CANCION_MAS_ESCUCHADA=(SELECT NOMBRE FROM CANCION WHERE NOMBRE='MIA') 
WHERE ID=2;

UPDATE ARTISTA SET CANCION_MAS_ESCUCHADA=(SELECT NOMBRE FROM CANCION WHERE NOMBRE='Tema de Garage') 
WHERE ID=3;



