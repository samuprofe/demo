import { useState } from 'react'

function FormularioLogin({ onLogin, cargando, error, onIrRegistro }) {
  const [correo, setCorreo] = useState('')
  const [contrasena, setContrasena] = useState('')

  const handleSubmit = async (e) => {
    e.preventDefault()
    await onLogin(correo, contrasena)
  }

  return (
    <div className="formulario-contenedor">
      <h2>Iniciar sesión</h2>
      <form onSubmit={handleSubmit} className="formulario">
        <div className="campo">
          <label htmlFor="correo">Correo electrónico</label>
          <input
            id="correo"
            type="email"
            value={correo}
            onChange={(e) => setCorreo(e.target.value)}
            required
            placeholder="correo@ejemplo.com"
          />
        </div>
        <div className="campo">
          <label htmlFor="contrasena">Contraseña</label>
          <input
            id="contrasena"
            type="password"
            value={contrasena}
            onChange={(e) => setContrasena(e.target.value)}
            required
            placeholder="Tu contraseña"
          />
        </div>
        {error && <p className="error-mensaje">{error}</p>}
        <button type="submit" disabled={cargando} className="btn-primario">
          {cargando ? 'Iniciando sesión...' : 'Iniciar sesión'}
        </button>
      </form>
      <p className="enlace-alternativo">
        ¿No tienes cuenta?{' '}
        <button type="button" onClick={onIrRegistro} className="btn-enlace">
          Regístrate
        </button>
      </p>
    </div>
  )
}

export default FormularioLogin
