# Resumen del Frontend - Gestor de Tareas

## ¿Qué se ha creado?

Se ha creado un frontend completo con React y Vite en la carpeta `/frontend` con la siguiente estructura:

### Archivos principales creados:

1. **src/App.jsx** - Componente principal
   - Carga todas las tareas desde la API
   - Gestiona el estado de carga y errores
   - Proporciona la función para actualizar tareas

2. **src/components/TareaLista.jsx** - Componente que lista tareas
   - Muestra todas las tareas en una lista
   - Renderiza cada tarea con el componente TareaItem

3. **src/components/TareaItem.jsx** - Componente individual de tarea
   - Muestra un checkbox para marcar/desmarcar la tarea
   - Muestra el título, texto y fecha límite de la tarea
   - Permite interactuar con cada tarea

4. **src/App.css** - Estilos (vacío por ahora)
   - Se rellenará más adelante con Tailwind CSS

5. **.env** y **.env.example** - Variables de entorno
   - Configuración de la URL de la API

6. **README.md** - Documentación del frontend

## Funcionalidades implementadas

✅ **Listar todas las tareas** - Obtiene y muestra todas las tareas del backend
✅ **Marcar tareas como completadas** - Checkbox para cambiar estado
✅ **Desmarcar tareas** - Permite volver a marcar como pendiente
✅ **Gestión de errores** - Muestra mensajes si hay problemas
✅ **Estado de carga** - Indica cuando se están cargando las tareas

## Cómo ejecutar

### Prerrequisitos
- Node.js 16+ instalado
- Backend Spring Boot corriendo en http://localhost:8080
- Las dependencias ya están instaladas

### Iniciar el desarrollo

**Terminal 1 - Backend (Spring Boot):**
```bash
cd C:\Users\Samuel2\Downloads\demo\demo
.\mvnw.cmd spring-boot:run
```

**Terminal 2 - Frontend (React + Vite):**
```bash
cd C:\Users\Samuel2\Downloads\demo\demo\frontend
npm run dev
```

El frontend estará disponible en: **http://localhost:5173**

## Endpoints API utilizados

El frontend utiliza los siguientes endpoints:

- `GET /api/tareas` - Obtiene todas las tareas
- `PATCH /api/tareas/{id}/completar` - Marca una tarea como completada
- `PATCH /api/tareas/{id}/descompletar` - Marca una tarea como no completada

## Configuración

La URL de la API está configurada en el archivo `.env`:
```
VITE_API_URL=http://localhost:8080/api
```

El CORS ya está configurado en el backend para permitir peticiones desde `http://localhost:5173`.

## Próximas mejoras

Como comentaste, las próximas implementaciones serán:

1. **Estilos con Tailwind CSS** - Hacer la interfaz más atractiva
2. **Autenticación JWT** - Implementar login/logout
3. **Filtrar tareas por usuario** - Mostrar solo las tareas del usuario conectado
4. **CRUD completo** - Crear, editar y eliminar tareas
5. **Filtros y búsqueda** - Buscar y filtrar tareas por fecha, estado, etc.

## Estructura final del proyecto

```
demo/
├── src/                          (Backend Java)
├── pom.xml
├── mvnw.cmd
├── README.md
├── AGENTS.md
├── openapi.yaml
├── docs/
│   └── API_ROUTES.md
├── data/
└── frontend/                     (Frontend React + Vite) ← NUEVO
    ├── src/
    │   ├── components/
    │   │   ├── TareaItem.jsx
    │   │   ├── TareaLista.jsx
    │   └── index.js
    │   ├── hooks/
    │   │   └── useTareas.js
    │   ├── services/
    │   │   └── api.js
    │   ├── App.jsx
    │   ├── App.css
    │   ├── main.jsx
    │   └── index.css
    ├── .env
    │ .env.example
    ├── .gitignore
    ├── eslint.config.js
    ├── index.html
    ├── package.json
    ├── package-lock.json
    ├── vite.config.js
    ├── README.md
    ├── SETUP.md
    └── node_modules/
```

## Notas importantes

- **Sin estilos**: Como solicitaste, por ahora no hay estilos CSS específicos. Solo lo básico de HTML sin estilo.
- **Todas las tareas**: Actualmente muestra TODAS las tareas de la base de datos.
- **Sin autenticación**: No hay filtro de usuario. Cuando implementes JWT, se actualizará para mostrar solo las tareas del usuario conectado.
- **Auto-actualización**: Los cambios en el checkbox se sincronizaban automáticamente con la API.

## Troubleshooting

### El frontend no conecta con la API
- Verifica que el backend esté corriendo en http://localhost:8080
- Revisa la consola del navegador (F12) para ver los errores
- Comprueba que CORS esté configurado correctamente

### El servidor Vite no inicia
- Asegúrate de estar en la carpeta `frontend`
- Comprueba que las dependencias están instaladas: `npm install`
- Intenta limpiar la caché: `rm -r node_modules package-lock.json` y reinstala

### Puerto 5173 ya está en uso
- Cambia el puerto en vite.config.js o usa: `npm run dev -- --port 3000`


