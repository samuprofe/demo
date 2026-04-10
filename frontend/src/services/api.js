// Configuración central de la API

const API_BASE_URL = import.meta.env.VITE_API_URL

export const API_ENDPOINTS = {
  TAREAS: `${API_BASE_URL}/tareas`,
  TAREA: (id) => `${API_BASE_URL}/tareas/${id}`,
  COMPLETAR_TAREA: (id) => `${API_BASE_URL}/tareas/${id}/completar`,
  DESCOMPLETAR_TAREA: (id) => `${API_BASE_URL}/tareas/${id}/descompletar`,
}

export const fetchTareas = async () => {
  const response = await fetch(API_ENDPOINTS.TAREAS)
  if (!response.ok) {
    throw new Error('Error al cargar las tareas')
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

export default {
  API_ENDPOINTS,
  fetchTareas,
  actualizarEstadoTarea,
}

