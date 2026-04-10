# AGENTS.md

## Propósito del repositorio

Este repositorio contiene una API REST de ejemplo para un gestor de tareas con usuarios y roles.

Stack principal:
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Validation
- H2 Database
- Lombok
- Maven

El alcance actual cubre CRUD de usuarios y tareas, persistencia JPA, datos semilla y documentación manual/OpenAPI.
No incluye todavía autenticación/autorización real con Spring Security ni JWT.

Archivos de referencia:
- `pom.xml`
- `README.md`
- `openapi.yaml`
- `docs/API_ROUTES.md`

---

## Arquitectura del proyecto

Mantener la separación por capas ya existente:

- `src/main/java/com/example/demo/controlador/`
  - Capa HTTP/REST
  - Define endpoints, recibe requests, aplica validación y delega a servicios
- `src/main/java/com/example/demo/servicio/`
  - Lógica de negocio
  - Validaciones funcionales
  - Mapeo entre entidades y DTOs
- `src/main/java/com/example/demo/modelo/entidad/`
  - Entidades JPA del dominio
- `src/main/java/com/example/demo/modelo/repositorio/`
  - Repositorios Spring Data JPA
- `src/main/java/com/example/demo/dto/`
  - DTOs de entrada y salida de la API
- `src/main/java/com/example/demo/configuracion/`
  - Configuración de infraestructura y datos iniciales
- `src/main/resources/`
  - Configuración de aplicación
- `src/test/`
  - Tests

No mezclar responsabilidades entre capas.

---

## Convenciones de código

### Idioma y naming
- Mantener nombres en español, como en el resto del proyecto.
- Ejemplos actuales: `UsuarioControlador`, `TareaServicio`, `correoElectronico`, `fechaLimite`, `habilitado`.
- No introducir mezcla innecesaria de naming en inglés y español.

### Controladores
- Deben ser finos.
- Deben limitarse a:
  - recibir parámetros HTTP
  - usar `@Valid` cuando aplique
  - delegar al servicio
  - devolver DTOs o códigos HTTP apropiados
- No colocar lógica de negocio compleja en controladores.

### Servicios
- Centralizan reglas de negocio y validaciones funcionales.
- El proyecto usa `ResponseStatusException` para comunicar errores HTTP funcionales (`404`, `400`, `409`).
- Mantener este estilo salvo que se introduzca explícitamente una estrategia global consistente con `@ControllerAdvice`.

### DTOs
- No devolver entidades JPA directamente desde la API.
- Mantener DTOs separados para petición y respuesta.
- Si cambia el contrato HTTP, revisar DTOs y mapeos manuales.

### Repositorios
- Mantener repositorios simples, preferentemente con query methods derivados de Spring Data.
- Solo añadir consultas personalizadas cuando las derivadas no sean suficientes.

### Lombok
- El proyecto usa Lombok de forma intensiva.
- Mantener el estilo actual:
  - `@Data` para DTOs
  - `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` en entidades según el patrón ya existente
- Evitar boilerplate redundante si Lombok ya lo resuelve.

### Auditoría y timestamps
- Las entidades usan callbacks JPA (`@PrePersist`, `@PreUpdate`) para fechas de auditoría.
- Si se crean nuevas entidades con trazabilidad temporal, seguir el mismo patrón.

---

## Dominio actual

### Usuario
Archivo: `src/main/java/com/example/demo/modelo/entidad/Usuario.java`

Campos relevantes:
- `id`
- `correoElectronico` (único)
- `nombreCompleto`
- `contrasenaHash`
- `habilitado`
- `ultimoAccesoEn`
- `creadoEn`
- `actualizadoEn`
- `roles`

Notas:
- `correoElectronico` actúa como identificador funcional.
- La contraseña todavía no se cifra realmente: se persiste en `contrasenaHash` tal como llega.
- En `UsuarioServicio` existen `TODO` indicando que debe sustituirse por BCrypt cuando se integre seguridad real.

### Rol
Archivos:
- `src/main/java/com/example/demo/modelo/entidad/Rol.java`
- `src/main/java/com/example/demo/modelo/entidad/RolNombre.java`

Valores actuales:
- `ADMIN`
- `USER`

Regla actual:
- Si no se informan roles al crear usuario, el servicio intenta asignar `USER` por defecto.

### Tarea
Archivo: `src/main/java/com/example/demo/modelo/entidad/Tarea.java`

Campos relevantes:
- `id`
- `titulo`
- `texto`
- `fechaLimite`
- `completada`
- `completadaEn`
- `creadoEn`
- `actualizadoEn`
- `propietario`

Relación actual:
- muchas tareas pertenecen a un usuario (`ManyToOne`)

---

## API actual

### Usuarios
Controlador: `src/main/java/com/example/demo/controlador/UsuarioControlador.java`

Endpoints vigentes:
- `GET /api/usuarios`
- `GET /api/usuarios/activos`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `PATCH /api/usuarios/{id}/habilitar`
- `PATCH /api/usuarios/{id}/deshabilitar`
- `DELETE /api/usuarios/{id}`

Servicio asociado:
- `src/main/java/com/example/demo/servicio/UsuarioServicio.java`

### Tareas
Controlador: `src/main/java/com/example/demo/controlador/TareaControlador.java`

Endpoints vigentes:
- `GET /api/tareas`
- `GET /api/tareas/{id}`
- `GET /api/tareas/usuario/{usuarioId}`
- `POST /api/tareas`
- `PUT /api/tareas/{id}`
- `PATCH /api/tareas/{id}/completar`
- `PATCH /api/tareas/{id}/descompletar`
- `DELETE /api/tareas/{id}`

Servicio asociado:
- `src/main/java/com/example/demo/servicio/TareaServicio.java`

### Regla importante de filtros
En `GET /api/tareas/usuario/{usuarioId}`, la precedencia actual es:
1. Si llega `completada`, se ignoran `desde` y `hasta`
2. Si no llega `completada` y llegan `desde` y `hasta`, filtra por rango
3. Si no llega ningún filtro, devuelve todas las tareas del usuario

Si se cambia esta regla, actualizar también:
- `docs/API_ROUTES.md`
- `openapi.yaml`

---

## Configuración y ejecución

### Base de datos
Archivo: `src/main/resources/application.properties`

Configuración actual:
- H2 en memoria: `jdbc:h2:mem:demo-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- `spring.jpa.hibernate.ddl-auto=update`
- Consola H2 habilitada en `/h2-console`

Tests:
- `src/test/resources/application.properties`
- usan H2 en memoria con `create-drop`

### CORS
Archivo: `src/main/java/com/example/demo/configuracion/WebConfiguracion.java`

Orígenes permitidos actualmente:
- `http://localhost:5173`
- `http://localhost:3000`

Si se añade frontend nuevo o despliegue, revisar esta lista.

### Datos semilla
Archivo: `src/main/java/com/example/demo/configuracion/DataInitializer.java`

Al arrancar, se crean o actualizan:
- roles base
- usuarios demo
- tareas demo

Si cambian reglas de negocio, roles o datos mínimos, revisar este inicializador.

---

## Documentación obligatoria cuando cambia la API

Si se modifica cualquier endpoint, DTO o comportamiento observable, actualizar siempre también:
- `openapi.yaml`
- `docs/API_ROUTES.md`
- `README.md` si cambia alcance, arranque o descripción funcional

No dejar implementación y documentación desincronizadas.

---

## Testing esperado

Test actual existente:
- `src/test/java/com/example/demo/DemoApplicationTests.java`

Ese test solo verifica la carga de contexto. Si se cambia comportamiento de negocio o de API, añadir o ampliar cobertura con alguno de estos enfoques:
- tests de servicio
- tests de repositorio
- tests de integración/controlador

Prioridades recomendadas:
1. creación, actualización y eliminación de usuarios
2. unicidad de `correoElectronico`
3. resolución y asignación de roles
4. creación y actualización de tareas
5. completar/descompletar tareas
6. filtros por usuario, estado y rango de fechas

---


## Pautas para cambios futuros

### Si añades una nueva funcionalidad
1. Define o ajusta DTOs si cambia el contrato HTTP
2. Implementa la lógica en `servicio/`
3. Expón endpoints en `controlador/`
4. Añade/ajusta repositorios si hace falta
5. Actualiza `openapi.yaml` y `docs/API_ROUTES.md`

### Si integras Spring Security y JWT
Seguir estas pautas:
- no mezclar autenticación con lógica de negocio en controladores de dominio
- cifrar contraseñas con BCrypt o equivalente
- no exponer `contrasenaHash` en respuestas
- separar claramente autenticación, autorización y negocio
- revisar CORS, códigos HTTP y documentación de endpoints protegidos

### Si cambias entidades del dominio
- revisar DTOs y mapeos manuales
- revisar `DataInitializer`
- revisar métodos derivados en repositorios
- revisar documentación y OpenAPI

---

## Qué evitar

- No devolver entidades JPA directamente desde la API
- No introducir lógica de negocio pesada en controladores
- No romper el naming en español sin necesidad
- No cambiar contratos HTTP sin actualizar documentación
- No persistir contraseñas en texto plano si se incorpora seguridad real
- No alterar reglas funcionales ya documentadas sin actualizar también la documentación

---

## Resumen operativo para agentes

Cuando trabajes sobre este repositorio:
1. identifica primero si el cambio afecta a usuarios, tareas, seguridad o infraestructura
2. respeta la arquitectura por capas
3. mantén el estilo y naming en español
4. usa DTOs para contratos HTTP
5. actualiza `openapi.yaml` y `docs/API_ROUTES.md` si cambia la API
6. revisa `DataInitializer` y configuración si cambia el dominio
7. añade tests si cambia el comportamiento
8. revisa compatibilidad de Java/Spring Boot antes de tocar versiones

