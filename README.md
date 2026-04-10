# Demo - Gestor de Tareas (Backend + Frontend)

Proyecto de ejemplo con **Backend en Spring Boot** y **Frontend en React + Vite**.

## 📋 Descripción

Sistema completo de gestión de tareas con:
- API REST en Java/Spring Boot
- Frontend interactivo en React
- Base de datos H2
- Modelo de datos: Usuarios, Roles y Tareas

## 🚀 Inicio rápido

### Backend (Spring Boot)
```bash
cd demo
.\mvnw.cmd spring-boot:run
```
Backend disponible en: `http://localhost:8080`

### Frontend (React + Vite)
```bash
cd demo/frontend
npm run dev
```
Frontend disponible en: `http://localhost:5173`

## 📁 Estructura del proyecto

```
demo/
├── src/                      # Backend (Java/Spring Boot)
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── controlador/  # REST Controllers
│   │   │   ├── servicio/     # Lógica de negocio
│   │   │   ├── modelo/       # Entidades y repositorios
│   │   │   └── dto/          # DTOs de entrada/salida
│   │   └── resources/
│   └── test/
├── frontend/                 # Frontend (React + Vite)
│   ├── src/
│   │   ├── components/       # Componentes React
│   │   ├── hooks/           # Hooks personalizados
│   │   ├── services/        # Servicios de API
│   │   ├── App.jsx          # Componente principal
│   │   └── main.jsx
│   ├── .env                 # Configuración
│   ├── package.json
│   └── vite.config.js
├── docs/                     # Documentación
├── pom.xml                   # Dependencias Maven
└── openapi.yaml             # Documentación API
```

## 🏗️ Backend - Entidades

### Usuario
- `id`, `correoElectronico` (único), `nombreCompleto`
- `contrasenaHash`, `habilitado`
- Auditoría: `creadoEn`, `actualizadoEn`, `ultimoAccesoEn`
- Relación: múltiples tareas

### Rol
- Valores: `ADMIN`, `USER`
- Relación: múltiples usuarios

### Tarea
- `titulo`, `texto`, `fechaLimite`
- `completada`, `completadaEn`
- Auditoría: `creadoEn`, `actualizadoEn`
- Propietario: Usuario (ManyToOne)

## 🎯 Frontend - Funcionalidades

✅ Listar todas las tareas
✅ Marcar/desmarcar tareas como completadas
✅ Gestión de errores y estados de carga
✅ Comunicación con API REST
⏳ Estilos con Tailwind CSS (próximo)
⏳ Autenticación JWT (próximo)
⏳ Filtrar por usuario (próximo)

## 📡 API Endpoints

### Tareas
- `GET /api/tareas` - Listar todas
- `GET /api/tareas/{id}` - Obtener una
- `GET /api/tareas/usuario/{usuarioId}` - Por usuario
- `POST /api/tareas` - Crear
- `PUT /api/tareas/{id}` - Actualizar
- `PATCH /api/tareas/{id}/completar` - Marcar completa
- `PATCH /api/tareas/{id}/descompletar` - Marcar pendiente
- `DELETE /api/tareas/{id}` - Eliminar

### Usuarios
- `GET /api/usuarios` - Listar todas
- `GET /api/usuarios/activos` - Activos
- `GET /api/usuarios/{id}` - Obtener uno
- `POST /api/usuarios` - Crear
- `PUT /api/usuarios/{id}` - Actualizar
- `PATCH /api/usuarios/{id}/habilitar` - Habilitar
- `PATCH /api/usuarios/{id}/deshabilitar` - Deshabilitar
- `DELETE /api/usuarios/{id}` - Eliminar

## 🔧 Tecnologías

### Backend
- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**

### Frontend
- **React 19**
- **Vite 8**
- **JavaScript ES6+**
- **Fetch API**

## 📚 Documentación

- `docs/API_ROUTES.md` - Rutas de la API
- `AGENTS.md` - Guía para desarrolladores
- `openapi.yaml` - Especificación OpenAPI
- `frontend/README.md` - Documentación del frontend
- `frontend/QUICKSTART.md` - Inicio rápido del frontend
- `frontend/DEVELOPMENT.md` - Guía de desarrollo

## ⚙️ Configuración

### Backend
- BD: H2 en `jdbc:h2:file:./data/demo-db`
- Consola H2: `http://localhost:8080/h2-console`
- CORS: Configurado para `http://localhost:5173`

### Frontend
- Puerto: `5173` (Vite)
- API URL: `http://localhost:8080/api` (en `.env`)

## 🧪 Testing

Backend:
```bash
mvnw.cmd test
```

## 🎨 Próximos pasos

1. **Estilos**: Agregar Tailwind CSS al frontend
2. **Autenticación**: Implementar JWT
3. **Usuarios**: Filtrar tareas por usuario conectado
4. **CRUD**: Crear, editar, eliminar tareas desde UI
5. **Validaciones**: Validar datos de entrada
6. **Notificaciones**: Agregar toast messages

## 📝 Notas importantes

- Las contraseñas actualmente NO están cifradas (usar BCrypt en producción)
- CORS está abierto para desarrollo local
- Base de datos H2 es en archivo (no en memoria)
- API requiere actualización a JWT cuando se implemente autenticación

## 🐛 Troubleshooting

**Backend no inicia**
```bash
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

**Frontend no carga tareas**
- Verifica que el backend esté corriendo
- Mira F12 → Network para errores CORS
- Comprueba `.env` con la URL correcta

**Puerto en uso**
```bash
# Cambiar puerto del frontend
npm run dev -- --port 3000
```

---

Desarrollado para demostración de un sistema de gestión de tareas con modelo de datos completo.
