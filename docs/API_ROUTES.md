# API Routes

## Base URL

- `http://localhost:8080`
- Prefijo API: `/api`

## Usuarios

### `GET /api/usuarios`
- **Descripcion:** lista todos los usuarios.
- **Query params:** ninguno.
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `UsuarioRespuesta[]`

### `GET /api/usuarios/activos`
- **Descripcion:** lista usuarios habilitados.
- **Query params:** ninguno.
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `UsuarioRespuesta[]`

### `GET /api/usuarios/{id}`
- **Descripcion:** obtiene un usuario por id.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `UsuarioRespuesta`
  - `404 Not Found`: usuario no encontrado

### `POST /api/usuarios`
- **Descripcion:** crea un usuario.
- **Body:** `UsuarioPeticion`.
- **Respuestas:**
  - `201 Created`: `UsuarioRespuesta`
  - `400 Bad Request`: validacion o rol no valido/no encontrado
  - `409 Conflict`: correo ya existente

### `PUT /api/usuarios/{id}`
- **Descripcion:** actualiza un usuario.
- **Path params:** `id: Long`.
- **Body:** `UsuarioPeticion`.
- **Respuestas:**
  - `200 OK`: `UsuarioRespuesta`
  - `400 Bad Request`: validacion o rol no valido/no encontrado
  - `404 Not Found`: usuario no encontrado
  - `409 Conflict`: correo ya existente

### `PATCH /api/usuarios/{id}/habilitar`
- **Descripcion:** habilita usuario.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `204 No Content`
  - `404 Not Found`: usuario no encontrado

### `PATCH /api/usuarios/{id}/deshabilitar`
- **Descripcion:** deshabilita usuario.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `204 No Content`
  - `404 Not Found`: usuario no encontrado

### `DELETE /api/usuarios/{id}`
- **Descripcion:** elimina usuario.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `204 No Content`
  - `404 Not Found`: usuario no encontrado

## Tareas

### `GET /api/tareas`
- **Descripcion:** lista todas las tareas o filtra por estado.
- **Query params opcionales:**
  - `completada: boolean`
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `TareaRespuesta[]`

### `GET /api/tareas/{id}`
- **Descripcion:** obtiene una tarea por id.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `TareaRespuesta`
  - `404 Not Found`: tarea no encontrada

### `GET /api/tareas/usuario/{usuarioId}`
- **Descripcion:** lista tareas de un usuario.
- **Path params:** `usuarioId: Long`.
- **Query params opcionales:**
  - `completada: boolean`
  - `desde: yyyy-MM-dd`
  - `hasta: yyyy-MM-dd`
- **Respuestas:**
  - `200 OK`: `TareaRespuesta[]`
  - `404 Not Found`: usuario no encontrado
- **Nota de precedencia de filtros (implementacion actual):**
  1. Si llega `completada`, se ignoran `desde` y `hasta`.
  2. Si no llega `completada` y llegan `desde` + `hasta`, filtra por rango.
  3. Si no llega ninguno, devuelve todas del usuario.

### `POST /api/tareas`
- **Descripcion:** crea una tarea.
- **Body:** `TareaPeticion`.
- **Respuestas:**
  - `201 Created`: `TareaRespuesta`
  - `400 Bad Request`: validacion
  - `404 Not Found`: usuario propietario no encontrado

### `PUT /api/tareas/{id}`
- **Descripcion:** actualiza una tarea.
- **Path params:** `id: Long`.
- **Body:** `TareaPeticion`.
- **Respuestas:**
  - `200 OK`: `TareaRespuesta`
  - `400 Bad Request`: validacion
  - `404 Not Found`: tarea no encontrada o usuario propietario no encontrado

### `PATCH /api/tareas/{id}/completar`
- **Descripcion:** marca una tarea como completada.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `TareaRespuesta`
  - `404 Not Found`: tarea no encontrada

### `PATCH /api/tareas/{id}/descompletar`
- **Descripcion:** marca una tarea como no completada.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `200 OK`: `TareaRespuesta`
  - `404 Not Found`: tarea no encontrada

### `DELETE /api/tareas/{id}`
- **Descripcion:** elimina una tarea.
- **Path params:** `id: Long`.
- **Body:** no.
- **Respuestas:**
  - `204 No Content`
  - `404 Not Found`: tarea no encontrada

## DTOs de referencia

### `UsuarioPeticion`
```json
{
  "correoElectronico": "ana@demo.com",
  "nombreCompleto": "Ana Garcia",
  "contrasena": "secreto123",
  "habilitado": true,
  "roles": ["USER"]
}
```

### `UsuarioRespuesta`
```json
{
  "id": 1,
  "correoElectronico": "ana@demo.com",
  "nombreCompleto": "Ana Garcia",
  "habilitado": true,
  "ultimoAccesoEn": null,
  "creadoEn": "2026-04-10T13:00:00",
  "actualizadoEn": "2026-04-10T13:00:00",
  "roles": ["USER"]
}
```

### `TareaPeticion`
```json
{
  "titulo": "Preparar demo",
  "texto": "Crear la presentacion",
  "fechaLimite": "2026-04-20",
  "propietarioId": 2
}
```

### `TareaRespuesta`
```json
{
  "id": 10,
  "titulo": "Preparar demo",
  "texto": "Crear la presentacion",
  "fechaLimite": "2026-04-20",
  "completada": false,
  "completadaEn": null,
  "creadoEn": "2026-04-10T13:00:00",
  "actualizadoEn": "2026-04-10T13:00:00",
  "propietarioId": 2,
  "propietarioNombre": "Ana Garcia"
}
```

