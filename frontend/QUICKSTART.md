# ⚡ Quick Start - Frontend Gestor de Tareas

## 🎯 Objetivo

Frontend funcional en React + Vite que lista todas las tareas y permite marcar/desmarcar.

## ✅ Ya completado

- ✨ Proyecto React + Vite creado
- ✨ Componentes de tareas (lista e ítem)
- ✨ Hook personalizado para lógica
- ✨ Servicio centralizado de API
- ✨ Conexión al backend funcionando
- ✨ Checkboxes para marcar/desmarcar tareas
- ✨ Gestión de errores y estados de carga
- ✨ Configuración con variables de entorno
- ✨ Arquitectura escalable para futuras mejoras

## 🚀 Ejecutar en 2 comandos

### Terminal 1 (Backend)
```bash
cd C:\Users\Samuel2\Downloads\demo\demo
.\mvnw.cmd spring-boot:run
```

### Terminal 2 (Frontend)
```bash
cd C:\Users\Samuel2\Downloads\demo\demo\frontend
npm run dev
```

Luego abre: **http://localhost:5173**

## 📁 Carpetas principales

```
frontend/
├── src/
│   ├── components/      # Componentes React
│   ├── hooks/          # Lógica personalizada
│   ├── services/       # Llamadas a API
│   ├── App.jsx         # Componente principal
│   └── main.jsx        # Entrada
└── .env                # Configuración
```

## 🎮 Funcionalidades

| Función | Estado |
|---------|--------|
| Ver todas las tareas | ✅ Hecho |
| Marcar como completa | ✅ Hecho |
| Desmarcar tarea | ✅ Hecho |
| Manejo de errores | ✅ Hecho |
| Estado de carga | ✅ Hecho |

## 📝 Notas importantes

✓ **Sin estilos CSS** - Como solicitaste  
✓ **Todas las tareas** - No hay filtro de usuario aún  
✓ **Cambios en tiempo real** - Sync con API  
✓ **Listo para Tailwind CSS** - Cuando lo necesites  
✓ **Preparado para JWT** - Cuando implementes autenticación  

## 🔄 Próximas mejoras

1. Tailwind CSS (estilos)
2. JWT Authentication (login)
3. Filtrar por usuario
4. Crear/editar/eliminar tareas
5. Filtros y búsqueda

## 📚 Documentación

- `README.md` - Información del proyecto
- `SETUP.md` - Instalación y configuración
- `DEVELOPMENT.md` - Guía detallada de desarrollo

## 🆘 Ayuda rápida

**¿No se cargan las tareas?**
- Verifica que el backend esté corriendo
- Comprueba en F12 → Network si hay errores

**¿El servidor no inicia?**
- Asegúrate de estar en la carpeta `frontend`
- Ejecuta: `npm install`

**¿Quiero cambiar la API URL?**
- Edita `.env`
- Cambia: `VITE_API_URL=http://localhost:8080/api`

---

## 🎉 ¡Listo!

El frontend está completamente funcional. Ahora puedes:

1. Probar que todo funciona
2. Empezar a agregar estilos con Tailwind CSS
3. Implementar autenticación JWT cuando estés listo
4. Agregar más funcionalidades

¡Éxito! 🚀

