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

## 🚀 Endpoints de la API (Documentación de Rutas)

La API implementa una arquitectura REST con endpoints testeados y validados mediante **Postman**:

### 👨‍🎤 Artistas (`/artistas`)
* `GET /artistas` - Lista todos los artistas disponibles.
* `GET /artistas/{id}` - Obtiene un artista específico mediante su ID.
* `GET /artistas/buscar?nombre=...` - Buscador de artistas por coincidencia parcial de nombre.
* `GET /artistas/verificados` - Filtra únicamente los artistas con cuenta verificada.
* `GET /artistas/paginado?page=0&size=10&sort=id` - Listado paginado y ordenado (por defecto por ID).
* `POST /artistas` - Registra un nuevo artista en el catálogo.
* `POST /artistas/limpieza-verificados?minSeguidores=...` - Quita masivamente la verificación a los artistas que no alcancen el mínimo de seguidores requerido.
* `PUT /artistas/{id}` - Actualiza por completo la información de un artista existente.
* `DELETE /artistas/{id}` - Elimina un artista del sistema por su ID.

### 🎧 Canciones (`/cancion`)
* `GET /cancion` - Lista el catálogo completo de canciones.
* `GET /cancion/{nombre}` - Busca canciones por coincidencia parcial en su nombre.
* `GET /cancion/{idArtista}/{nombre}` - Obtiene una canción específica mediante su clave compuesta.
* `GET /cancion/masReproducidas?numero=...` - Retorna un top de las canciones más escuchadas según el límite indicado.
* `GET /cancion/paginado?page=0&size=10&sort=reproducciones` - Listado paginado de canciones (ordenado por defecto por reproducciones).
* `POST /cancion` - Registra un track asociado a la clave compuesta `(idArtista, nombre)`. Soporta tipo de dato `INTERVAL` de PostgreSQL mapeado con `java.time.Duration`.
* `PUT /cancion/{idArtista}/{nombre}` - Actualiza los datos de una canción identificada por su clave compuesta.
* `DELETE /cancion/{idArtista}/{nombre}` - Elimina físicamente una canción usando sus dos identificadores.

### 👤 Módulo de Usuarios (`/usuario`)
Administra los datos de los oyentes registrados en la plataforma.

| Método | Endpoint | Descripción | Parámetros / Query Params |
| :--- | :--- | :--- | :--- |
| **GET** | `/usuario` | Obtiene la lista completa de usuarios. | Ninguno |
| **GET** | `/usuario/{id}` | Busca un usuario específico por su ID único. | `id` *(Path Variable)* |
| **GET** | `/usuario/buscar` | Busca un usuario mediante coincidencia exacta/parcial de nombre. | `nombre` *(Query Param)* |
| **GET** | `/usuario/paginado` | Lista usuarios utilizando paginación y ordenamiento dinámico. | `page` (def: 0), `size` (def: 10), `sort` (def: id) |
| **POST** | `/usuario` | Crea y da de alta un nuevo usuario en el sistema. | `RequestBody` *(JSON Usuario)* |
| **PUT** | `/usuario/{id}` | Modifica los datos completos de un usuario existente. | `id` *(Path)*, `RequestBody` *(JSON)* |
| **DELETE** | `/usuario/{id}` | Elimina físicamente un usuario por su ID de la base de datos. | `id` *(Path Variable)* |

---

### 🎤 Módulo de Artistas (`/artistas`)
Gestiona los perfiles de los músicos, sus métricas de oyentes y estado de verificación.

| Método | Endpoint | Descripción | Parámetros / Query Params |
| :--- | :--- | :--- | :--- |
| **GET** | `/artistas` | Recupera el listado total de artistas registrados. | Ninguno |
| **GET** | `/artistas/{id}` | Busca un artista por su identificador primario. | `id` *(Path Variable)* |
| **GET** | `/artistas/buscar` | Filtra artistas por coincidencia de nombre. | `nombre` *(Query Param)* |
| **GET** | `/artistas/verificados` | Retorna únicamente los artistas que poseen el flag de verificado en `TRUE`. | Ninguno |
| **GET** | `/artistas/paginado` | Paginación y ordenación de artistas del catálogo. | `page` (def: 0), `size` (def: 10), `sort` (def: id) |
| **POST** | `/artistas` | Inserta un nuevo artista en el catálogo musical. | `RequestBody` *(JSON Artista)* |
| **POST** | `/artistas/limpieza-verificados` | **Operación de negocio:** Remueve el estado verificado si no cumplen una cuota. | `minSeguidores` *(Query Param)* |
| **PUT** | `/artistas/{id}` | Actualiza la información (oyentes, seguidores, etc.) de un artista. | `id` *(Path)*, `RequestBody` *(JSON)* |
| **DELETE** | `/artistas/{id}` | Da de baja a un artista y procesa transaccionalmente sus dependencias. | `id` *(Path Variable)* |

---

### 🎵 Módulo de Canciones (`/cancion`)
Soporta el CRUD de tracks musicales. **Aplica Clave Primaria Compuesta** basada en `(nombre, idArtista)`.

| Método | Endpoint | Descripción | Parámetros / Query Params |
| :--- | :--- | :--- | :--- |
| **GET** | `/cancion` | Obtiene el inventario global de canciones cargadas en la app. | Ninguno |
| **GET** | `/cancion/{idArtista}/{nombre}` | Busca un track único mapeando su **clave compuesta** exacta. | `idArtista`, `nombre` *(Path Variables)* |
| **GET** | `/cancion/{nombre}` | Busca canciones que compartan o contengan un nombre específico. | `nombre` *(Path Variable)* |
| **GET** | `/cancion/masReproducidas` | Muestra el Top N de canciones con más reproducciones históricas. | `numero` *(Query Param - cantidad)* |
| **GET** | `/cancion/paginado` | Devuelve canciones ordenadas por métricas de reproducción. | `page` (def: 0), `size` (def: 10), `sort` (def: reproducciones) |
| **POST** | `/cancion` | Registra una nueva canción en el sistema vinculada a su artista. | `RequestBody` *(JSON Cancion)* |
| **PUT** | `/cancion/{idArtista}/{nombre}` | Actualiza metadatos de un track ubicándolo por su PK compuesta. | `idArtista`, `nombre` *(Path)*, `RequestBody` *(JSON)* |
| **DELETE** | `/cancion/{idArtista}/{nombre}` | Borra una canción del catálogo validando sus restricciones lógicas. | `idArtista`, `nombre` *(Path Variables)* |

---

### 📂 Módulo de Listas de Reproducción (`/lista`)
Playlists de los usuarios. Utiliza **Clave Compuesta** asignada secuencialmente por código por usuario `(id, idUsuario)`.

| Método | Endpoint | Descripción | Parámetros / Query Params |
| :--- | :--- | :--- | :--- |
| **GET** | `/lista` | Devuelve todas las listas de reproducción del sistema. | Ninguno |
| **GET** | `/lista/{id}/{idUsuario}` | Obtiene una playlist puntual combinando su ID secuencial y su dueño. | `id`, `idUsuario` *(Path Variables)* |
| **GET** | `/lista/buscar` | Busca listas públicas o privadas por coincidencia de nombre. | `nombre` *(Query Param)* |
| **GET** | `/lista/paginado` | Paginación y ordenamiento sobre las listas del sistema. | `page` (def: 0), `size` (def: 10), `sort` (def: id) |
| **POST** | `/lista/{idUsuario}` | Crea una playlist calculando el `maxId` automático **perteneciente a ese usuario**. | `idUsuario` *(Path)*, `RequestBody` *(JSON Lista)* |
| **PUT** | `/lista/{id}/{idUsuario}` | Modifica propiedades de la lista (Nombre, Pública, Aleatorio, Descripción). | `id`, `idUsuario` *(Path)*, `RequestBody` *(JSON)* |
| **DELETE** | `/lista/{id}/{idUsuario}` | Elimina por completo una lista de reproducción del usuario. | `id`, `idUsuario` *(Path Variables)* |

---

### ➕ Módulo de Canciones por Lista (`/listaCancion`)
Tabla intermedia que rompe la relación Muchos a Muchos entre `ListaReproduccion` y `Cancion`. Cruza dos entidades con claves compuestas propias.

| Método | Endpoint | Descripción | Parámetros / Path Variables |
| :--- | :--- | :--- | :--- |
| **GET** | `/listaCancion` | Trae el mapeo global de asignaciones de canciones en listas. | Ninguno |
| **GET** | `/listaCancion/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}` | Recupera el registro exacto de inclusión usando la PK compuesta de 4 campos. | `idLista`, `idUsuario`, `nombreCancion`, `idArtista` |
| **GET** | `/listaCancion/lista/{idLista}/{idUsuario}` | **Ver Playlist:** Lista todo el contenido (canciones) agregado en una lista específica. | `idLista`, `idUsuario` *(Path Variables)* |
| **GET** | `/listaCancion/cancion/{nombreCancion}/{idArtista}` | Busca en qué listas de reproducción de la plataforma se incluyó un track específico. | `nombreCancion`, `idArtista` *(Path Variables)* |
| **GET** | `/listaCancion/paginado` | Paginación nativa del mapeo de canciones vinculadas. | `page`, `size`, `sort` *(Query Params)* |
| **POST** | `/listaCancion/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}` | **Agregar a Playlist:** Inserta una canción existente dentro de la lista de un usuario. | `idLista`, `idUsuario`, `nombreCancion`, `idArtista` |
| **DELETE** | `/listaCancion/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}` | **Quitar de Playlist:** Elimina una canción puntual de una lista de reproducción específica. | `idLista`, `idUsuario`, `nombreCancion`, `idArtista` |

---

### 🎙️ Módulo de Colaboraciones (`/featCancion`)
Mapea los artistas invitados (`Feats`) en las canciones de otros artistas principales.

| Método | Endpoint | Descripción | Parámetros / Path Variables |
| :--- | :--- | :--- | :--- |
| **GET** | `/featCancion` | Lista todas las colaboraciones registradas en el sistema. | Ninguno |
| **GET** | `/featCancion/buscar/{idArtista}/{nombreCancion}/{idFeat}` | Busca la coincidencia exacta de una colaboración. | `idArtista`, `nombreCancion`, `idFeat` |
| **GET** | `/featCancion/cancion/{idArtista}/{nombreCancion}` | Obtiene **todos los artistas invitados** que participan en una canción en particular. | `idArtista`, `nombreCancion` *(Path Variables)* |
| **GET** | `/featCancion/feat/{idFeat}` | Obtiene **todas las canciones** en las que un artista específico participó como invitado. | `idFeat` *(Path Variable)* |
| **GET** | `/featCancion/paginado` | Paginación de las colaboraciones del catálogo. | `page`, `size`, `sort` *(Query Params)* |
| **POST** | `/featCancion/{idArtista}/{nombreCancion}/{idFeat}` | **Asignar Colaboración:** Añade un artista invitado a un track musical existente. | `idArtista`, `nombreCancion`, `idFeat` |
| **DELETE** | `/featCancion/{idArtista}/{nombreCancion}/{idFeat}` | Remueve la participación de un artista invitado en una canción determinada. | `idArtista`, `nombreCancion`, `idFeat` |

---

## 📂 Estructura del Repositorio

El proyecto se encuentra modularizado dividiendo la capa de datos nativa de la lógica del servidor de aplicaciones backend:

```plaintext
spotify-database-postgres/
├── database/                   # Capa de Base de Datos Nativa
│   ├── 01_estructura.sql       # Código DDL (Creación de tablas, tipos e integridad)
│   ├── 02_data.sql             # Set de datos de prueba ("seed data")
│   └── 03_queries.sql          # Banco de consultas complejas en SQL puro
├── spotify-api/                # Capa Backend (Servidor REST)
│   ├── src/                    # Código fuente Java organizado en capas (Model, Repository, Service, Controller)
│   ├── pom.xml                 # Gestor de dependencias Maven (Spring Data JPA, PostgreSQL Driver)
│   └── application.properties  # Configuración de entorno y credenciales de conexión
└── README.md                   # Documentación general del proyecto
```
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

