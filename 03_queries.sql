-- =========================================================================
-- NIVEL 1: CONSULTAS BÁSICAS Y AGREGACIÓN
-- =========================================================================
-- 1. Listar los artistas verificados ordenados de mayor a menor cantidad de seguidores

SELECT *
FROM ARTISTA 
WHERE VERIFICADO=TRUE
ORDER BY SEGUIDORES ASC;

-- 2. Obtener el total de reproducciones de TODAS las canciones de la plataforma

SELECT SUM(REPRODUCCIONES)
FROM CANCION;


-- 3. Encontrar las 5 canciones más largas (Duracion)

SELECT NOMBRE,DURACION
FROM CANCION
ORDER BY DURACION DESC LIMIT(5);

-- =========================================================================
-- NIVEL 2: AGRUPAMIENTOS (GROUP BY & HAVING)
-- =========================================================================

-- 4. Calcular cuántas canciones tiene cada artista
SELECT A.NOMBRE,COUNT(C.ID_ARTISTA) AS CANT_CANCIONES
FROM ARTISTA A
LEFT JOIN CANCION C ON A.ID=C.ID_ARTISTA 
GROUP BY A.ID,A.NOMBRE;

-- 5. Listar los IDs de los artistas que tengan un promedio de reproducciones mayor a 500.000
SELECT A.NOMBRE,AVG(C.REPRODUCCIONES)
FROM ARTISTA A
INNER JOIN CANCION C ON A.ID=C.ID_ARTISTA 
GROUP BY C.ID_ARTISTA,A.NOMBRE
HAVING AVG(C.REPRODUCCIONES)>500000;



-- =========================================================================
-- NIVEL 3: RELACIONES ENTRE TABLAS (JOINS)
-- =========================================================================

-- 6. Mostrar el nombre del artista, el nombre de la canción y sus reproducciones 
SELECT A.NOMBRE,C.NOMBRE,C.REPRODUCCIONES
FROM CANCION C
INNER JOIN ARTISTA A ON A.ID=C.ID_ARTISTA;



-- 7. Listar los nombres de las canciones que están en la lista de reproducción ID = 1 del Usuario ID = 1
SELECT C.NOMBRE
FROM CANCION C
INNER JOIN LISTA_CANCION LC ON LC.NOMBRE_CANCION=C.NOMBRE AND LC.ID_ARTISTA=C.ID_ARTISTA
WHERE LC.ID_LISTA=1 AND LC.ID_USUARIO=1;


-- 8. Mostrar las canciones que tienen un artista invitado (Feat), mostrando el nombre de la canción, 
-- el nombre del artista principal y el nombre del artista invitado.
SELECT C.NOMBRE,A.NOMBRE,FEAT.NOMBRE AS NOMBRE_FEAT
FROM CANCION C
INNER JOIN FEAT_CANCION FC ON FC.NOMBRE_CANCION=C.NOMBRE AND FC.ID_ARTISTA=C.ID_ARTISTA
INNER JOIN ARTISTA A ON A.ID=FC.ID_ARTISTA
INNER JOIN ARTISTA FEAT ON FEAT.ID=FC.ID_ARTISTA_FEAT;


-- =========================================================================
-- NIVEL 4: SUBCONSULTAS Y OPERADORES AVANZADOS
-- =========================================================================

-- 9. Encontrar los nombres de los usuarios que son seguidos por mas personas que el promedio de seguidores global
SELECT NOMBRE
FROM USUARIO
WHERE SEGUIDORES>(SELECT AVG(SEGUIDORES) FROM USUARIO);

-- 10. Listar los artistas que NO tienen ninguna canción registrada todavía

SELECT A.NOMBRE
FROM ARTISTA A
EXCEPT
SELECT A.NOMBRE
FROM CANCION C
INNER JOIN ARTISTA A ON A.ID=C.ID_ARTISTA;

-- 11. Obtener una lista única de todos los nombres de usuarios y nombres de artistas en la plataforma
SELECT NOMBRE
FROM ARTISTA
UNION
SELECT NOMBRE
FROM USUARIO;

-- =========================================================================
-- NIVEL 5: SUBCONSULTAS CORRELACIONADAS
-- =========================================================================

-- 12. Listar las canciones que tienen más reproducciones que el PROMEDIO de las canciones de su PROPIO artista.
SELECT C.NOMBRE
FROM CANCION C
WHERE C.REPRODUCCIONES>(
SELECT AVG(C2.REPRODUCCIONES)
FROM CANCION C2
WHERE C2.ID_ARTISTA=C.ID_ARTISTA
)


-- 13. Encontrar los usuarios que tienen listas de reproducción, pero ÚNICAMENTE si TODAS sus listas son públicas.

SELECT U.NOMBRE
FROM USUARIO U
INNER JOIN LISTA_REPRODUCCION LR ON LR.ID_USUARIO=U.ID
GROUP BY U.ID,U.NOMBRE
HAVING COUNT(*) = (
SELECT COUNT(*)
FROM USUARIO U2
INNER JOIN LISTA_REPRODUCCION LR2 ON LR2.ID_USUARIO=U2.ID
WHERE LR2.PUBLICA=TRUE AND U2.ID=U.ID
)

-- 15. Encontrar el o los usuarios que agregaron a sus listas de reproducción ABSOLUTAMENTE TODAS las canciones del artista Bad bunny (ID=2).

SELECT U.NOMBRE
FROM USUARIO U
WHERE NOT EXISTS (
    WHERE C.ID_ARTISTA = 2
    AND NOT EXISTS (
        SELECT 1 
        FROM LISTA_CANCION LC
        WHERE LC.ID_USUARIO = U.ID 
          AND LC.NOMBRE_CANCION = C.NOMBRE 
          AND LC.ID_ARTISTA = C.ID_ARTISTA
    )
);

-- =========================================================================
-- NIVEL 6: CONSULTAS AVANZADAS DE RENDIMIENTO
-- =========================================================================

-- 16. Encontrar el o los artistas que tengan la mayor cantidad absoluta de canciones en la plataforma (Manejo de máximos con empates).

SELECT A.NOMBRE, COUNT(C.ID_ARTISTA) AS TOTAL_CANCIONES
FROM ARTISTA A
INNER JOIN CANCION C ON A.ID = C.ID_ARTISTA
GROUP BY A.ID, A.NOMBRE
HAVING COUNT(C.ID_ARTISTA) = (
    SELECT MAX(CANTIDAD)
    FROM (
        SELECT COUNT(ID_ARTISTA) AS CANTIDAD 
        FROM CANCION 
        GROUP BY ID_ARTISTA
    ) AS SUB
);

-- 17. Listar los usuarios que crearon listas de reproducción durante el año 2023, 
-- pero que nunca crearon ninguna lista durante el año 2024 (Filtro temporal exclusivo con fechas reales).

SELECT U.NOMBRE
FROM USUARIO U
INNER JOIN LISTA_REPRODUCCION LR ON LR.ID_USUARIO=U.ID
WHERE LR.FECHA_CREACION >= '2023-01-01' AND LR.FECHA_CREACION <= '2023-12-31'
EXCEPT
SELECT U.NOMBRE
FROM USUARIO U
INNER JOIN LISTA_REPRODUCCION LR ON LR.ID_USUARIO=U.ID
WHERE LR.FECHA_CREACION >= '2024-01-01' AND LR.FECHA_CREACION <= '2024-12-31'

-- 18. Mostrar los nombres de los artistas que tengan canciones en las que sean el artista principal y
-- en las que sean el feat(Intersección de roles).

SELECT A.NOMBRE
FROM ARTISTA A
INNER JOIN FEAT_CANCION FC ON FC.ID_ARTISTA=A.ID
INTERSECT
SELECT A.NOMBRE
FROM ARTISTA A
INNER JOIN FEAT_CANCION FC ON FC.ID_ARTISTA_FEAT=A.ID
