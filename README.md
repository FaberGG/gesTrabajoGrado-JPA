# 🎓 Sistema de Gestión de Trabajos de Grado

Sistema desarrollado con Spring Boot y PostgreSQL para la gestión de trabajos de grado, estudiantes, profesores y directores

---

## 📋 Tabla de Contenidos

1. [Requisitos Previos](#-requisitos-previos)
2. [Configuración Inicial](#-configuración-inicial)
3. [Iniciar el Proyecto](#-iniciar-el-proyecto)
4. [Acceder a pgAdmin](#-acceder-a-pgadmin)
5. [Configurar Conexión en pgAdmin](#-configurar-conexión-en-pgadmin)
6. [Ver Tablas y Hacer Consultas](#-ver-tablas-y-hacer-consultas)
7. [Ejecutar la Aplicación Spring Boot](#-ejecutar-la-aplicación-spring-boot)
8. [Probar la API](#-probar-la-api)
9. [Detener los Servicios](#-detener-los-servicios)
10. [Troubleshooting](#-troubleshooting)

---

## 🔧 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- ✅ **Java 18** o superior ([Descargar aquí](https://www.oracle.com/java/technologies/downloads/))
- ✅ **Maven 3.6+** (viene incluido con IntelliJ IDEA)
- ✅ **Docker Desktop** ([Descargar aquí](https://www.docker.com/products/docker-desktop))
- ✅ **IntelliJ IDEA** (recomendado) o cualquier IDE Java
- ✅ **Git** (opcional, para clonar el repositorio)

### Verificar instalaciones:
```bash
java -version
mvn -version
docker --version
docker-compose --version
```

---

## ⚙️ Configuración Inicial

### 1. Clonar o descargar el proyecto
```bash
git clone https://github.com/FaberGG/gesTrabajoGrado-JPA.git
cd gesTrabajoGrado-JPA
```

---

## 🚀 Iniciar el Proyecto

### Paso 1: Iniciar los contenedores de Docker

Abre una terminal en la raíz del proyecto y ejecuta:
```bash
docker-compose up -d
```

- El flag `-d` ejecuta los contenedores en segundo plano (detached mode)

### Paso 2: Verificar que los contenedores estén corriendo
```bash
docker-compose ps
```

Deberías ver algo como:
```
NAME                        STATUS              PORTS
trabajogrado-postgres       Up 10 seconds       0.0.0.0:5432->5432/tcp
trabajogrado-pgadmin        Up 10 seconds       0.0.0.0:5050->80/tcp
```

### Paso 3: Verificar los logs (opcional)
```bash
# Ver logs de PostgreSQL
docker-compose logs postgres

# Ver logs en tiempo real
docker-compose logs -f postgres
```

---

## 🗄️ Acceder a pgAdmin

pgAdmin es una interfaz gráfica web para administrar PostgreSQL de forma visual.

### 1. Abrir pgAdmin en el navegador
```
http://localhost:5050
```

### 2. Iniciar sesión

- **Email:** `admin@trabajogrado.com`
- **Password:** `admin123`


---

## 🔗 Configurar Conexión en pgAdmin

Una vez dentro de pgAdmin, necesitas registrar el servidor PostgreSQL:

### Paso 1: Registrar un nuevo servidor

1. En el panel izquierdo, haz **clic derecho** en **"Servers"**
2. Selecciona **"Register" → "Server..."**

### Paso 2: Configurar la pestaña "General"

En la pestaña **"General"**:

| Campo | Valor |
|-------|-------|
| **Name** | `TrabajoGrado Local` |


### Paso 3: Configurar la pestaña "Connection"

En la pestaña **"Connection"**:

| Campo | Valor |
|-------|-------|
| **Host name/address** | `postgres` |
| **Port** | `5432` |
| **Maintenance database** | `trabajogrado_db` |
| **Username** | `admin` |
| **Password** | `admin123` |
| **Save password?** | ✅ Marcado |

> ⚠️ **IMPORTANTE:** El host debe ser `postgres` (nombre del servicio en Docker), NO `localhost`

### Paso 4: Guardar la configuración

Haz clic en el botón **"Save"** en la parte inferior.

### Resultado esperado:

En el panel izquierdo deberías ver:
```
Servers
└── TrabajoGrado Local
    └── Databases
        └── trabajogrado_db
```

---

## 📊 Ver Tablas y Hacer Consultas

### Método 1: Explorar visualmente las tablas

1. En el panel izquierdo, navega a:
```
   Servers → TrabajoGrado Local → Databases → trabajogrado_db → Schemas → public → Tables
```

2. Verás las tablas creadas por JPA:
    - `persona`
    - `estudiante`
    - `profesor`
    - `trabajo_grado`
    - `tipo_trabajo_grado`
    - etc.

3. **Ver datos de una tabla:**
    - Haz clic derecho en una tabla (ej: `estudiante`)
    - Selecciona **"View/Edit Data" → "All Rows"**

4. **Ver estructura de una tabla:**
    - Haz clic derecho en una tabla
    - Selecciona **"Properties"**
    - Ve a la pestaña **"Columns"**

### Método 2: Ejecutar consultas SQL

1. Haz clic derecho en **`trabajogrado_db`**
2. Selecciona **"Query Tool"**

3. Escribe tus consultas SQL:
```sql
-- Listar todas las tablas
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';

-- Ver todos los estudiantes
SELECT * FROM estudiante;

-- Ver todos los profesores
SELECT * FROM profesor;

-- Ver todos los trabajos de grado
SELECT * FROM trabajo_grado;

-- Ver trabajos con sus directores
SELECT 
    tg.titulo, 
    p.nombres, 
    p.apellidos 
FROM trabajo_grado tg
JOIN profesor p ON tg.director_id = p.id;

-- Contar trabajos por tipo
SELECT tipo, COUNT(*) as total
FROM trabajo_grado
GROUP BY tipo;
```

4. Haz clic en el botón **▶️ (Execute/Refresh)** o presiona **F5**

5. Los resultados aparecerán en la parte inferior

### Método 3: Usar el terminal (Opcional)

También puedes acceder directamente desde la línea de comandos:
```bash
docker exec -it trabajogrado-postgres psql -U admin -d trabajogrado_db
```

Comandos útiles en psql:
```sql
\dt              -- Listar todas las tablas
\d persona       -- Ver estructura de la tabla persona
\d+ estudiante   -- Ver estructura detallada
SELECT * FROM estudiante LIMIT 10;  -- Consultar datos
\q               -- Salir
```

---

## ▶️ Ejecutar la Aplicación Spring Boot

### Opción 1: Desde IntelliJ IDEA (Recomendado)

1. **Abrir el proyecto:**
    - `File → Open` → Selecciona la carpeta del proyecto

2. **Esperar a que Maven descargue las dependencias**
    - Verás una barra de progreso en la parte inferior

3. **Ejecutar la aplicación:**
    - Busca la clase principal: `TrabajoGradoApplication.java`
    - Haz clic derecho → **"Run 'TrabajoGradoApplication'"**
    - O presiona el botón verde ▶️ en la parte superior

4. **Verificar el inicio:**
    - En la consola deberías ver:
```
   Started TrabajoGradoApplication in X seconds
```

### Opción 2: Desde la terminal
```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

### Opción 3: Ejecutar el JAR
```bash
# Generar el JAR
mvn clean package

# Ejecutar
java -jar target/trabajo-grado-app-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Probar la API

Una vez que la aplicación esté corriendo, la API estará disponible en:
```
http://localhost:8080
```

---

## 🛑 Detener los Servicios

### Detener la aplicación Spring Boot

- **En IntelliJ:** Haz clic en el botón rojo ⬛ "Stop" en la parte superior
- **En terminal:** Presiona `Ctrl + C`

### Detener los contenedores de Docker
```bash
# Detener sin eliminar los contenedores
docker-compose stop

# Detener y eliminar los contenedores (los datos persisten)
docker-compose down

# Detener, eliminar contenedores Y BORRAR TODOS LOS DATOS ⚠️
docker-compose down -v
```

### Verificar que todo esté detenido
```bash
docker-compose ps
```

No debería haber contenedores en ejecución.

---
 

## 📚 Recursos Adicionales

- [Documentación de Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Documentación de Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Documentación de PostgreSQL](https://www.postgresql.org/docs/)
- [Documentación de Docker Compose](https://docs.docker.com/compose/)

---

## 👥 Equipo de Desarrollo

- **Faber:** Configuración del entorno y Docker
- **Sofía:** Modelo UML y diseño de relaciones
- **Hener:** Entidades, repositorios y DataLoader
- **Liz:** DTOs y servicios
- **Yeimi:** Controladores y pruebas REST

---

## 📄 Licencia

Este proyecto es desarrollado con fines académicos para el curso de Ingeniería de Software II - Universidad del Cauca.

---

## 📞 Contacto

Para dudas o problemas, contactar al equipo de desarrollo.

---
