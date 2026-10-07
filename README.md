# Gym Management API

API REST para la gestión de un gimnasio, desarrollada con **Java y Spring Boot**. El proyecto permite administrar miembros, planes, entrenadores y clases, incluyendo las relaciones entre estas entidades.

El proyecto fue desarrollado como práctica para consolidar conocimientos de desarrollo de APIs REST, Spring Data JPA, Hibernate, DTOs, validaciones y manejo de excepciones.

## Tecnologías

* Java 17
* Spring Boot 4.1.1
* Spring Web MVC
* Spring Data JPA
* Hibernate
* MySQL
* Jakarta Validation
* Lombok
* Maven

## Funcionalidades

### Miembros

* Crear miembros
* Consultar todos los miembros
* Consultar un miembro por ID
* Actualizar información de un miembro
* Eliminar miembros
* Asignar un plan a un miembro
* Consultar miembros por plan
* Filtrar miembros por estado activo

### Planes

* Crear planes
* Consultar todos los planes
* Consultar un plan por ID
* Actualizar planes
* Eliminar planes
* Validar precio y duración del plan
* Controlar si un plan se encuentra activo

### Entrenadores

* Crear entrenadores
* Consultar todos los entrenadores
* Consultar un entrenador por ID
* Actualizar entrenadores
* Eliminar entrenadores
* Gestionar especialidad y estado del entrenador

### Clases

* Crear clases
* Consultar todas las clases
* Consultar una clase por ID
* Actualizar clases
* Eliminar clases
* Asignar un entrenador a una clase
* Agregar miembros a una clase
* Remover miembros de una clase

## Relaciones entre entidades

El modelo utiliza relaciones JPA para representar la estructura del gimnasio:

```text
Plan
  │
  │ 1:N
  ▼
Member
  │
  │ N:M
  ▼
GymClass
  │
  │ N:1
  ▼
Trainer
```

### Relaciones implementadas

* Un **Plan** puede estar asociado a varios **Members**.
* Un **Member** pertenece a un **Plan**.
* Un **Trainer** puede impartir varias **GymClass**.
* Una **GymClass** pertenece a un **Trainer**.
* Una **GymClass** puede tener varios **Members**.
* Un **Member** puede pertenecer a varias **GymClass**.

La relación entre `GymClass` y `Member` utiliza una tabla intermedia:

```text
gym_class_member
├── id_class
└── id_member
```

## Arquitectura

El proyecto utiliza una arquitectura por capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

### Controller

Maneja las peticiones HTTP y expone los endpoints de la API.

### Service

Contiene la lógica de negocio y coordina las operaciones entre los controladores y los repositorios.

### Repository

Utiliza Spring Data JPA para acceder a la base de datos y realizar consultas sobre las entidades.

### Entity

Representa las tablas de la base de datos mediante JPA.

### DTO

Se utilizan DTOs para separar los datos recibidos en las peticiones de las entidades utilizadas internamente por la aplicación.

### Manejo de excepciones

El proyecto cuenta con excepciones para recursos inexistentes y reglas de negocio, además de un manejador global mediante `GlobalExceptionHandler`.

## Estructura del proyecto

```text
src/
└── main/
    ├── java/com/management/gym/
    │   ├── controller/
    │   │   ├── GymClassController.java
    │   │   ├── MemberController.java
    │   │   ├── PlanController.java
    │   │   └── TrainerController.java
    │   │
    │   ├── dto/
    │   │   ├── request/
    │   │   └── response/
    │   │
    │   ├── entity/
    │   │   ├── GymClass.java
    │   │   ├── Member.java
    │   │   ├── Plan.java
    │   │   └── Trainer.java
    │   │
    │   ├── exception/
    │   │   ├── BusinessRuleException.java
    │   │   ├── GlobalExceptionHandler.java
    │   │   └── ResourceNotFoundException.java
    │   │
    │   ├── repository/
    │   │   ├── GymClassRepository.java
    │   │   ├── MemberRepository.java
    │   │   ├── PlanRepository.java
    │   │   └── TrainerRepository.java
    │   │
    │   └── service/
    │       ├── GymClassService.java
    │       ├── MemberService.java
    │       ├── PlanService.java
    │       └── TrainerService.java
    │
    └── resources/
        ├── application.properties
        └── application-example.properties
```

## Endpoints principales

### Members

| Método | Endpoint                | Descripción                 |
| ------ | ----------------------- | --------------------------- |
| POST   | `/members`              | Crear miembro               |
| GET    | `/members`              | Obtener miembros            |
| GET    | `/members/{id}`         | Obtener miembro por ID      |
| PATCH  | `/members/{id}`         | Actualizar miembro          |
| DELETE | `/members/{id}`         | Eliminar miembro            |
| GET    | `/members/plan{idPlan}` | Consultar miembros por plan |

El filtro de estado puede utilizarse como parámetro opcional:

```text
GET /members/plan?idPlan=1
GET /members/plan?idPlan=1&active=true
```

### Plans

| Método | Endpoint      | Descripción         |
| ------ | ------------- | ------------------- |
| POST   | `/plans`      | Crear plan          |
| GET    | `/plans`      | Obtener planes      |
| GET    | `/plans/{id}` | Obtener plan por ID |
| PATCH  | `/plans/{id}` | Actualizar plan     |
| DELETE | `/plans/{id}` | Eliminar plan       |

### Trainers

| Método | Endpoint        | Descripción               |
| ------ | --------------- | ------------------------- |
| POST   | `/trainer`      | Crear entrenador          |
| GET    | `/trainer`      | Obtener entrenadores      |
| GET    | `/trainer/{id}` | Obtener entrenador por ID |
| PATCH  | `/trainer/{id}` | Actualizar entrenador     |
| DELETE | `/trainer/{id}` | Eliminar entrenador       |

### Gym Classes

| Método | Endpoint                                 | Descripción                     |
| ------ | ---------------------------------------- | ------------------------------- |
| POST   | `/gymclass`                              | Crear clase                     |
| GET    | `/gymclass`                              | Obtener clases                  |
| GET    | `/gymclass/{idClass}`                    | Obtener clase por ID            |
| PATCH  | `/gymclass/{idClass}`                    | Actualizar clase                |
| DELETE | `/gymclass/{idClass}`                    | Eliminar clase                  |
| POST   | `/gymclass/{idClass}/members`            | Agregar miembros a una clase    |
| DELETE | `/gymclass/{classId}/members/{memberId}` | Remover un miembro de una clase |

## Base de datos

El proyecto utiliza **MySQL**.

La aplicación espera una base de datos llamada:

```sql
gym_system
```

Para configurar la conexión, utiliza como referencia:

```text
src/main/resources/application-example.properties
```

Ejemplo:

```properties
spring.application.name=gym

spring.datasource.url=jdbc:mysql://localhost:3306/gym_system?useSSL=false&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

> No se incluyen credenciales reales en el repositorio.

## Instalación y ejecución

### Requisitos

* Java 17 o superior
* Maven
* MySQL
* Git

### 1. Clonar el repositorio

```bash
git clone https://github.com/TU-USUARIO/gym.git
cd gym
```

### 2. Crear la base de datos

En MySQL:

```sql
CREATE DATABASE gym_system;
```

### 3. Configurar la aplicación

Crear o configurar:

```text
src/main/resources/application.properties
```

con las credenciales locales de MySQL.

### 4. Ejecutar el proyecto

Con Maven:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

La API estará disponible en:

```text
http://localhost:8080
```

## Ejemplo de creación de un miembro

```http
POST /members
Content-Type: application/json
```

```json
{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "7711234567",
  "planID": 1
}
```

El miembro se registra asociado al plan indicado.

## Validaciones

El proyecto utiliza **Jakarta Validation** para validar los datos recibidos mediante los DTOs.

Entre las validaciones implementadas se encuentran:

* Campos obligatorios
* Valores numéricos positivos
* Duración de planes entre 1 y 12 meses
* Precio mayor a cero
* Validación de recursos existentes
* Validaciones relacionadas con planes activos

## Objetivo del proyecto

Este proyecto fue desarrollado para practicar y consolidar conocimientos de:

* Desarrollo de APIs REST con Spring Boot
* Arquitectura por capas
* Spring Data JPA
* Hibernate
* Relaciones `@ManyToOne` y `@ManyToMany`
* DTOs
* Validación de datos
* Consultas derivadas de Spring Data
* Manejo de excepciones
* MySQL
* Diseño de lógica de negocio