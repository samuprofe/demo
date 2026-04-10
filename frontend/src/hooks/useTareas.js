import { useState, useEffect } from 'react'
import { fetchTareas, actualizarEstadoTarea } from '../services/api'

export const useTareas = () => {
  const [tareas, setTareas] = useState([])
  const [cargando, setCargando] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    cargarTareas()
  }, [])

  const cargarTareas = async () => {
    try {
      setCargando(true)
      setError(null)
      const datos = await fetchTareas()
      setTareas(datos)
    } catch (err) {
      setError(err.message)
      console.error('Error cargando tareas:', err)
    } finally {
      setCargando(false)
    }
  }

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

