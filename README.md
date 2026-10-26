# Gym Management API

API REST para la gestión de un gimnasio, desarrollada con **Java 17 y Spring Boot**. Permite administrar miembros, planes, entrenadores y clases, además de gestionar usuarios, autenticación y autorización mediante JSON Web Tokens (JWT).

El proyecto está enfocado en la aplicación de buenas prácticas de desarrollo backend, arquitectura por capas, persistencia con Spring Data JPA, validación de datos, manejo centralizado de excepciones, seguridad y pruebas automatizadas.

## Tecnologías

- **Java 17**
- **Spring Boot 4.1.1**
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JSON Web Token (JWT)
- JJWT 0.12.6
- MySQL
- Jakarta Validation
- Lombok
- Maven
- JUnit 5 y Spring Boot Test
- H2 Database para pruebas
- JaCoCo para reportes de cobertura de pruebas

## Funcionalidades

### Miembros

- Crear, consultar, actualizar y eliminar miembros.
- Consultar un miembro por su identificador.
- Asociar miembros con planes.
- Consultar miembros asociados a un plan.
- Filtrar miembros por estado activo mediante un parámetro opcional.

### Planes

- Crear, consultar, actualizar y eliminar planes.
- Consultar un plan por su identificador.
- Validar precios y duración de los planes.
- Gestionar el estado activo de los planes.
- Asociar planes con miembros.

### Entrenadores

- Crear, consultar, actualizar y eliminar entrenadores.
- Consultar un entrenador por su identificador.
- Gestionar especialidades y estado del entrenador.
- Asociar entrenadores con clases.

### Clases

- Crear, consultar, actualizar y eliminar clases.
- Consultar una clase por su identificador.
- Asignar un entrenador a una clase.
- Agregar miembros a una clase.
- Remover miembros de una clase.
- Gestionar las relaciones entre clases y miembros.

### Usuarios y autenticación

- Registrar usuarios.
- Validar credenciales durante el inicio de sesión.
- Encriptar contraseñas mediante BCrypt.
- Generar tokens JWT después de una autenticación exitosa.
- Validar tokens JWT en las peticiones protegidas.
- Aplicar autorización basada en roles mediante Spring Security.
- Gestionar errores relacionados con credenciales inválidas.

## Seguridad

La API utiliza **Spring Security y JWT** para proteger los endpoints y controlar el acceso a los recursos.

### Flujo de autenticación

1. El usuario se registra mediante el endpoint de registro.
2. El usuario envía su correo electrónico y contraseña al endpoint de inicio de sesión.
3. Spring Security valida las credenciales.
4. Si la autenticación es exitosa, la API genera un token JWT.
5. El cliente utiliza el token en las siguientes peticiones protegidas mediante la cabecera `Authorization`.

Ejemplo de cabecera:

```http
Authorization: Bearer <JWT_TOKEN>
```

### Roles y autorización

La configuración de seguridad contempla roles como:

- `Member`
- `Trainer`
- `Boss`
- `Admin`

El acceso a las operaciones se configura mediante reglas de autorización de Spring Security, según el tipo de recurso y la operación solicitada.

### Seguridad de credenciales

Las contraseñas de los usuarios se almacenan utilizando BCrypt. La clave secreta utilizada para firmar los JWT debe mantenerse fuera del repositorio y configurarse mediante una variable de entorno.

La clave JWT debe generarse aleatoriamente con una longitud mínima de 32 bytes para el algoritmo HS256.

**Importante:** las reglas de autorización deben coincidir con los métodos HTTP implementados en los controladores. En particular, las operaciones de actualización utilizan `PATCH`, por lo que deben revisarse las reglas correspondientes en `SecurityConfig`.

## Arquitectura

El proyecto utiliza una arquitectura por capas para separar las responsabilidades y facilitar el mantenimiento.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Spring Data JPA / Hibernate
    ↓
MySQL
```

### Controller

Expone los endpoints REST, recibe las solicitudes HTTP, valida los datos de entrada y devuelve las respuestas correspondientes.

### Service

Contiene la lógica de negocio y coordina las operaciones entre los controladores y los repositorios.

### Repository

Utiliza Spring Data JPA para consultar y modificar las entidades persistidas en la base de datos.

### Entity

Representa las entidades del dominio y sus relaciones mediante anotaciones JPA.

### DTO

Separa los datos recibidos en las solicitudes de las entidades utilizadas internamente. También permite aplicar validaciones a los datos de entrada.

### Mapper

Centraliza la conversión entre entidades, DTO de solicitud y DTO de respuesta.

### Security

Contiene los componentes relacionados con la autenticación y autorización, incluyendo la generación y validación de tokens JWT y el filtro de autenticación.

### Exception

Centraliza el manejo de errores mediante excepciones personalizadas y un manejador global.

## Relaciones entre entidades

El modelo de datos representa las siguientes relaciones:

- Un `Plan` puede estar asociado a varios `Member`.
- Un `Member` pertenece a un `Plan`.
- Un `Trainer` puede impartir varias `GymClass`.
- Una `GymClass` pertenece a un `Trainer`.
- Una `GymClass` puede tener varios `Member`.
- Un `Member` puede pertenecer a varias `GymClass`.

La relación entre clases y miembros utiliza una tabla intermedia:

```text
gym_class_member
├── id_class
└── id_member
```

Estas relaciones se implementan mediante JPA y Hibernate.

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/com/management/gym/
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── GymClassController.java
│   │   │   ├── MemberController.java
│   │   │   ├── PlanController.java
│   │   │   ├── TrainerController.java
│   │   │   └── UserController.java
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── entity/
│   │   │   ├── GymClass.java
│   │   │   ├── Member.java
│   │   │   ├── Plan.java
│   │   │   ├── Trainer.java
│   │   │   └── User.java
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── repository/
│   │   ├── security/
│   │   │   ├── JwtAuthenticatorFilter.java
│   │   │   └── JwtService.java
│   │   ├── service/
│   │   └── GymApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/management/gym/
        ├── controller/
        ├── repository/
        ├── service/
        └── GymApplicationTests.java
```

## Endpoints de la API

La aplicación utiliza el prefijo base:

```text
http://localhost:8080
```

### Autenticación y usuarios

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/user/register` | Registrar un usuario |
| POST | `/auth/login` | Autenticar un usuario y obtener un JWT |

### Miembros

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/members` | Crear un miembro |
| GET | `/members` | Consultar miembros |
| GET | `/members/{id}` | Consultar un miembro por ID |
| PATCH | `/members/{id}` | Actualizar un miembro |
| DELETE | `/members/{id}` | Eliminar un miembro |
| GET | `/members/plan` | Consultar miembros por plan y estado |

Para consultar miembros por plan, el comportamiento previsto es utilizar parámetros de consulta:

```http
GET /members/plan?idPlan=1
GET /members/plan?idPlan=1&active=true
```

**Nota:** el mapeo actual del controlador de miembros debe ajustarse a `@GetMapping("/plan")` para que estas rutas coincidan con la documentación.

### Planes

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/plans` | Crear un plan |
| GET | `/plans` | Consultar planes |
| GET | `/plans/{id}` | Consultar un plan por ID |
| PATCH | `/plans/{id}` | Actualizar un plan |
| DELETE | `/plans/{id}` | Eliminar un plan |

### Entrenadores

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/trainer` | Crear un entrenador |
| GET | `/trainer` | Consultar entrenadores |
| GET | `/trainer/{id}` | Consultar un entrenador por ID |
| PATCH | `/trainer/{id}` | Actualizar un entrenador |
| DELETE | `/trainer/{id}` | Eliminar un entrenador |

### Clases

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/gymclass` | Crear una clase |
| GET | `/gymclass` | Consultar clases |
| GET | `/gymclass/{idClass}` | Consultar una clase por ID |
| PATCH | `/gymclass/{idClass}` | Actualizar una clase |
| DELETE | `/gymclass/{idClass}` | Eliminar una clase |
| POST | `/gymclass/{idClass}/members` | Agregar miembros a una clase |
| DELETE | `/gymclass/{classId}/members/{memberId}` | Remover un miembro de una clase |

Los endpoints protegidos requieren autenticación y los permisos correspondientes.

## Base de datos

El proyecto utiliza MySQL y espera una base de datos llamada `gym_system`.

Crear la base de datos:

```sql
CREATE DATABASE gym_system;
```

### Configuración

Configura las siguientes propiedades en `src/main/resources/application.properties`:

```properties
spring.application.name=gym

spring.datasource.url=jdbc:mysql://localhost:3306/gym_system?useSSL=false&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update

jwt.secret=${JWT_SECRET}
```

Define las variables de entorno `DB_USERNAME`, `DB_PASSWORD` y `JWT_SECRET` antes de ejecutar la aplicación.

No subas credenciales reales ni claves secretas al repositorio.

La propiedad `spring.jpa.hibernate.ddl-auto=update` permite que Hibernate actualice el esquema de la base de datos según las entidades. Para entornos de producción, conviene utilizar una estrategia controlada de migraciones.

## Instalación y ejecución

### Requisitos

- Java 17
- Maven, o el Maven Wrapper incluido en el proyecto
- MySQL
- Git

### 1. Clonar el repositorio

```bash
git clone https://github.com/SuperMKCG/gym-management-api.git
cd gym-management-api
```

### 2. Crear la base de datos

```sql
CREATE DATABASE gym_system;
```

### 3. Configurar las variables de entorno

En PowerShell, por ejemplo:

```powershell
$env:DB_USERNAME="tu_usuario"
$env:DB_PASSWORD="tu_contraseña"
$env:JWT_SECRET="tu_clave_aleatoria_segura"
```

Genera una clave JWT aleatoria de al menos 32 bytes para HS256. Estos comandos establecen las variables únicamente para la sesión actual de PowerShell.

### 4. Ejecutar la aplicación

En Windows, con Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

También puedes ejecutar el proyecto desde IntelliJ IDEA mediante la clase `GymApplication`.

Por defecto, la API estará disponible en:

```text
http://localhost:8080
```

## Ejemplo de autenticación

Solicitud de inicio de sesión:

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "usuario@example.com",
  "password": "TuPassword123!"
}
```

La respuesta contiene un token JWT:

```json
{
  "token": "<JWT_TOKEN>"
}
```

El token debe enviarse en la cabecera `Authorization` al acceder a endpoints protegidos.

## Validaciones y manejo de errores

La aplicación utiliza Jakarta Validation para validar los datos recibidos mediante los DTO.

Entre las validaciones contempladas se encuentran:

- Campos obligatorios.
- Formato de correo electrónico.
- Requisitos de complejidad para las contraseñas durante el registro.
- Valores numéricos positivos.
- Reglas de precio y duración de los planes.
- Existencia de los recursos solicitados.
- Reglas de negocio relacionadas con los planes.

El proyecto implementa excepciones personalizadas, entre ellas:

- `BusinessRuleException`
- `InvalidCredentialsException`
- `ResourceNotFoundException`

`GlobalExceptionHandler` centraliza el tratamiento de excepciones y permite devolver respuestas de error estructuradas.

## Pruebas automatizadas

El proyecto incluye pruebas para distintas capas de la aplicación:

- **Controller:** pruebas de endpoints de miembros.
- **Service:** pruebas de la lógica de miembros y entrenadores.
- **Repository:** pruebas de persistencia de miembros y entrenadores.
- **Contexto de Spring Boot:** pruebas de carga de la aplicación.

Se utilizan JUnit y las herramientas de pruebas de Spring Boot. H2 está incluida como dependencia de pruebas.

### Ejecutar las pruebas

Con Maven Wrapper en Windows:

```powershell
.\mvnw.cmd test
```

### Reporte de cobertura

El proyecto incluye JaCoCo para generar reportes de cobertura durante la ejecución de las pruebas.

Después de ejecutar:

```powershell
.\mvnw.cmd test
```

Revisa el reporte HTML en:

```text
target/site/jacoco/index.html
```

La cobertura depende de las pruebas implementadas y no implica que todas las clases estén cubiertas.

## Objetivo del proyecto

Este proyecto permite practicar y consolidar conocimientos de desarrollo backend con Java y Spring Boot, incluyendo:

- Diseño y desarrollo de APIs REST.
- Arquitectura por capas.
- Spring Data JPA y Hibernate.
- Relaciones entre entidades.
- DTO y mapeo de objetos.
- Validación de datos.
- Manejo centralizado de excepciones.
- Autenticación y autorización con Spring Security.
- Generación y validación de JWT.
- Persistencia con MySQL.
- Pruebas automatizadas con JUnit.
- Reportes de cobertura con JaCoCo.
- Gestión de código fuente con Git y GitHub.

Proyecto personal de práctica y aprendizaje de desarrollo backend con Java y Spring Boot.
