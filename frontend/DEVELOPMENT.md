# Guía de desarrollo - Frontend Gestor de Tareas

## 🚀 Iniciando el desarrollo

### Paso 1: Iniciar el Backend

```bash
cd C:\Users\Samuel2\Downloads\demo\demo
.\mvnw.cmd spring-boot:run
```

Espera a ver el mensaje:
```
Started DemoApplication in X seconds
```

### Paso 2: Iniciar el Frontend

Abre OTRA terminal (en PowerShell):

```bash
cd C:\Users\Samuel2\Downloads\demo\demo\frontend
npm run dev
```

Deberías ver:
```
  VITE v8.0.4  ready in 300 ms

  ➜  Local:   http://localhost:5173/
```

### Paso 3: Abrir en el navegador

Accede a: **http://localhost:5173**

Deberías ver la página "Gestor de Tareas" con un listado de tareas.

## 📝 Estructura del código

### Componentes (`src/components/`)

**TareaLista.jsx** - Contenedor de la lista
- Props: `tareas`, `onAlternarCompletada`
- Renderiza múltiples `TareaItem`

**TareaItem.jsx** - Ítem individual
- Props: `tarea`, `onAlternarCompletada`
- Muestra checkbox, título, descripción y fecha

### Hooks (`src/hooks/`)

**useTareas.js** - Hook personalizado
- Maneja: `tareas`, `cargando`, `error`
- Métodos: `actualizarTarea()`, `recargar()`
- Centraliza toda la lógica de tareas

### Servicios (`src/services/`)

**api.js** - Servicio de API
- `fetchTareas()` - GET todas las tareas
- `actualizarEstadoTarea(id, completada)` - PATCH estado
- Maneja errores automáticamente

## 🔄 Flujo de actualización de una tarea

1. Usuario hace click en checkbox
2. `TareaItem` llama a `onAlternarCompletada(id, completada)`
3. `App` llama a `actualizarTarea()` del hook
4. `useTareas` llama a `actualizarEstadoTarea()` del servicio
5. `api.js` hace PATCH a `/api/tareas/{id}/completar` o `descompletar`
6. La API retorna la tarea actualizada
7. El estado en React se actualiza
8. El componente se re-renderiza

## 🎨 Próximo paso: Tailwind CSS

Cuando esté listo para agregar estilos:

```bash
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p
```

Luego configura `tailwind.config.js` y `postcss.config.js`.

Después actualiza `App.css` y componentes con clases de Tailwind.

## 🔐 Próximo paso: JWT Authentication

Cuando implementes JWT:

1. Crea `src/hooks/useAuth.js` para login/logout
2. Crea `src/services/auth.js` para auth endpoints
3. Crea `src/components/LoginForm.jsx`
4. Guarda token en localStorage
5. Filtra tareas por usuario en `useTareas.js`
6. Agrega token a headers en `api.js`

## 📱 Responsive

El código actual no tiene media queries. Cuando agregues Tailwind CSS, puedes:

```jsx
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
```

## 🧪 Testing (opcional)

Si quieres agregar tests:

```bash
npm install -D vitest @testing-library/react
```

Ejemplo:
```javascript
// TareaItem.test.jsx
import { render, screen } from '@testing-library/react'
import TareaItem from './TareaItem'

test('renders tarea title', () => {
  const tarea = { id: 1, titulo: 'Test', completada: false }
  render(<TareaItem tarea={tarea} onAlternarCompletada={() => {}} />)
  expect(screen.getByText('Test')).toBeInTheDocument()
})
```

## 🐛 Debugging

### Consola del navegador (F12)
- Verifica errores de red (Network tab)
- Mira la consola JavaScript para errores

### Devtools de React
```bash
npm install -D @react-devtools/shell
```

Luego instala la extensión en el navegador.

### Logs en el código
```javascript
console.log('Tareas:', tareas)
console.log('Error:', error)
```

## 📦 Build para producción

```bash
npm run build
```

Genera carpeta `dist/` lista para desplegar.

Preview:
```bash
npm run preview
```

## 🔗 URLs importantes

- Frontend: http://localhost:5173
- Backend: http://localhost:8080
- API Docs: http://localhost:8080/swagger-ui/ (si existe)
- H2 Console: http://localhost:8080/h2-console

## 📚 Recursos útiles

- [React Docs](https://react.dev)
- [Vite Docs](https://vite.dev)
- [Tailwind CSS](https://tailwindcss.com)
- [MDN Web Docs](https://developer.mozilla.org)

## ✨ Tips

1. **Hot Module Reload**: Los cambios en archivos se reflejan al instante
2. **Console errors**: Los errores aparecen directamente en la terminal
3. **Network throttling**: Simula conexión lenta en DevTools
4. **LocalStorage**: Puedes guardar preferencias del usuario
5. **Fetch API**: Puedes usar `async/await` como en `api.js`

## 🎯 Checklist de desarrollo

- [ ] Backend corriendo en 8080
- [ ] Frontend corriendo en 5173
- [ ] Tareas cargando en la página
- [ ] Checkboxes funcionando
- [ ] No hay errores en la consola
- [ ] Cambios guardándose en la API

---

¡Felicidades! 🎉 El frontend está listo para desarrollo.

