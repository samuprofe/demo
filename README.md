# Demo - Modelo de datos (backend)

Este proyecto incluye el modelo de datos inicial para un gestor de tareas con usuarios y roles, sin Spring Security por ahora.

## Entidades

- `Usuario`
  - `correoElectronico` (unico, usado como identificador de login)
  - `nombreCompleto`
  - `contrasenaHash`
  - `habilitado`
  - auditoria: `creadoEn`, `actualizadoEn`, `ultimoAccesoEn`
- `Rol`
  - `nombre` (`ADMIN`, `USER`)
- `Tarea`
  - `titulo`, `texto`, `fechaLimite`
  - `completada`, `completadaEn`
  - relacion con `Usuario` via `propietario`

## Repositorios

- `UsuarioRepositorio`
- `RolRepositorio`
- `TareaRepositorio`

## Ejecutar tests

```bat
mvnw.cmd test
```

