# 📊 Diagrama de arquitectura - Frontend Gestor de Tareas

## 🏗️ Arquitectura general

```
┌─────────────────────────────────────────────────────────────────┐
│                     NAVEGADOR (localhost:5173)                  │
└─────────────────────────────────────────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                    APLICACIÓN REACT + VITE                      │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                      App.jsx                             │  │
│  │  - Componente principal                                 │  │
│  │  - Inicializa useTareas                                 │  │
│  │  - Maneja estado global                                 │  │
│  └──────────────────────────────────────────────────────────┘  │
│                             │                                   │
│                             ▼                                   │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              useTareas Hook (src/hooks/)                │  │
│  │  - Maneja: tareas[], cargando, error                    │  │
│  │  - Métodos: actualizarTarea(), recargar()               │  │
│  │  - Llama a servicios de API                             │  │
│  └──────────────────────────────────────────────────────────┘  │
│                             │                                   │
│                             ▼                                   │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │          services/api.js (Servicio de API)              │  │
│  │  - API_ENDPOINTS (constantes)                           │  │
│  │  - fetchTareas() → GET /api/tareas                      │  │
│  │  - actualizarEstadoTarea() → PATCH /api/tareas/{id}     │  │
│  │  - Manejo centralizado de errores                       │  │
│  └──────────────────────────────────────────────────────────┘  │
│                             │                                   │
└─────────────────────────────┼─────────────────────────────────┘
                              │
                    HTTP REQUESTS (CORS)
                              │
        ┌─────────────────────▼─────────────────────┐
        │                                           │
        ▼                                           ▼
    ┌────────────┐                        ┌──────────────────┐
    │  GET       │                        │  PATCH           │
    │ /tareas    │                        │ /tareas/{id}/... │
    └────────────┘                        └──────────────────┘
        │                                           │
        └─────────────────────┬─────────────────────┘
                              │
        ┌─────────────────────▼─────────────────────┐
        │     API REST - Backend (localhost:8080)  │
        │                                           │
        │  Spring Boot - Java                       │
        │  - TareaControlador                       │
        │  - TareaServicio                          │
        │  - TareaRepositorio                       │
        │  - Base de datos H2                       │
        └─────────────────────────────────────────┘
```

## 🎮 Flujo de interacción

```
Usuario hace click en checkbox
        │
        ▼
TareaItem.jsx (onChange event)
        │
        ▼
onAlternarCompletada(id, completada)
        │
        ▼
App.jsx (actualizar llamadas)
        │
        ▼
useTareas.actualizarTarea(id, completada)
        │
        ▼
services/api.actualizarEstadoTarea()
        │
        ▼
fetch PATCH /api/tareas/{id}/completar
        │
        ▼
Backend (Spring Boot)
        │
        ▼
Base de datos actualizada
        │
        ▼
Respuesta con tarea actualizada
        │
        ▼
Actualizar estado en React
        │
        ▼
Re-renderizar TareaItem ✅
```

## 📁 Estructura de archivos

```
frontend/
│
├── src/
│   │
│   ├── components/
│   │   ├── TareaItem.jsx          ← Checkbox individual
│   │   ├── TareaLista.jsx         ← Lista de items
│   │   └── index.js               ← Exportador
│   │
│   ├── hooks/
│   │   ├── useTareas.js           ← Lógica de tareas
│   │   └── index.js               ← Exportador
│   │
│   ├── services/
│   │   ├── api.js                 ← Llamadas HTTP
│   │   └── index.js               ← Exportador
│   │
│   ├── App.jsx                    ← Componente principal
│   ├── main.jsx                   ← Entrada
│   ├── App.css                    ← Estilos (vacío)
│   └── index.css                  ← Base
│
├── public/
│   └── (archivos estáticos)
│
├── .env                           ← Configuración
├── package.json                   ← Dependencias
├── vite.config.js                 ← Configuración Vite
├── README.md                       ← Documentación
└── (otros archivos)
```

## 🔄 Flujo de datos (Data Flow)

```
┌─────────────────────────────────────────────────────────┐
│                    App.jsx                              │
│  Estado: [tareas, cargando, error]                      │
└─────────────────────────────────────────────────────────┘
          │                               │
          │ Props: tareas                 │ Props: onAlternarCompletada
          │                               │
          ▼                               ▼
┌─────────────────────────────────────────────────────────┐
│                  TareaLista.jsx                         │
│  Itera y renderiza TareaItem para cada tarea            │
└─────────────────────────────────────────────────────────┘
          │                               │
          │ Props: tarea                  │ Props: onAlternarCompletada
          │                               │
          ▼                               ▼
┌─────────────────────────────────────────────────────────┐
│                  TareaItem.jsx                          │
│  onChange → onAlternarCompletada(id, completada)        │
└─────────────────────────────────────────────────────────┘
          │
          │ Callback
          │
          ▼
┌─────────────────────────────────────────────────────────┐
│               App.jsx (actualizar)                      │
│  actualizarTarea(id, completada)                        │
└─────────────────────────────────────────────────────────┘
          │
          │ Llama hook
          │
          ▼
┌─────────────────────────────────────────────────────────┐
│              useTareas Hook                             │
│  actualizarTarea() → updateEstadoTarea()                │
└─────────────────────────────────────────────────────────┘
          │
          │ Llama servicio
          │
          ▼
┌─────────────────────────────────────────────────────────┐
│             services/api.js                            │
│  fetch(PATCH /api/tareas/{id}/completar)               │
└─────────────────────────────────────────────────────────┘
          │
          │ HTTP Request
          │
          ▼
┌─────────────────────────────────────────────────────────┐
│        Backend Spring Boot                              │
│  TareaControlador → TareaServicio → Base de datos       │
└─────────────────────────────────────────────────────────┘
          │
          │ HTTP Response
          │
          ▼
┌─────────────────────────────────────────────────────────┐
│         setTareas(tareas.map(...))                      │
│  Estado actualizado en React                           │
└─────────────────────────────────────────────────────────┘
          │
          │ Re-render
          │
          ▼
┌─────────────────────────────────────────────────────────┐
│      TareaItem.jsx (actualizado)                        │
│  El checkbox refleja el nuevo estado ✅                 │
└─────────────────────────────────────────────────────────┘
```

## 🌐 Comunicación Cliente-Servidor

```
Cliente (React)                      Servidor (Spring Boot)
    │                                        │
    │──────── GET /api/tareas ────────────►  │
    │         (al cargar)                    │
    │                                        │
    │◄───── Array de tareas JSON ───────────│
    │                                        │
    │  (Usuario interactúa)                  │
    │                                        │
    │──── PATCH /api/tareas/1/completar ──► │
    │      (marca como completa)             │
    │                                        │
    │◄─── Tarea actualizada (JSON) ────────│
    │                                        │
    │──── PATCH /api/tareas/1/descompletar ─► │
    │      (desmarcar)                       │
    │                                        │
    │◄─── Tarea actualizada (JSON) ────────│
    │                                        │
```

## 📱 Componentes y sus responsabilidades

```
App.jsx
├─ Responsabilidad: Componente raíz
├─ Estado: [tareas, cargando, error]
├─ Efectos: useEffect(() => cargarTareas)
├─ Props a hijos: [tareas, onAlternarCompletada]
└─ Funciones: alternarCompletada()

TareaLista.jsx
├─ Responsabilidad: Contenedor de lista
├─ Estado: Ninguno (controlled component)
├─ Props: [tareas, onAlternarCompletada]
├─ Renderiza: array.map() → TareaItem
└─ Funciones: Ninguna lógica

TareaItem.jsx
├─ Responsabilidad: Ítem individual
├─ Estado: Ninguno (controlled)
├─ Props: [tarea, onAlternarCompletada]
├─ Eventos: onChange → handleChange()
└─ Funciones: handleChange()

useTareas Hook
├─ Responsabilidad: Lógica centralizada
├─ Estado: [tareas, cargando, error]
├─ API: fetchTareas(), actualizarEstadoTarea()
└─ Retorna: {tareas, cargando, error, actualizarTarea}

services/api.js
├─ Responsabilidad: Llamadas HTTP
├─ Endpoints: GET, PATCH
├─ Errores: Throw automático
└─ Retorna: JSON o error
```

## 🔀 Estados posibles

```
Cargando: true    ──→  "Cargando tareas..."
Cargando: false   ──→  Renderizar lista

Error: null       ──→  Mostrar contenido
Error: mensaje    ──→  "Error: " + mensaje

Tareas: []        ──→  "No hay tareas aún"
Tareas: [...N]    ──→  Renderizar tareas
```

---

Esta arquitectura es escalable, mantenible y preparada para futuras mejoras como Tailwind CSS y JWT.

