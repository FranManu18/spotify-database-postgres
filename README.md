# 🎵 Spotify Database Project - PostgreSQL

¡Bienvenido! Este proyecto consiste en el diseño, implementación y explotación de una base de datos relacional orientada a una plataforma de streaming de música estilo **Spotify**. 

El objetivo principal es demostrar habilidades avanzadas en el diseño lógico de datos, manejo de restricciones de integridad (Claves Primarias Compuestas, Claves Foráneas) y la resolución de consultas de negocio complejas mediante **PostgreSQL**.

---

## 📊 Modelo de Datos (Estructura)

El diseño cuenta con **6 tablas** optimizadas que modelan la interacción entre usuarios, artistas, canciones y listas de reproducción, soportando colaboraciones (`Feat`) y relaciones Muchos a Muchos (N:M).

| Tabla | Descripción | Clave Primaria (PK) |
| :--- | :--- | :--- |
| **Usuario** | Registra los oyentes de la plataforma. | `ID` |
| **Artista** | Mapea a los creadores con métricas y estado de verificación. | `ID` |
| **Cancion** | Almacena los tracks (Maneja clave compuesta con el artista). | `(Nombre, ID_Artista)` |
| **ListaReproduccion** | Playlists creadas por los usuarios. | `(ID, ID_Usuario)` |
| **Lista_Cancion** | Tabla intermedia N:M que conecta canciones con las listas. | `(ID_Lista, ID_Usuario, Nombre_cancion, ID_Artista)` |
| **Feat_Cancion** | Tabla intermedia para colaboraciones entre artistas. | `(Nombre_cancion, ID_Artista, ID_Artista_feat)` |

---

## 📂 Estructura del Repositorio

El proyecto se encuentra modularizado de la siguiente manera para facilitar su despliegue:

* 📄 `01_estructura.sql`: Contiene el código DDL para la creación de tablas, definición de tipos de datos (`INTERVAL`, `BOOLEAN`) y restricciones.
* 📄 `02_data.sql`: Set de datos de prueba ("seed data") con escenarios reales para testear la lógica del negocio.
* 📄 `03_queries.sql`: Banco de consultas ordenadas por complejidad.

---

## 🔍 Consultas Destacadas (Demostración de Habilidades)

A continuación se presentan algunas de las consultas SQL más complejas implementadas en el archivo `03_queries.sql` para resolver problemas lógicos avanzados:

### ⚡ Manejo de Agregación y Left Joins (Control de Nulos)
Consulta optimizada para calcular la cantidad de canciones por artista. Utiliza `COUNT(C.ID_ARTISTA)` sobre un `LEFT JOIN` para garantizar que los artistas nuevos o sin catálogo figuren correctamente con un total de `0` en lugar de caer en el error común de contabilizar una fila vacía como `1`.

```sql
SELECT A.NOMBRE, COUNT(C.ID_ARTISTA) AS CANT_CANCIONES
FROM ARTISTA A
LEFT JOIN CANCION C ON A.ID = C.ID_ARTISTA 
GROUP BY A.ID, A.NOMBRE;
```

### 🧠 Operadores de Conjuntos (EXCEPT)
Demostración del uso de operaciones de conjuntos del álgebra relacional para identificar de forma limpia qué artistas de la base de datos no poseen aún ninguna canción registrada.

```sql
SELECT A.NOMBRE
FROM ARTISTA A
EXCEPT
SELECT A.NOMBRE
FROM CANCION C
INNER JOIN ARTISTA A ON A.ID = C.ID_ARTISTA;
```

### 🎯 Subconsultas Avanzadas y Agrupamientos Complejos
Una excelente resolución lógica para encontrar usuarios cuyas listas de reproducción sean únicamente públicas. Compara el conteo total de listas del usuario contra un conteo condicional que filtra solo las públicas a través de una subconsulta correlacionada en el HAVING.

```sql
SELECT U.NOMBRE
FROM USUARIO U
INNER JOIN LISTA_REPRODUCCION LR ON LR.ID_USUARIO = U.ID
GROUP BY U.ID, U.NOMBRE
HAVING COUNT(*) = (
    SELECT COUNT(*)
    FROM USUARIO U2
    INNER JOIN LISTA_REPRODUCCION LR2 ON LR2.ID_USUARIO = U2.ID
    WHERE LR2.PUBLICA = TRUE AND U2.ID = U.ID
);
```

###  Resolución de División Relacional 
Consulta diseñada para resolver el problema de división relacional: encontrar qué usuarios agregaron a sus listas de reproducción absolutamente todas las canciones de un artista específico (Bad Bunny - ID 2). Se logra comparando las canciones del artista dentro de las listas del usuario contra el total general de temas que posee dicho artista en el catálogo.

```sql
SELECT U.NOMBRE
FROM USUARIO U
INNER JOIN LISTA_REPRODUCCION LR ON LR.ID_USUARIO = U.ID
INNER JOIN LISTA_CANCION LC ON LC.ID_LISTA = LR.ID
WHERE LC.ID_ARTISTA = 2
GROUP BY U.ID, U.NOMBRE
HAVING COUNT(*) = (
    SELECT COUNT(*)
    FROM ARTISTA A
    INNER JOIN CANCION C ON C.ID_ARTISTA = A.ID
    WHERE ID = 2
);
```

### 🛠️ Tecnologías Utilizadas
* Motor de Base de Datos: PostgreSQL 18

* Interfaz de Administración: pgAdmin 4

* Editor de Código: Visual Studio Code / Markdown

**Proyecto desarrollado como parte del proceso de formación profesional en bases de datos relacionales.**

