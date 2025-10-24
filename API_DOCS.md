# 📚 Documentación de Endpoints - API Sistema de Gestión de Trabajos de Grado

## 📑 Tabla de Contenidos

1. [Información General](#información-general)
2. [Estudiantes](#-estudiantes)
3. [Profesores](#-profesores)
4. [Tipos de Trabajo](#-tipos-de-trabajo)
5. [Trabajos de Grado](#-trabajos-de-grado)
6. [Códigos de Estado HTTP](#-códigos-de-estado-http)
7. [Guía de Pruebas en Postman](#-guía-de-pruebas-en-postman)

---

## Información General

### Base URL
```
http://localhost:8080/api
```

### Enumeraciones del Sistema

#### EnumProgram (Programas Académicos)
- `INGENIERIA_SISTEMAS`
- `INGENIERIA_ELECTRONICA`
- `INGENIERIA_CIVIL`
- `FISICA`
- `MATEMATICAS`

#### EnumModalidad (Modalidades de Trabajo de Grado)
- `MONOGRAFIA`
- `PROYECTO_INVESTIGACION`
- `PASANTIA`
- `EMPRENDIMIENTO`

### ⚠️ Importante: Manejo de Ciclos JSON

Para evitar referencias circulares infinitas entre entidades relacionadas (TrabajoGrado ↔ Estudiante ↔ Profesor), la API utiliza **DTOs (Data Transfer Objects)**:

- Las respuestas NO incluyen las relaciones inversas completas
- Se devuelven solo los datos necesarios sin ciclos
- Al crear/actualizar, se envían solo los IDs de las relaciones

---

## 👨‍🎓 Estudiantes

Gestiona la información de los estudiantes del sistema.

**Endpoint base:** `/api/estudiantes`

### 1. Obtener todos los estudiantes

Obtiene una lista completa de todos los estudiantes registrados en el sistema.

**Endpoint:**
```http
GET /api/estudiantes
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "identificacion": "1234567890",
    "nombres": "Juan Carlos",
    "apellidos": "Pérez García",
    "email": "juan.perez@unicauca.edu.co",
    "celular": "3001234567",
    "programa": "INGENIERIA_SISTEMAS",
    "codigoEstudiante": "100123456"
  },
  {
    "id": 2,
    "identificacion": "9876543210",
    "nombres": "Ana María",
    "apellidos": "López Rodríguez",
    "email": "ana.lopez@unicauca.edu.co",
    "celular": "3109876543",
    "programa": "INGENIERIA_ELECTRONICA",
    "codigoEstudiante": "100654321"
  }
]
```

---

### 2. Obtener estudiante por ID

Obtiene la información detallada de un estudiante específico mediante su ID.

**Endpoint:**
```http
GET /api/estudiantes/{id}
```

**Parámetros de URL:**

| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| id | Long | ID único del estudiante | 1 |

**Ejemplo de solicitud:**
```http
GET /api/estudiantes/1
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "identificacion": "1234567890",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "email": "juan.perez@unicauca.edu.co",
  "celular": "3001234567",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100123456"
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/estudiantes/999"
}
```

---

### 3. Crear estudiante

Crea un nuevo estudiante en el sistema.

**Endpoint:**
```http
POST /api/estudiantes
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "identificacion": "1234567890",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "email": "juan.perez@unicauca.edu.co",
  "celular": "3001234567",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100123456"
}
```

**Campos del modelo:**

| Campo | Tipo | Descripción | Requerido | Validación |
|-------|------|-------------|-----------|------------|
| identificacion | String | Número de cédula | Sí | Único |
| nombres | String | Nombres del estudiante | Sí | No vacío |
| apellidos | String | Apellidos del estudiante | Sí | No vacío |
| email | String | Correo institucional | Sí | Formato email, único |
| celular | String | Número de celular | No | - |
| programa | String | Programa académico | Sí | Valor de EnumProgram |
| codigoEstudiante | String | Código estudiantil | Sí | Único |

**Respuesta exitosa (200 OK):**
```json
{
  "id": 3,
  "identificacion": "1234567890",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "email": "juan.perez@unicauca.edu.co",
  "celular": "3001234567",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100123456"
}
```

---

### 4. Actualizar estudiante

Actualiza la información de un estudiante existente. **IMPORTANTE:** Los campos `codigoEstudiante` y `programa` NO pueden ser modificados.

**Endpoint:**
```http
PUT /api/estudiantes/{id}
```

**Campos modificables:**
- ✅ `nombres`
- ✅ `apellidos`
- ✅ `email`
- ✅ `celular`

**Campos NO modificables:**
- ❌ `codigoEstudiante`
- ❌ `programa`

**Body (JSON):**
```json
{
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "email": "juan.perez.nuevo@unicauca.edu.co",
  "celular": "3009876543"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "identificacion": "1234567890",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "email": "juan.perez.nuevo@unicauca.edu.co",
  "celular": "3009876543",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100123456"
}
```

**⚠️ Respuesta de error si se intenta modificar campos bloqueados (403 Forbidden):**
```json
{
  "error": "Campos no modificables",
  "mensaje": "No tienes permiso para modificar: codigoEstudiante y programa",
  "camposBloqueados": ["codigoEstudiante", "programa"],
  "camposModificables": ["nombres", "apellidos", "email", "celular"]
}
```

**Ejemplo de solicitud incorrecta:**
```json
{
  "nombres": "Juan Carlos",
  "programa": "INGENIERIA_CIVIL",
  "codigoEstudiante": "100999999"
}
```
> ❌ Esto generará error 403

---

### 5. Eliminar estudiante

Elimina un estudiante del sistema.

**Endpoint:**
```http
DELETE /api/estudiantes/{id}
```

**Respuesta exitosa (200 OK):**
```
Status: 200 OK
(Sin contenido en el body)
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/estudiantes/999"
}
```

---

## 👨‍🏫 Profesores

Gestiona la información de los profesores y directores de trabajos de grado.

**Endpoint base:** `/api/profesores`

### 1. Obtener todos los profesores

Obtiene una lista completa de todos los profesores registrados.

**Endpoint:**
```http
GET /api/profesores
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "identificacion": "9876543210",
    "nombres": "María Fernanda",
    "apellidos": "González López",
    "email": "maria.gonzalez@unicauca.edu.co",
    "celular": "3101234567",
    "programa": "INGENIERIA_SISTEMAS"
  },
  {
    "id": 2,
    "identificacion": "5555555555",
    "nombres": "Carlos Alberto",
    "apellidos": "Ramírez Torres",
    "email": "carlos.ramirez@unicauca.edu.co",
    "celular": "3205551234",
    "programa": "INGENIERIA_ELECTRONICA"
  }
]
```

---

### 2. Obtener profesor por ID

Obtiene la información detallada de un profesor específico.

**Endpoint:**
```http
GET /api/profesores/{id}
```

**Parámetros de URL:**

| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| id | Long | ID único del profesor | 1 |

**Ejemplo de solicitud:**
```http
GET /api/profesores/1
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "identificacion": "9876543210",
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez@unicauca.edu.co",
  "celular": "3101234567",
  "programa": "INGENIERIA_SISTEMAS"
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/profesores/999"
}
```

---

### 3. Crear profesor

Crea un nuevo profesor en el sistema.

**Endpoint:**
```http
POST /api/profesores
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "identificacion": "9876543210",
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez@unicauca.edu.co",
  "celular": "3101234567",
  "programa": "INGENIERIA_SISTEMAS"
}
```

**Campos del modelo:**

| Campo | Tipo | Descripción | Requerido | Validación |
|-------|------|-------------|-----------|------------|
| identificacion | String | Número de cédula | Sí | Único |
| nombres | String | Nombres del profesor | Sí | No vacío |
| apellidos | String | Apellidos del profesor | Sí | No vacío |
| email | String | Correo institucional | Sí | Formato email, único |
| celular | String | Número de celular | No | - |
| programa | String | Programa al que pertenece | Sí | Valor de EnumProgram |

**Respuesta exitosa (200 OK):**
```json
{
  "id": 3,
  "identificacion": "9876543210",
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez@unicauca.edu.co",
  "celular": "3101234567",
  "programa": "INGENIERIA_SISTEMAS"
}
```

---

### 4. Actualizar profesor

Actualiza la información de un profesor existente. **IMPORTANTE:** El campo `programa` NO puede ser modificado.

**Endpoint:**
```http
PUT /api/profesores/{id}
```

**Campos modificables:**
- ✅ `nombres`
- ✅ `apellidos`
- ✅ `email`
- ✅ `celular`

**Campos NO modificables:**
- ❌ `programa`

**Body (JSON):**
```json
{
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez.nuevo@unicauca.edu.co",
  "celular": "3109876543"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "identificacion": "9876543210",
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez.nuevo@unicauca.edu.co",
  "celular": "3109876543",
  "programa": "INGENIERIA_SISTEMAS"
}
```

**⚠️ Respuesta de error si se intenta modificar el programa (403 Forbidden):**
```json
{
  "error": "Campo no modificable",
  "mensaje": "No tienes permiso para modificar el programa",
  "camposBloqueados": ["programa"],
  "camposModificables": ["nombres", "apellidos", "email", "celular"]
}
```

**Ejemplo de solicitud incorrecta:**
```json
{
  "nombres": "María Fernanda",
  "programa": "INGENIERIA_CIVIL"
}
```
> ❌ Esto generará error 403

---

### 5. Eliminar profesor

Elimina un profesor del sistema.

**Endpoint:**
```http
DELETE /api/profesores/{id}
```

**Respuesta exitosa (200 OK):**
```
Status: 200 OK
(Sin contenido en el body)
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/profesores/999"
}
```

---

## 📋 Tipos de Trabajo

Gestiona los diferentes tipos de trabajos de grado disponibles en el sistema.

**Endpoint base:** `/api/tipos`

### 1. Obtener todos los tipos de trabajo

Lista todos los tipos de trabajos de grado configurados.

**Endpoint:**
```http
GET /api/tipos
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Monografía",
    "maxEstudiantes": 1
  },
  {
    "id": 2,
    "nombre": "Proyecto de Investigación",
    "maxEstudiantes": 2
  },
  {
    "id": 3,
    "nombre": "Pasantía",
    "maxEstudiantes": 1
  },
  {
    "id": 4,
    "nombre": "Emprendimiento",
    "maxEstudiantes": 3
  }
]
```

---

### 2. Obtener tipo de trabajo por ID

Obtiene la información de un tipo de trabajo específico.

**Endpoint:**
```http
GET /api/tipos/{id}
```

**Parámetros de URL:**

| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| id | Long | ID único del tipo de trabajo | 1 |

**Ejemplo de solicitud:**
```http
GET /api/tipos/1
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Monografía",
  "maxEstudiantes": 1
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/tipos/999"
}
```

---

### 3. Crear tipo de trabajo

Crea un nuevo tipo de trabajo de grado.

**Endpoint:**
```http
POST /api/tipos
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "nombre": "Proyecto Aplicado",
  "maxEstudiantes": 2
}
```

**Campos del modelo:**

| Campo | Tipo | Descripción | Requerido | Validación |
|-------|------|-------------|-----------|------------|
| nombre | String | Nombre del tipo de trabajo | Sí | No vacío |
| maxEstudiantes | Integer | Máximo número de estudiantes permitidos | Sí | Mayor a 0 |

**Respuesta exitosa (200 OK):**
```json
{
  "id": 5,
  "nombre": "Proyecto Aplicado",
  "maxEstudiantes": 2
}
```

---

### 4. Actualizar tipo de trabajo

Actualiza la información de un tipo de trabajo existente.

**Endpoint:**
```http
PUT /api/tipos/{id}
```

**Body (JSON):**
```json
{
  "nombre": "Proyecto Aplicado e Innovación",
  "maxEstudiantes": 3
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 5,
  "nombre": "Proyecto Aplicado e Innovación",
  "maxEstudiantes": 3
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/tipos/999"
}
```

---

### 5. Eliminar tipo de trabajo

Elimina un tipo de trabajo del sistema.

**Endpoint:**
```http
DELETE /api/tipos/{id}
```

**Respuesta exitosa (200 OK):**
```
Status: 200 OK
(Sin contenido en el body)
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/tipos/999"
}
```

---

## 📝 Trabajos de Grado

Gestiona los trabajos de grado de los estudiantes, incluyendo su asignación a directores y seguimiento.

**Endpoint base:** `/api/trabajos`

### 1. Obtener todos los trabajos de grado

Lista todos los trabajos de grado registrados en el sistema.

**Endpoint:**
```http
GET /api/trabajos
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Sistema de recomendación basado en IA",
    "modalidad": "PROYECTO_INVESTIGACION",
    "fechaCreacion": "2025-02-01",
    "objetivoGeneral": "Desarrollar un sistema de recomendaciones inteligente",
    "objetivosEspecificos": [
      "Analizar algoritmos de ML",
      "Implementar el sistema",
      "Evaluar resultados"
    ],
    "estado": "EN_DESARROLLO",
    "numeroIntentos": 1,
    "estudiante1": {
      "id": 1,
      "nombres": "Juan Carlos",
      "apellidos": "Pérez García"
    },
    "estudiante2": null,
    "director": {
      "id": 1,
      "nombres": "María Fernanda",
      "apellidos": "González López"
    },
    "codirectores": [],
    "tipoTrabajo": {
      "id": 2,
      "nombre": "Proyecto de Investigación"
    }
  }
]
```

---

### 2. Obtener trabajo de grado por ID

Obtiene la información detallada de un trabajo de grado específico.

**Endpoint:**
```http
GET /api/trabajos/{id}
```

**Parámetros de URL:**

| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| id | Integer | ID único del trabajo de grado | 1 |

**Ejemplo de solicitud:**
```http
GET /api/trabajos/1
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "titulo": "Sistema de recomendación basado en IA",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar un sistema de recomendaciones inteligente usando algoritmos de machine learning",
  "objetivosEspecificos": [
    "Analizar y comparar algoritmos de ML existentes",
    "Implementar el sistema propuesto",
    "Evaluar y validar resultados con métricas establecidas"
  ],
  "estado": "EN_DESARROLLO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": 1,
    "identificacion": "1234567890",
    "nombres": "Juan Carlos",
    "apellidos": "Pérez García",
    "email": "juan.perez@unicauca.edu.co"
  },
  "estudiante2": null,
  "director": {
    "id": 1,
    "identificacion": "9876543210",
    "nombres": "María Fernanda",
    "apellidos": "González López",
    "email": "maria.gonzalez@unicauca.edu.co"
  },
  "codirectores": [
    {
      "id": 2,
      "nombres": "Carlos Alberto",
      "apellidos": "Ramírez Torres"
    }
  ],
  "tipoTrabajo": {
    "id": 2,
    "nombre": "Proyecto de Investigación",
    "maxEstudiantes": 2
  }
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/trabajos/999"
}
```

---

### 3. Crear trabajo de grado

Crea un nuevo trabajo de grado y lo asigna a estudiantes y director.

**Endpoint:**
```http
POST /api/trabajos
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "titulo": "Sistema de recomendación basado en IA",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar un sistema de recomendaciones inteligente",
  "objetivosEspecificos": [
    "Analizar algoritmos de ML",
    "Implementar el sistema",
    "Evaluar resultados"
  ],
  "estado": "EN_DESARROLLO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": 1
  },
  "estudiante2": null,
  "director": {
    "id": 1
  },
  "codirectores": [
    {
      "id": 2
    }
  ],
  "tipoTrabajo": {
    "id": 2
  }
}
```

**Campos del modelo:**

| Campo | Tipo | Descripción | Requerido | Observaciones |
|-------|------|-------------|-----------|---------------|
| titulo | String | Título del trabajo | Sí | Máx. 500 caracteres |
| modalidad | String | Modalidad del trabajo | Sí | Valor de EnumModalidad |
| fechaCreacion | Date | Fecha de creación | Sí | Formato: YYYY-MM-DD |
| objetivoGeneral | String | Objetivo principal | Sí | - |
| objetivosEspecificos | Array[String] | Lista de objetivos | Sí | Mínimo 1 objetivo |
| estado | String | Estado actual | Sí | Ej: EN_DESARROLLO, COMPLETADO |
| numeroIntentos | Integer | Número de intentos | Sí | Por defecto: 1 |
| estudiante1 | Object | Primer estudiante | Sí | Solo enviar {id: X} |
| estudiante2 | Object | Segundo estudiante | No | Solo si aplica, enviar {id: X} |
| director | Object | Director del trabajo | Sí | Solo enviar {id: X} |
| codirectores | Array[Object] | Lista de codirectores | No | Enviar [{id: X}, {id: Y}] |
| tipoTrabajo | Object | Tipo de trabajo | Sí | Solo enviar {id: X} |

**Respuesta exitosa (200 OK):**
```json
{
  "id": 3,
  "titulo": "Sistema de recomendación basado en IA",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar un sistema de recomendaciones inteligente",
  "objetivosEspecificos": [
    "Analizar algoritmos de ML",
    "Implementar el sistema",
    "Evaluar resultados"
  ],
  "estado": "EN_DESARROLLO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": 1,
    "nombres": "Juan Carlos",
    "apellidos": "Pérez García"
  },
  "estudiante2": null,
  "director": {
    "id": 1,
    "nombres": "María Fernanda",
    "apellidos": "González López"
  },
  "codirectores": [
    {
      "id": 2,
      "nombres": "Carlos Alberto",
      "apellidos": "Ramírez Torres"
    }
  ],
  "tipoTrabajo": {
    "id": 2,
    "nombre": "Proyecto de Investigación"
  }
}
```

---

### 4. Actualizar trabajo de grado

Actualiza la información de un trabajo de grado existente.

**Endpoint:**
```http
PUT /api/trabajos/{id}
```

**Body (JSON) - Todos los campos son actualizables:**
```json
{
  "titulo": "Sistema de recomendación basado en IA - Actualizado",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar e implementar un sistema avanzado de recomendaciones",
  "objetivosEspecificos": [
    "Analizar y comparar algoritmos de ML",
    "Diseñar la arquitectura del sistema",
    "Implementar el sistema completo",
    "Evaluar y validar resultados con métricas"
  ],
  "estado": "COMPLETADO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": 1
  },
  "estudiante2": {
    "id": 2
  },
  "director": {
    "id": 1
  },
  "codirectores": [
    {
      "id": 2
    },
    {
      "id": 3
    }
  ],
  "tipoTrabajo": {
    "id": 2
  }
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "titulo": "Sistema de recomendación basado en IA - Actualizado",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar e implementar un sistema avanzado de recomendaciones",
  "objetivosEspecificos": [
    "Analizar y comparar algoritmos de ML",
    "Diseñar la arquitectura del sistema",
    "Implementar el sistema completo",
    "Evaluar y validar resultados con métricas"
  ],
  "estado": "COMPLETADO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": 1,
    "nombres": "Juan Carlos",
    "apellidos": "Pérez García"
  },
  "estudiante2": {
    "id": 2,
    "nombres": "Ana María",
    "apellidos": "López Rodríguez"
  },
  "director": {
    "id": 1,
    "nombres": "María Fernanda",
    "apellidos": "González López"
  },
  "codirectores": [
    {
      "id": 2,
      "nombres": "Carlos Alberto",
      "apellidos": "Ramírez Torres"
    },
    {
      "id": 3,
      "nombres": "Pedro José",
      "apellidos": "Martínez Silva"
    }
  ],
  "tipoTrabajo": {
    "id": 2,
    "nombre": "Proyecto de Investigación"
  }
}
```

---

### 5. Eliminar trabajo de grado

Elimina un trabajo de grado del sistema.

**Endpoint:**
```http
DELETE /api/trabajos/{id}
```

**Respuesta exitosa (200 OK):**
```
Status: 200 OK
(Sin contenido en el body)
```

**Respuesta de error (404 Not Found):**
```json
{
  "timestamp": "2025-10-24T10:30:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/trabajos/999"
}
```

---

## 📊 Códigos de Estado HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| **200** | OK | Operación exitosa (GET, PUT, DELETE) |
| **201** | Created | Recurso creado exitosamente (POST) - *Nota: Actualmente el API retorna 200* |
| **400** | Bad Request | Solicitud incorrecta (validación fallida) |
| **403** | Forbidden | Intento de modificar campos no permitidos |
| **404** | Not Found | Recurso no encontrado |
| **409** | Conflict | Conflicto (ej: violación de restricción única) |
| **500** | Internal Server Error | Error interno del servidor |

---

## 🧪 Guía de Pruebas en Postman

### Configuración Inicial

#### 1. Crear un entorno (Environment)

En Postman, crea un nuevo entorno con estas variables:

| Variable | Valor Inicial | Descripción |
|----------|---------------|-------------|
| `base_url` | `http://localhost:8080/api` | URL base del API |
| `estudiante1_id` | (vacío) | Se guardará después de crear |
| `estudiante2_id` | (vacío) | Se guardará después de crear |
| `profesor1_id` | (vacío) | Se guardará después de crear |
| `profesor2_id` | (vacío) | Se guardará después de crear |
| `tipo_trabajo_id` | (vacío) | Se guardará después de crear |
| `trabajo_id` | (vacío) | Se guardará después de crear |

---

### 📝 Secuencia de Pruebas Recomendada

#### **PASO 1: Crear Tipos de Trabajo**

```http
POST {{base_url}}/tipos
Content-Type: application/json

{
  "nombre": "Proyecto de Investigación",
  "maxEstudiantes": 2
}
```

**Script Post-response (Tests tab):**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("tipo_trabajo_id", jsonData.id);
    pm.test("Tipo de trabajo creado", function() {
        pm.expect(jsonData.id).to.exist;
    });
}
```

---

#### **PASO 2: Crear Estudiantes**

**Estudiante 1:**
```http
POST {{base_url}}/estudiantes
Content-Type: application/json

{
  "identificacion": "1234567890",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "email": "juan.perez@unicauca.edu.co",
  "celular": "3001234567",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100123456"
}
```

**Script Post-response:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("estudiante1_id", jsonData.id);
    pm.test("Estudiante 1 creado", function() {
        pm.expect(jsonData.id).to.exist;
        pm.expect(jsonData.nombres).to.eql("Juan Carlos");
    });
}
```

**Estudiante 2:**
```http
POST {{base_url}}/estudiantes
Content-Type: application/json

{
  "identificacion": "9876543210",
  "nombres": "Ana María",
  "apellidos": "López Rodríguez",
  "email": "ana.lopez@unicauca.edu.co",
  "celular": "3109876543",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100654321"
}
```

**Script Post-response:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("estudiante2_id", jsonData.id);
}
```

---

#### **PASO 3: Crear Profesores**

**Profesor 1 (Director):**
```http
POST {{base_url}}/profesores
Content-Type: application/json

{
  "identificacion": "5555555555",
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez@unicauca.edu.co",
  "celular": "3101234567",
  "programa": "INGENIERIA_SISTEMAS"
}
```

**Script Post-response:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("profesor1_id", jsonData.id);
}
```

**Profesor 2 (Codirector):**
```http
POST {{base_url}}/profesores
Content-Type: application/json

{
  "identificacion": "6666666666",
  "nombres": "Carlos Alberto",
  "apellidos": "Ramírez Torres",
  "email": "carlos.ramirez@unicauca.edu.co",
  "celular": "3205551234",
  "programa": "INGENIERIA_SISTEMAS"
}
```

**Script Post-response:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("profesor2_id", jsonData.id);
}
```

---

#### **PASO 4: Crear Trabajo de Grado**

```http
POST {{base_url}}/trabajos
Content-Type: application/json

{
  "titulo": "Sistema de recomendación basado en IA",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar un sistema de recomendaciones inteligente",
  "objetivosEspecificos": [
    "Analizar algoritmos de ML existentes",
    "Implementar el sistema propuesto",
    "Evaluar y validar resultados"
  ],
  "estado": "EN_DESARROLLO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": {{estudiante1_id}}
  },
  "estudiante2": {
    "id": {{estudiante2_id}}
  },
  "director": {
    "id": {{profesor1_id}}
  },
  "codirectores": [
    {
      "id": {{profesor2_id}}
    }
  ],
  "tipoTrabajo": {
    "id": {{tipo_trabajo_id}}
  }
}
```

**Script Post-response:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("trabajo_id", jsonData.id);
    pm.test("Trabajo de grado creado", function() {
        pm.expect(jsonData.titulo).to.exist;
        pm.expect(jsonData.estudiante1).to.exist;
        pm.expect(jsonData.director).to.exist;
    });
}
```

---

#### **PASO 5: Consultar Datos Creados**

**Ver todos los trabajos:**
```http
GET {{base_url}}/trabajos
```

**Ver trabajo específico:**
```http
GET {{base_url}}/trabajos/{{trabajo_id}}
```

**Ver todos los estudiantes:**
```http
GET {{base_url}}/estudiantes
```

**Ver todos los profesores:**
```http
GET {{base_url}}/profesores
```

---

### 🔄 Pruebas de Actualización

#### Actualizar Estudiante (Campos Permitidos)

```http
PUT {{base_url}}/estudiantes/{{estudiante1_id}}
Content-Type: application/json

{
  "nombres": "Juan Carlos Actualizado",
  "apellidos": "Pérez García",
  "email": "juan.perez.nuevo@unicauca.edu.co",
  "celular": "3009999999"
}
```

**Respuesta esperada:** 200 OK con los datos actualizados.

---

#### Actualizar Estudiante (Intentar Modificar Campos Bloqueados)

```http
PUT {{base_url}}/estudiantes/{{estudiante1_id}}
Content-Type: application/json

{
  "nombres": "Juan Carlos",
  "programa": "INGENIERIA_CIVIL",
  "codigoEstudiante": "100999999"
}
```

**Respuesta esperada:** 403 Forbidden
```json
{
  "error": "Campos no modificables",
  "mensaje": "No tienes permiso para modificar: codigoEstudiante y programa",
  "camposBloqueados": ["codigoEstudiante", "programa"],
  "camposModificables": ["nombres", "apellidos", "email", "celular"]
}
```

---

#### Actualizar Profesor (Campo Permitido)

```http
PUT {{base_url}}/profesores/{{profesor1_id}}
Content-Type: application/json

{
  "nombres": "María Fernanda",
  "apellidos": "González López",
  "email": "maria.gonzalez.actualizado@unicauca.edu.co",
  "celular": "3101111111"
}
```

**Respuesta esperada:** 200 OK con los datos actualizados.

---

#### Actualizar Profesor (Intentar Modificar Campo Bloqueado)

```http
PUT {{base_url}}/profesores/{{profesor1_id}}
Content-Type: application/json

{
  "nombres": "María Fernanda",
  "programa": "INGENIERIA_ELECTRONICA"
}
```

**Respuesta esperada:** 403 Forbidden
```json
{
  "error": "Campo no modificable",
  "mensaje": "No tienes permiso para modificar el programa",
  "camposBloqueados": ["programa"],
  "camposModificables": ["nombres", "apellidos", "email", "celular"]
}
```

---

#### Actualizar Trabajo de Grado

```http
PUT {{base_url}}/trabajos/{{trabajo_id}}
Content-Type: application/json

{
  "titulo": "Sistema de recomendación basado en IA - Versión Final",
  "modalidad": "PROYECTO_INVESTIGACION",
  "fechaCreacion": "2025-02-01",
  "objetivoGeneral": "Desarrollar e implementar un sistema avanzado de recomendaciones",
  "objetivosEspecificos": [
    "Analizar y comparar algoritmos de ML",
    "Diseñar la arquitectura del sistema",
    "Implementar el sistema completo",
    "Evaluar resultados con métricas de precisión y recall"
  ],
  "estado": "COMPLETADO",
  "numeroIntentos": 1,
  "estudiante1": {
    "id": {{estudiante1_id}}
  },
  "estudiante2": {
    "id": {{estudiante2_id}}
  },
  "director": {
    "id": {{profesor1_id}}
  },
  "codirectores": [
    {
      "id": {{profesor2_id}}
    }
  ],
  "tipoTrabajo": {
    "id": {{tipo_trabajo_id}}
  }
}
```

---

### ❌ Pruebas de Eliminación

**Eliminar Tipo de Trabajo:**
```http
DELETE {{base_url}}/tipos/{{tipo_trabajo_id}}
```

**Eliminar Trabajo de Grado:**
```http
DELETE {{base_url}}/trabajos/{{trabajo_id}}
```

**Eliminar Profesor:**
```http
DELETE {{base_url}}/profesores/{{profesor2_id}}
```

**Eliminar Estudiante:**
```http
DELETE {{base_url}}/estudiantes/{{estudiante2_id}}
```

**Respuesta esperada para todas:** 200 OK (sin contenido en el body)

---

### 🔍 Pruebas de Validación de Errores

#### 1. Intentar obtener un recurso inexistente

```http
GET {{base_url}}/estudiantes/99999
```

**Respuesta esperada:** 404 Not Found

---

#### 2. Intentar crear estudiante con email duplicado

Primero crear un estudiante, luego intentar crear otro con el mismo email:

```http
POST {{base_url}}/estudiantes
Content-Type: application/json

{
  "identificacion": "1111111111",
  "nombres": "Pedro",
  "apellidos": "Gómez",
  "email": "juan.perez@unicauca.edu.co",
  "celular": "3001234567",
  "programa": "INGENIERIA_SISTEMAS",
  "codigoEstudiante": "100111111"
}
```

**Respuesta esperada:** 409 Conflict o 500 Internal Server Error (dependiendo de la configuración de la BD)

---

#### 3. Intentar actualizar un recurso inexistente

```http
PUT {{base_url}}/estudiantes/99999
Content-Type: application/json

{
  "nombres": "Test",
  "apellidos": "Test"
}
```

**Respuesta esperada:** 404 Not Found

---

## 📌 Notas Importantes

### Relaciones entre Entidades

- **Estudiante ↔ TrabajoGrado**: Un estudiante puede tener múltiples trabajos (histórico), pero típicamente solo uno activo
- **Profesor ↔ TrabajoGrado**: Un profesor puede dirigir múltiples trabajos simultáneamente
- **TipoTrabajo ↔ TrabajoGrado**: Define las reglas (máximo de estudiantes, modalidad, etc.)

### Campos No Modificables

Por razones de integridad académica, ciertos campos no pueden ser modificados después de la creación:

**Estudiante:**
- `codigoEstudiante` - Asignado por el sistema
- `programa` - Debe mantenerse fijo

**Profesor:**
- `programa` - Debe mantenerse fijo

**Trabajo de Grado:**
- Todos los campos son modificables para permitir correcciones y actualizaciones

### Recomendaciones de Uso

1. **Siempre crear en orden:** Tipos de Trabajo → Estudiantes → Profesores → Trabajos de Grado
2. **Verificar existencia:** Antes de asignar relaciones, verifica que los IDs existan
3. **Manejo de errores:** Implementa validación en el cliente para campos bloqueados
4. **Pruebas:** Usa el entorno de Postman para mantener los IDs entre solicitudes

---

## 🎯 Casos de Uso Comunes

### Caso 1: Estudiante actualiza su información personal

El estudiante puede actualizar su email y celular, pero no su código o programa:

```http
PUT {{base_url}}/estudiantes/{{estudiante1_id}}
Content-Type: application/json

{
  "email": "nuevo.email@unicauca.edu.co",
  "celular": "3001234567"
}
```

### Caso 2: Asignar un segundo estudiante a un trabajo grupal

```http
PUT {{base_url}}/trabajos/{{trabajo_id}}
Content-Type: application/json

{
  "titulo": "...",
  "modalidad": "...",
  "estudiante1": {
    "id": {{estudiante1_id}}
  },
  "estudiante2": {
    "id": {{estudiante2_id}}
  }
}
```

### Caso 3: Cambiar el estado de un trabajo a completado

```http
PUT {{base_url}}/trabajos/{{trabajo_id}}
Content-Type: application/json

{
  "estado": "COMPLETADO"
}
```

### Caso 4: Agregar un codirector a un trabajo existente

```http
PUT {{base_url}}/trabajos/{{trabajo_id}}
Content-Type: application/json

{
  "codirectores": [
    {
      "id": {{profesor2_id}}
    },
    {
      "id": {{profesor3_id}}
    }
  ]
}
```

---

## 🛠️ Troubleshooting

### Problema: Error 404 al consultar

**Causa:** El recurso no existe o el ID es incorrecto

**Solución:** Verifica que el ID existe haciendo GET a la colección completa

---

### Problema: Error 403 al actualizar

**Causa:** Intentas modificar campos bloqueados (codigoEstudiante, programa)

**Solución:** Envía solo los campos permitidos: nombres, apellidos, email, celular

---

### Problema: Error 500 al crear

**Causa posible:** Violación de restricciones únicas (email, identificación duplicada)

**Solución:** Verifica que no exista ya un registro con esos datos únicos

---

### Problema: Trabajo de grado no muestra relaciones

**Causa:** Es normal, el sistema usa DTOs para evitar ciclos JSON

**Solución:** Las relaciones se muestran con información básica (id, nombre, apellido)

---

## 📚 Recursos Adicionales

- **Repositorio:** [GitHub - gesTrabajoGrado-JPA](https://github.com/FaberGG/gesTrabajoGrado-JPA)
- **Documentación Spring Boot:** [spring.io](https://spring.io/projects/spring-boot)
- **PostgreSQL Docs:** [postgresql.org](https://www.postgresql.org/docs/)
- **Postman Learning:** [learning.postman.com](https://learning.postman.com)

---

## 👥 Equipo de Desarrollo

- **Faber:** Configuración del entorno y Docker
- **Sofía:** Modelo UML y diseño de relaciones
- **Hener:** Entidades, repositorios y DataLoader
- **Liz:** DTOs y servicios
- **Yeimi:** Controladores y pruebas REST

---

**Versión del documento:** 1.0  
**Última actualización:** Octubre 24, 2025  
**Sistema:** Gestión de Trabajos de Grado - Universidad del Cauca