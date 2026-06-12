# PlexSalud - API de Gestión de Turnos Médicos

> **"La salud es nuestra pasión"**

API RESTful para la gestión de turnos médicos, construida con Spring Boot y arquitectura hexagonal.

## Stack Tecnológico

- **Java 17** + **Spring Boot 3.5.3**
- **Maven** - Gestión de dependencias y build
- **PostgreSQL 16** - Base de datos relacional
- **JWT (jjwt)** - Autenticación stateless
- **Spring Security** - Control de acceso basado en roles
- **SpringDoc OpenAPI** - Documentación interactiva (Swagger UI)
- **Lombok** - Reducción de boilerplate

## Roles de Usuario

| Rol | Descripción |
|-----|-------------|
| `DOCTOR` | Profesional de la salud que gestiona sus turnos |
| `PATIENT` | Paciente que solicita turnos |
| `NURSE` | Personal de enfermería |

## Arquitectura

El proyecto sigue una **arquitectura hexagonal (puertos y adaptadores)** con diseño guiado por el dominio (DDD). Cada contexto delimitado está organizado en:

```
backend/src/main/java/com/plexsalud/plexsalud/
├── appointment/     # Gestión de turnos
├── auth/            # Autenticación y autorización
├── doctor/          # Perfiles de doctores
├── nurse/           # Perfiles de enfermería
├── patient/         # Perfiles de pacientes
├── user/            # Gestión de usuarios (kernel compartido)
├── common/          # Mappers y utilidades compartidas
└── configs/         # Configuración global (Web, OpenAPI)
```

Cada contexto sigue la misma estructura interna:

- `domain/models/` - Modelos de dominio puros (sin anotaciones de framework)
- `application/ports/in/` - Puertos de entrada (casos de uso)
- `application/ports/out/` - Puertos de salida (contratos de repositorio)
- `application/services/` - Implementación de servicios
- `infrastructure/controllers/` - Controladores REST
- `infrastructure/persistance/` - Entidades JPA y repositorios

## API Endpoints

Todos los endpoints están bajo `/api/v1/`.

### Autenticación
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/signup` | Registrar nuevo usuario |
| POST | `/auth/login` | Iniciar sesión (devuelve JWT + refresh cookie) |
| GET | `/auth/logout` | Cerrar sesión |
| GET | `/auth/refresh` | Renovar access token |

### Usuarios
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/users` | Listar usuarios |

### Pacientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/patients` | Crear perfil de paciente |
| GET | `/patients/self` | Obtener perfil propio |
| GET | `/patients/uuid` | Obtener paciente por UUID |

### Doctores
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/doctor` | Crear perfil de doctor |
| GET | `/doctor/self` | Obtener perfil propio |
| GET | `/doctor/uuid` | Obtener doctor por UUID |
| GET | `/doctor/specialties` | Listar especialidades |
| GET | `/doctor/doctors-by-specialty` | Filtrar doctores por especialidad |

### Enfermería
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/nurses` | Crear perfil de enfermero |
| GET | `/nurses/self` | Obtener perfil propio |
| GET | `/nurses/uuid` | Obtener enfermero por UUID |

### Turnos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/appointment/patient` | Turnos del paciente autenticado |
| GET | `/appointment/doctor` | Turnos del doctor autenticado |
| GET | `/appointment/doctor-search-by-patient` | Buscar turnos de un doctor |
| POST | `/appointment` | Crear un turno |
| DELETE | `/appointment/{uuid}` | Cancelar un turno |

### Documentación Interactiva

- **Swagger UI:** `/swagger-ui/index.html`
- **OpenAPI JSON:** `/v3/api-docs`

## Requisitos

- **Java 17+**
- **PostgreSQL 16+**
- **Maven** (o usar el wrapper `./mvnw`)

## Configuración

Las siguientes variables de entorno son requeridas:

| Variable | Descripción |
|----------|-------------|
| `POSTGRES_HOST` | Host de la base de datos |
| `POSTGRES_PORT` | Puerto de la base de datos |
| `POSTGRES_DB` | Nombre de la base de datos |
| `POSTGRES_USER` | Usuario de la base de datos |
| `POSTGRES_PASSWORD` | Contraseña de la base de datos |
| `JWT_SECRET_KEY` | Clave secreta JWT (en Base64) |
| `JWT_EXPIRATION_TIME` | Duración del access token (ms) |
| `JWT_REFRESH_EXPIRATION_TIME` | Duración del refresh token (ms) |
| `ALLOWED_ORIGIN` | Orígenes permitidos para CORS |
| `SERVER_PORT` | Puerto del servidor (default: 8080) |

### Ejemplo (.env)

```env
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=plexsalud_db
POSTGRES_USER=plexsalud_user
POSTGRES_PASSWORD=plexsalud_password
JWT_SECRET_KEY=3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b
JWT_EXPIRATION_TIME=3600000
JWT_REFRESH_EXPIRATION_TIME=86400000
ALLOWED_ORIGIN=http://localhost:4200
SERVER_PORT=8080
```

## Ejecución

### Desarrollo local

```bash
cd backend
./mvnw spring-boot:run
```

### Docker Compose

```bash
cd .devcontainer
docker-compose up
```

### Build y ejecución del JAR

```bash
cd backend
./mvnw clean package -DskipTests
java -jar target/plexsalud-0.0.1-SNAPSHOT.jar
```

## Licencia

[MIT](LICENSE)
