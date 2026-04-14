const API_BASE_URL = import.meta.env.VITE_API_URL

export const API_ENDPOINTS = {
  USUARIOS: `${API_BASE_URL}/usuarios`,
  USUARIO: (id) => `${API_BASE_URL}/usuarios/${id}`,
  FOTO_USUARIO: (id) => `${API_BASE_URL}/usuarios/${id}/foto`,
  TAREAS: `${API_BASE_URL}/tareas`,
  TAREA: (id) => `${API_BASE_URL}/tareas/${id}`,
  TAREAS_USUARIO: (usuarioId) => `${API_BASE_URL}/tareas/usuario/${usuarioId}`,
  COMPLETAR_TAREA: (id) => `${API_BASE_URL}/tareas/${id}/completar`,
  DESCOMPLETAR_TAREA: (id) => `${API_BASE_URL}/tareas/${id}/descompletar`,
  AUTH_LOGIN: `${API_BASE_URL}/auth/login`,
}

export const fetchTareas = async () => {
  const response = await fetch(API_ENDPOINTS.TAREAS)
  if (!response.ok) {
    throw new Error('Error al cargar las tareas')
  }
  return await response.json()
}

export const fetchTareasUsuario = async (usuarioId) => {
  const response = await fetch(API_ENDPOINTS.TAREAS_USUARIO(usuarioId))
  if (!response.ok) {
    throw new Error('Error al cargar las tareas del usuario')
  }
  return await response.json()
}

export const actualizarEstadoTarea = async (id, completada) => {
  const endpoint = completada
    ? API_ENDPOINTS.COMPLETAR_TAREA(id)
    : API_ENDPOINTS.DESCOMPLETAR_TAREA(id)

  const response = await fetch(endpoint, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  if (!response.ok) {
    throw new Error('Error al actualizar la tarea')
  }

  return await response.json()
}

export const crearUsuario = async (datos) => {
  const response = await fetch(API_ENDPOINTS.USUARIOS, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(datos),
  })
  if (!response.ok) {
    const error = await response.json().catch(() => ({}))
    throw new Error(error.detail || error.message || 'Error al crear el usuario')
  }
  return await response.json()
}

export const subirFotoUsuario = async (id, archivo) => {
  const formData = new FormData()
  formData.append('foto', archivo)
  const response = await fetch(API_ENDPOINTS.FOTO_USUARIO(id), {
    method: 'POST',
    body: formData,
  })
  if (!response.ok) {
    throw new Error('Error al subir la foto de perfil')
  }
}

export const loginUsuario = async (correoElectronico, contrasena) => {
  const response = await fetch(API_ENDPOINTS.AUTH_LOGIN, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ correoElectronico, contrasena }),
  })
  if (!response.ok) {
    const error = await response.json().catch(() => ({}))
    throw new Error(error.detail || error.message || 'Credenciales incorrectas')
  }
  return await response.json()
}

export default {
  API_ENDPOINTS,
  fetchTareas,
  fetchTareasUsuario,
  actualizarEstadoTarea,
  crearUsuario,
  subirFotoUsuario,
  loginUsuario,
}
