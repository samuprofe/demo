# 🧪 Checklist de funcionamiento

## ✅ Verificación pre-ejecución

Antes de ejecutar el servidor, verifica:

- [ ] Node.js 16+ instalado (`node -v`)
- [ ] npm instalado (`npm -v`)
- [ ] Java 21 instalado (`java -version`)
- [ ] Maven funcionando (`.\mvnw.cmd -v`)
- [ ] Puerto 8080 disponible (backend)
- [ ] Puerto 5173 disponible (frontend)

## 🚀 Pasos de ejecución

### Terminal 1 - Backend
```bash
cd C:\Users\Samuel2\Downloads\demo\demo
.\mvnw.cmd spring-boot:run
```

Espera a ver:
- [ ] BUILD SUCCESS
- [ ] "Started DemoApplication in X.XXX seconds"
- [ ] "Tomcat started on port(s): 8080"

### Terminal 2 - Frontend
```bash
cd C:\Users\Samuel2\Downloads\demo\demo\frontend
npm run dev
```

Espera a ver:
- [ ] "VITE v8.0.X ready in XXX ms"
- [ ] "➜ Local: http://localhost:5173/"

### Terminal 3 (Opcional) - Navegador
Abre: `http://localhost:5173`

## 🎯 Pruebas funcionales

### Página carga correctamente
- [ ] No hay error 404
- [ ] Se ve "Gestor de Tareas" como título
- [ ] Se ve una lista de tareas
- [ ] No hay errores rojo en F12 (Consola)

### Tareas se muestran
- [ ] Mínimo 1 tarea visible
- [ ] Cada tarea tiene:
  - [ ] Checkbox
  - [ ] Título
  - [ ] (Opcional) Descripción
  - [ ] (Opcional) Fecha límite

### Funcionalidad de checkboxes
- [ ] Click en checkbox marca como completada
- [ ] Visual feedback inmediato
- [ ] Cambio se persiste (recarga página y verifica)
- [ ] Click nuevamente desmarcar

### Peticiones a API
Abre DevTools (F12) → Network:

- [ ] `GET /api/tareas` - Status 200
- [ ] `PATCH /api/tareas/X/completar` - Status 200
- [ ] `PATCH /api/tareas/X/descompletar` - Status 200

### No hay errores
Abre DevTools (F12) → Console:

- [ ] ✅ Sin errores rojos
- [ ] ✅ Sin warnings CORS
- [ ] ✅ Sin errores de componentes React

## 🔍 Verificaciones de código

### Estructura de archivos
```bash
# Debería existir:
- frontend/src/components/TareaItem.jsx ✅
- frontend/src/components/TareaLista.jsx ✅
- frontend/src/hooks/useTareas.js ✅
- frontend/src/services/api.js ✅
- frontend/src/App.jsx ✅
- frontend/.env ✅
```

### Variables de entorno
```bash
# En frontend/.env debe haber:
VITE_API_URL=http://localhost:8080/api ✅
```

### Dependencias
```bash
# En frontend/package.json debe haber:
"react": "^19.2.4" ✅
"react-dom": "^19.2.4" ✅
"vite": "^8.0.4" ✅
```

## 🧩 Pruebas de integración

### Test 1: Cargar tareas
1. Abre http://localhost:5173
2. ✅ Se muestran tareas

### Test 2: Marcar tarea
1. Click en primer checkbox
2. ✅ Se marca (visual)
3. ✅ Se envía PATCH a API
4. ✅ Estado persiste

### Test 3: Desmarcar tarea
1. Click en checkbox ya marcado
2. ✅ Se desmarca (visual)
3. ✅ Se envía PATCH a API
4. ✅ Estado vuelve a pendiente

### Test 4: Recarga de página
1. Marca/desmarcar algunas tareas
2. Recarga página (F5)
3. ✅ Los cambios persisten

### Test 5: Múltiples tareas
1. Marca varias tareas diferentes
2. ✅ Todas se actualizan correctamente
3. ✅ No hay conflictos

### Test 6: Error en API
1. Detén el backend
2. Intenta marcar una tarea
3. ✅ Se muestra un mensaje de error
4. Reinicia el backend
5. ✅ Las tareas vuelven a funcionar

## 📊 Performance

- [ ] Página carga en < 3 segundos
- [ ] Click en checkbox responde inmediatamente (< 1s)
- [ ] No hay lag visual
- [ ] Console sin warnings de performance

## 🎨 Visual (sin estilos)

- [ ] Página muestra contenido semánticamente correcto
- [ ] Todos los elementos son clickeables/usables
- [ ] Sin CSS roto
- [ ] Responsive (redimensiona navegador)

## 🔐 Seguridad

- [ ] No hay credenciales en el código
- [ ] Variables de entorno no están hardcodeadas
- [ ] CORS no muestra errores
- [ ] No hay console.log() de datos sensibles

## 📱 Cross-browser (opcional)

- [ ] Funciona en Chrome
- [ ] Funciona en Firefox
- [ ] Funciona en Edge
- [ ] Funciona en Safari

## 📝 Documentación

- [ ] README.md está completo
- [ ] QUICKSTART.md es claro
- [ ] DEVELOPMENT.md es detallado
- [ ] Todos los comentarios en código

## 🚨 Troubleshooting rápido

### "Cannot GET /api/tareas"
```bash
# Verifica:
1. Backend corriendo en 8080
2. Tareas existen en BD
3. F12 → Network → muestra error
```

### "CORS error"
```bash
# Verifica:
1. Backend tiene CORS configurado
2. URL frontend es http://localhost:5173
3. No hay proxy intermedio
```

### "El checkbox no funciona"
```bash
# Verifica:
1. F12 → Network → PATCH request se envía
2. Backend responde con 200
3. No hay errores en Console
```

### "Vite no inicia"
```bash
# Soluciona:
1. cd frontend
2. npm install
3. npm run dev
4. Si aún falla: netstat -ano | findstr :5173
```

## ✨ Checklist final

- [ ] Todo funciona sin errores
- [ ] Tareas se cargan
- [ ] Checkboxes funcionan
- [ ] Cambios se persisten
- [ ] Documentación completa
- [ ] Código limpio
- [ ] Listo para agregar Tailwind CSS

---

## 🎉 Si todo está marcado ✅

**¡El frontend está listo para usar!**

Próximo paso: Agregar estilos con Tailwind CSS.

---

## 📞 Comandos útiles para debugging

```bash
# Ver logs del backend en tiempo real
.\mvnw.cmd spring-boot:run

# Ver logs del frontend
npm run dev

# Limpiar cache
rm -r frontend/node_modules
npm install

# Forzar recargar (frontend)
npm run dev -- --force

# Ver puertos en uso
netstat -ano | findstr "8080\|5173"

# Matar proceso en puerto
taskkill /PID <PID> /F
```

## 📊 URLs importantes

| Recurso | URL |
|---------|-----|
| Frontend | http://localhost:5173 |
| Backend | http://localhost:8080 |
| API | http://localhost:8080/api |
| H2 Console | http://localhost:8080/h2-console |

---

Usa este checklist cada vez que hagas cambios o tengas problemas.

