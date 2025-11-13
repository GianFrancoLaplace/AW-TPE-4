# Documentación API - Microservicio Flota

**Puerto:** 8083  
**Base URL:** `http://localhost:8083`

---

## Monopatines

### ABM Básico

#### Crear Monopatín
```http
POST /monopatines
Content-Type: application/json

{
  "estado": "DISPONIBLE",
  "latitudActual": -37.3213,
  "longitudActual": -59.1347,
  "kmTotalesAcumulados": 0.0,
  "tiempoUsoTotalMinutos": 0
}
```

#### Obtener Todos los Monopatines
```http
GET /monopatines
```

#### Obtener Monopatín por ID
```http
GET /monopatines/{id}
```

#### Actualizar Monopatín
```http
PUT /monopatines/{id}
Content-Type: application/json

{
  "estado": "EN_MANTENIMIENTO",
  "kmTotalesAcumulados": 150.5
}
```

#### Eliminar Monopatín
```http
DELETE /monopatines/{id}
```

---

### Gestión de Estado y Ubicación

#### Cambiar Estado
```http
PUT /monopatines/{id}/estado
Content-Type: application/json

{
  "nuevoEstado": "EN_MANTENIMIENTO"
}
```
**Estados válidos:** `DISPONIBLE`, `EN_USO`, `EN_MANTENIMIENTO`, `BATERIA_BAJA`

#### Actualizar Ubicación GPS
```http
PUT /monopatines/{id}/ubicacion
Content-Type: application/json

{
  "latitud": -37.3213,
  "longitud": -59.1347
}
```
**Rangos válidos:** Latitud [-90, 90], Longitud [-180, 180]

#### Actualizar Uso (km y tiempo)
```http
PUT /monopatines/{id}/uso
Content-Type: application/json

{
  "kmRecorridos": 5.2,
  "minutosUsados": 25
}
```

#### Ubicar en Parada
```http
POST /monopatines/{id}/ubicar-parada
Content-Type: application/json

{
  "idParada": 1
}
```

#### Registrar en Mantenimiento
```http
POST /monopatines/{id}/mantenimiento
```

---

### Consultas y Reportes

#### Obtener Monopatines Disponibles
```http
GET /monopatines/disponibles
```

#### Buscar Monopatines Cercanos
```http
GET /monopatines/cercanos?lat=-37.3213&lon=-59.1347&radio=1000
```
**Parámetros:**
- `lat` (requerido): Latitud
- `lon` (requerido): Longitud
- `radio` (opcional, default: 1000): Radio en metros

#### Reporte de Mantenimiento
```http
GET /monopatines/reporte-mantenimiento?umbralKm=500
```

---

## Paradas

### ABM Básico

#### Crear Parada
```http
POST /paradas
Content-Type: application/json

{
  "nombre": "Plaza Principal",
  "latitudCentro": -37.3213,
  "longitudCentro": -59.1347,
  "radioMetros": 100,
  "activa": true
}
```

#### Obtener Todas las Paradas
```http
GET /paradas
```

#### Obtener Parada por ID
```http
GET /paradas/{id}
```

#### Actualizar Parada
```http
PUT /paradas/{id}
Content-Type: application/json

{
  "nombre": "Plaza Central",
  "radioMetros": 150
}
```

#### Eliminar Parada
```http
DELETE /paradas/{id}
```

---

### Gestión de Paradas

#### Obtener Paradas Activas
```http
GET /paradas/activas
```

#### Cambiar Estado de Parada
```http
PUT /paradas/{id}/estado
Content-Type: application/json

{
  "activa": false
}
```

#### Buscar Paradas Cercanas
```http
GET /paradas/cercanas?lat=-37.3213&lon=-59.1347&radio=1000
```

#### Validar Ubicación en Parada
```http
GET /paradas/{id}/validar-ubicacion?lat=-37.3215&lon=-59.1345
```
**Respuesta:**
```json
{
  "dentroDeParada": true
}
```

---

## Datos de Prueba

### Paradas de Ejemplo

```json
// Parada 1 - Plaza Principal
{
  "nombre": "Plaza Principal",
  "latitudCentro": -37.3213,
  "longitudCentro": -59.1347,
  "radioMetros": 100,
  "activa": true
}

// Parada 2 - Universidad
{
  "nombre": "Universidad UNICEN",
  "latitudCentro": -37.3141,
  "longitudCentro": -59.1355,
  "radioMetros": 150,
  "activa": true
}
```

### Monopatines de Ejemplo

```json
// Monopatín en Plaza
{
  "estado": "DISPONIBLE",
  "latitudActual": -37.3215,
  "longitudActual": -59.1345,
  "kmTotalesAcumulados": 45.5,
  "tiempoUsoTotalMinutos": 320
}

// Monopatín en Universidad
{
  "estado": "DISPONIBLE",
  "latitudActual": -37.3143,
  "longitudActual": -59.1352,
  "kmTotalesAcumulados": 78.3,
  "tiempoUsoTotalMinutos": 550
}
```

---

## Códigos de Respuesta

- **200 OK** - Operación exitosa
- **204 No Content** - Eliminación exitosa
- **400 Bad Request** - Datos inválidos
- **404 Not Found** - Recurso no encontrado
- **500 Internal Server Error** - Error del servidor