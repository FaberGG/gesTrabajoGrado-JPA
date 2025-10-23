# Sistema de Gestión de Trabajos de Grado - Implementación JPA

Este documento describe la implementación del sistema de gestión de trabajos de grado universitarios utilizando Spring Boot, JPA, PostgreSQL y Lombok.

## 📋 Descripción del Proyecto

Este sistema permite gestionar trabajos de grado universitarios, incluyendo estudiantes, profesores, tipos de trabajos, y el seguimiento de los formatos asociados. La implementación se enfoca en la capa de persistencia utilizando JPA para el mapeo objeto-relacional.

## 🔧 Tecnologías Utilizadas

- **Spring Boot 3.5.6**
- **Java 21**
- **PostgreSQL 15** (en contenedor Docker)
- **Spring Data JPA**
- **Lombok**
- **Maven**
- **Docker & Docker Compose**

## 📁 Estructura del Proyecto

```
src/main/java/co/unicauca/gestrabajogradojpa/
├── model/               # Entidades JPA y enumeraciones
├── repository/          # Interfaces de repositorio Spring Data JPA
├── controller/          # Controladores REST
└── config/              # Configuración y carga inicial de datos
```

## 🗄️ Modelo de Datos

### Enumeraciones:
- **EnumProgram**: Programas académicos (INGENIERIA_DE_SISTEMAS, etc.)
- **EnumModalidad**: Tipos de modalidad (INVESTIGACION, PRACTICA_PROFESIONAL, etc.)
- **EnumEstadoProyecto**: Estados del proyecto (EN_PROCESO, APROBADO, etc.)
- **EnumEstadoFormato**: Estados del formato (PENDIENTE, APROBADO, etc.)

### Entidades Principales:
- **Persona** (clase abstracta): Información básica de persona
- **Estudiante** (hereda de Persona): Datos específicos del estudiante
- **Profesor** (hereda de Persona): Datos específicos del profesor
- **TrabajoGrado**: Entidad central que gestiona los trabajos de grado
- **TipoTrabajo**: Tipos de trabajo disponibles
- **FormatoA**: Documentos de seguimiento asociados al trabajo

## 🔄 Relaciones JPA Implementadas

- **Herencia**: Persona → Estudiante, Profesor (estrategia JOINED)
- **ManyToOne**: TrabajoGrado → Estudiante, Profesor, TipoTrabajo
- **ManyToMany**: TrabajoGrado ↔ Profesor (codirectores)
- **OneToMany**: TrabajoGrado → FormatoA (bidireccional)

## 🌐 Endpoints REST

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/estudiantes` | Listar todos los estudiantes |
| GET | `/api/estudiantes/{id}` | Obtener estudiante por ID |
| GET | `/api/profesores` | Listar todos los profesores |
| GET | `/api/profesores/{id}` | Obtener profesor por ID |
| GET | `/api/trabajos` | Listar todos los trabajos de grado |
| GET | `/api/trabajos/{id}` | Obtener trabajo por ID |

## 💾 Base de Datos

La configuración de la base de datos se realiza a través de Docker Compose:

```yaml
# Configuración PostgreSQL
postgres:
  image: postgres:15-alpine
  ports:
    - "5433:5432"
  environment:
    POSTGRES_DB: trabajogrado_db
    POSTGRES_USER: admin
    POSTGRES_PASSWORD: admin123
```

## ⚙️ Configuración de Spring Boot

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/trabajogrado_db
    username: admin
    password: admin123
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## 📝 Carga Inicial de Datos

El sistema incluye un DataLoader que carga datos de ejemplo:
- 3 tipos de trabajo
- 3 estudiantes
- 3 profesores
- 3 trabajos de grado
- 2 formatos A

## 🚀 Pasos para Ejecutar el Proyecto

1. **Iniciar PostgreSQL con Docker**:
   ```bash
   docker-compose up -d
   ```

2. **Compilar el proyecto**:
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

4. **Verificar los endpoints**:
   ```bash
   curl http://localhost:8080/api/estudiantes
   curl http://localhost:8080/api/profesores
   curl http://localhost:8080/api/trabajos
   ```

## 📊 Resultados de Ejecución

Al ejecutar la aplicación, verás en la consola:
```
🔄 Iniciando carga de datos de prueba...
✅ Tipos de trabajo guardados: 3
✅ Estudiantes guardados: 3
✅ Profesores guardados: 3
✅ Trabajos de grado guardados: 3
✅ Formatos A guardados: 2
✅ ¡Carga de datos completada exitosamente!
```

## 🔍 Verificación de la Base de Datos

Para verificar los datos cargados en PostgreSQL:

```sql
-- Ver todas las tablas creadas
SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';

-- Verificar datos en las tablas principales
SELECT COUNT(*) FROM estudiante;
SELECT COUNT(*) FROM profesor;
SELECT COUNT(*) FROM trabajo_grado;
```

## 📈 Diagrama de Entidades

```
   ┌───────────┐         ┌──────────┐
   │  Persona  │<────────│Estudiante│
   └─────┬─────┘         └──────────┘
         │
         │               ┌─────────┐
         └───────────────│Profesor │
                         └────┬────┘
                              │
┌────────────┐    ┌───────────┴──────────┐    ┌──────────┐
│TipoTrabajo │<───│    TrabajoGrado      │<───│ FormatoA │
└────────────┘    └──────────────────────┘    └──────────┘
```

## 📋 Consideraciones Técnicas

- **Estrategia de Herencia**: Se usa JOINED para optimizar el modelo relacional
- **Relaciones**: Se implementan con anotaciones JPA como @ManyToOne, @OneToMany
- **Validaciones**: Se utilizan anotaciones como @NotNull, @Size, etc.
- **JsonIgnore**: Se usa para evitar recursión infinita en relaciones bidireccionales

## 🤝 Contribución

Este proyecto forma parte de una implementación educativa para demostrar el uso de JPA con Spring Boot en un sistema de gestión académica.

---

Implementado: Octubre 2025
