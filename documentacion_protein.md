# Documentación del Microservicio de Proteínas — GenopolisProtein

## 1. Introducción

### ¿Para qué sirve?

**GenopolisProtein** es un microservicio REST diseñado para gestionar un catálogo de proteínas en el contexto del proyecto **Genopolis Colombia**. Permite registrar, consultar, actualizar y eliminar proteínas con sus datos biológicos asociados, incluyendo nombre FASTA, secuencia, fuente, organismo, clasificación y autores.

### ¿Qué hace?

- Expone una API REST con operaciones CRUD sobre el recurso `protein`.
- Persiste la información en una base de datos **MySQL**.
- Aplica una **arquitectura hexagonal (Ports & Adapters)**, separando la lógica de negocio (capa `domain`) de los detalles de infraestructura (capa `infra`).
- Habilita CORS para comunicarse con un frontend ejecutándose en `http://localhost:5173`.

---

## 2. Descripción General de la API

| Propiedad | Valor |
|---|---|
| Protocolo | HTTP/REST |
| Puerto | `8083` |
| URL base | `http://localhost:8083` |
| Formato de datos | JSON |
| CORS permitido | `http://localhost:5173` |

### Métodos HTTP soportados

`GET` · `POST` · `PUT` · `DELETE`

---

## 3. Modelo de Datos

### Proteína (`Protein`)

| Campo | Tipo | Descripción |
|---|---|---|
| `idProteina` | `UUID` | Identificador único generado automáticamente |
| `fastaNombre` | `String` | Nombre de la secuencia en formato FASTA |
| `fastaSecuencia` | `String` | Secuencia de aminoácidos en formato FASTA |
| `fuente` | `String` | Base de datos u origen de la secuencia (ej. UniProt, NCBI) |
| `organismo` | `String` | Organismo del que proviene la proteína |
| `clasificacion` | `String` | Clasificación funcional o estructural de la proteína |
| `ecClasificacion` | `Integer` | Número de clasificación enzimática (EC number) |
| `autores` | `String` | Autores o responsables del registro |

---

## 4. Documentación de Endpoints

### 4.1. Crear proteína

Registra una nueva proteína en el sistema.

- **Método:** `POST`
- **URL:** `/proteins`
- **Código de respuesta exitosa:** `201 Created`

**Cuerpo de la solicitud (JSON):**

```json
{
  "fastaNombre": "sp|P68871|HBB_HUMAN",
  "fastaSecuencia": "MVHLTPEEKSAVTALWGKVNVDEVGGEALGRLLVVYPWTQRFFESFGDLSTPDAVMGNPKVKAHGKKVLGAFSDGLAHLDNLKGTFATLSELHCDKLHVDPENFRLLGNVLVCVLAHHFGKEFTPPVQAAYQKVVAGVANALAHKYH",
  "fuente": "UniProtKB/Swiss-Prot",
  "organismo": "Homo sapiens",
  "clasificacion": "Transporte de oxígeno",
  "ecClasificacion": 1,
  "autores": "Lina Avila"
}
```

| Campo | Tipo | Requerido | Descripción |
|---|---|---|---|
| `fastaNombre` | `String` | Sí | Nombre FASTA de la proteína |
| `fastaSecuencia` | `String` | Sí | Secuencia de aminoácidos |
| `fuente` | `String` | Sí | Origen de la secuencia |
| `organismo` | `String` | Sí | Organismo de la proteína |
| `clasificacion` | `String` | Sí | Clasificación funcional |
| `ecClasificacion` | `Integer` | Sí | Número EC |
| `autores` | `String` | Sí | Autores del registro |

**Respuesta exitosa (`201 Created`):**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

### 4.2. Obtener todas las proteínas

Retorna la lista completa de proteínas registradas.

- **Método:** `GET`
- **URL:** `/proteins`
- **Código de respuesta exitosa:** `200 OK`

**Respuesta exitosa (`200 OK`):**

```json
{
  "proteins": [
    {
      "idProteina": "550e8400-e29b-41d4-a716-446655440000",
      "fastaNombre": "sp|P68871|HBB_HUMAN",
      "fastaSecuencia": "MVHLTPEEKSAVTALWGKVNVDEVGG...",
      "fuente": "UniProtKB/Swiss-Prot",
      "organismo": "Homo sapiens",
      "clasificacion": "Transporte de oxígeno",
      "ecClasificacion": 1,
      "autores": "Lina Avila"
    }
  ]
}
```

---

### 4.3. Obtener proteína por ID

Retorna los datos de una proteína específica según su identificador UUID.

- **Método:** `GET`
- **URL:** `/proteins/{protein_id}`
- **Parámetro de ruta:** `protein_id` — UUID de la proteína

**Respuesta exitosa (`200 OK`):**

```json
{
  "idProteina": "550e8400-e29b-41d4-a716-446655440000",
  "fastaNombre": "sp|P68871|HBB_HUMAN",
  "fastaSecuencia": "MVHLTPEEKSAVTALWGKVNVDEVGG...",
  "fuente": "UniProtKB/Swiss-Prot",
  "organismo": "Homo sapiens",
  "clasificacion": "Transporte de oxígeno",
  "ecClasificacion": 1,
  "autores": "Lina Avila"
}
```

**Respuesta de error (`404 Not Found`):**

```json
{
  "failure": "Protein not found",
  "detail": "the protein with id: 550e8400-e29b-41d4-a716-446655440000 was not found"
}
```

---

### 4.4. Actualizar proteína

Actualiza parcialmente los campos de una proteína existente. Solo se deben enviar los campos que se desean modificar; al menos uno es obligatorio.

- **Método:** `PUT`
- **URL:** `/proteins/{protein_id}`
- **Parámetro de ruta:** `protein_id` — UUID de la proteína a actualizar

**Cuerpo de la solicitud (JSON) — todos los campos son opcionales:**

```json
{
  "fastaNombre": "sp|P68871|HBB_HUMAN_v2",
  "fastaSecuencia": null,
  "fuente": null,
  "organismo": "Homo sapiens (actualizado)",
  "clasificacion": null,
  "ecClasificacion": null,
  "autores": null
}
```

| Campo | Tipo | Requerido | Descripción |
|---|---|---|---|
| `fastaNombre` | `String` (opcional) | No | Nuevo nombre FASTA |
| `fastaSecuencia` | `String` (opcional) | No | Nueva secuencia |
| `fuente` | `String` (opcional) | No | Nueva fuente |
| `organismo` | `String` (opcional) | No | Nuevo organismo |
| `clasificacion` | `String` (opcional) | No | Nueva clasificación |
| `ecClasificacion` | `Integer` (opcional) | No | Nuevo número EC (debe ser > 0) |
| `autores` | `String` (opcional) | No | Nuevos autores |

> Al menos uno de los campos anteriores debe tener un valor no vacío.

**Respuesta exitosa (`204 No Content`):** Cuerpo vacío.

**Respuesta de error (`400 Bad Request`) — ningún campo fue enviado:**

```json
{
  "failure": "Invalid fields",
  "detail": "At least one field must be provided to update the protein"
}
```

**Respuesta de error (`404 Not Found`) — proteína no existe:**

```json
{
  "failure": "Invalid protein id",
  "detail": "The provided protein was not found"
}
```

---

### 4.5. Eliminar proteína

Elimina permanentemente una proteína del sistema por su UUID.

- **Método:** `DELETE`
- **URL:** `/proteins/{protein_id}`
- **Parámetro de ruta:** `protein_id` — UUID de la proteína a eliminar
- **Código de respuesta exitosa:** `204 No Content`

**Respuesta exitosa (`204 No Content`):** Cuerpo vacío.

---

## 5. Resumen de Endpoints

| Método | Endpoint | Descripción | Código Exitoso |
|---|---|---|---|
| `POST` | `/proteins` | Crear una nueva proteína | `201 Created` |
| `GET` | `/proteins` | Listar todas las proteínas | `200 OK` |
| `GET` | `/proteins/{protein_id}` | Obtener una proteína por ID | `200 OK` |
| `PUT` | `/proteins/{protein_id}` | Actualizar parcialmente una proteína | `204 No Content` |
| `DELETE` | `/proteins/{protein_id}` | Eliminar una proteína | `204 No Content` |

---

## 6. Códigos de Estado HTTP

| Código | Significado | Cuándo ocurre |
|---|---|---|
| `200 OK` | Solicitud exitosa | GET retorna un recurso |
| `201 Created` | Recurso creado | POST crea una nueva proteína |
| `204 No Content` | Operación exitosa sin respuesta | PUT o DELETE exitosos |
| `400 Bad Request` | Solicitud inválida | PUT sin campos válidos para actualizar |
| `404 Not Found` | Recurso no encontrado | GET o PUT con UUID que no existe |

---

## 7. Arquitectura

El microservicio implementa **arquitectura hexagonal (Ports & Adapters)** organizada en dos módulos Gradle:

```
GenopolisProtein/
├── domain/          # Lógica de negocio pura (sin dependencias externas)
│   ├── kernel/      # Entidades del dominio (Protein, UpdateProtein)
│   ├── port/        # Interfaces (puertos): RepositoryPort
│   └── usecase/     # Casos de uso: Create, Get, GetAll, Put, Delete
│
└── infra/           # Capa de infraestructura (Spring Boot)
    ├── adapters/
    │   ├── in/http/ # Adaptador de entrada: controlador REST + DTOs
    │   └── out/mysql/ # Adaptador de salida: JPA + MySQL
    ├── handlers/    # Orquestadores entre controlador y casos de uso
    └── configuration/ # Configuración de beans de Spring
```

### Flujo de una solicitud

```
Cliente HTTP
    → ProteinControllerAdapter (REST)
        → Handler (orquestador)
            → UseCase (lógica de negocio)
                → RepositoryPort (interfaz)
                    → MysqlProteinRepositoryImpl (JPA)
                        → MySQL (base de datos)
```

---

## 8. Configuración y Requisitos

### Requisitos

| Componente | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.4 |
| MySQL | 8.x |
| Gradle | Compatible con el wrapper incluido |

### Base de datos

| Parámetro | Valor |
|---|---|
| Motor | MySQL |
| Base de datos | `proteins_genopolis` |
| Host | `localhost:3306` |
| Usuario | `springuser` |
| Contraseña | `p4t1App` |
| DDL | `update` (crea o actualiza tablas automáticamente) |

### Ejecutar el microservicio

```bash
./gradlew :infra:bootRun
```

El servidor quedará disponible en: `http://localhost:8083`

---

## 9. Dependencias Principales

| Librería | Propósito |
|---|---|
| `spring-boot-starter-web` | Exposición de endpoints REST |
| `spring-boot-starter-data-jpa` | Persistencia con JPA/Hibernate |
| `mysql-connector-j` | Conector JDBC para MySQL |
| `lombok` | Reducción de código repetitivo |
| `slf4j-api` | Logging en la capa de dominio |
| `testcontainers` | Pruebas de integración con contenedores MySQL |
