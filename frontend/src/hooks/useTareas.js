import { useState, useEffect, useCallback } from 'react'
import { fetchTareasUsuario, actualizarEstadoTarea } from '../services/api'

export const useTareas = (usuarioId) => {
  const [tareas, setTareas] = useState([])
  const [cargando, setCargando] = useState(true)
  const [error, setError] = useState(null)

  const cargarTareas = useCallback(async () => {
    if (!usuarioId) {
      setTareas([])
      setCargando(false)
      return
    }
    try {
      setCargando(true)
      setError(null)
      const datos = await fetchTareasUsuario(usuarioId)
      setTareas(datos)
    } catch (err) {
      setError(err.message)
      console.error('Error cargando tareas:', err)
    } finally {
      setCargando(false)
    }
  }, [usuarioId])

  useEffect(() => {
    cargarTareas()
  }, [cargarTareas])

  const actualizarTarea = async (id, completada) => {
    try {
      const tareaActualizada = await actualizarEstadoTarea(id, !completada)
      setTareas(tareas.map(tarea =>
        tarea.id === id ? tareaActualizada : tarea
      ))
    } catch (err) {
      console.error('Error al actualizar tarea:', err)
      setError(err.message)
      throw err
    }
  }

  return {
    tareas,
    cargando,
    error,
    actualizarTarea,
    recargar: cargarTareas,
  }
}

export default useTareas
