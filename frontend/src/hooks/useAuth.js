import { useState, useCallback } from 'react'
import { loginUsuario, crearUsuario, subirFotoUsuario } from '../services/api'
import { API_ENDPOINTS } from '../services/api'

const CLAVE_USUARIO = 'usuario_actual'

const cargarUsuarioGuardado = () => {
  try {
    const datos = localStorage.getItem(CLAVE_USUARIO)
    return datos ? JSON.parse(datos) : null
  } catch {
    return null
  }
}

export const useAuth = () => {
  const [usuario, setUsuario] = useState(cargarUsuarioGuardado)
  const [cargando, setCargando] = useState(false)
  const [error, setError] = useState(null)

  const guardarUsuario = (datos) => {
    localStorage.setItem(CLAVE_USUARIO, JSON.stringify(datos))
    setUsuario(datos)
  }

  const login = useCallback(async (correoElectronico, contrasena) => {
    setCargando(true)
    setError(null)
    try {
      const datos = await loginUsuario(correoElectronico, contrasena)
      guardarUsuario(datos)
      return datos
    } catch (err) {
      setError(err.message)
      throw err
    } finally {
      setCargando(false)
    }
  }, [])

  const registro = useCallback(async (datosUsuario, archivoFoto) => {
    setCargando(true)
    setError(null)
    try {
      const nuevoUsuario = await crearUsuario(datosUsuario)
      if (archivoFoto) {
        await subirFotoUsuario(nuevoUsuario.id, archivoFoto)
        nuevoUsuario.tieneFoto = true
      }
      guardarUsuario(nuevoUsuario)
      return nuevoUsuario
    } catch (err) {
      setError(err.message)
      throw err
    } finally {
      setCargando(false)
    }
  }, [])

  const logout = useCallback(() => {
    localStorage.removeItem(CLAVE_USUARIO)
    setUsuario(null)
    setError(null)
  }, [])

  const fotoUrl = (id) => {
    return API_ENDPOINTS.FOTO_USUARIO(id)
  }

  return {
    usuario,
    cargando,
    error,
    login,
    registro,
    logout,
    fotoUrl,
  }
}

export default useAuth
