import { useState, useRef } from 'react'

function FormularioRegistro({ onRegistro, cargando, error, onIrLogin }) {
  const [nombreCompleto, setNombreCompleto] = useState('')
  const [correo, setCorreo] = useState('')
  const [contrasena, setContrasena] = useState('')
  const [fotoPreview, setFotoPreview] = useState(null)
  const [archivoFoto, setArchivoFoto] = useState(null)
  const inputFotoRef = useRef(null)

  const handleFotoChange = (e) => {
    const archivo = e.target.files[0]
    if (!archivo) return
    setArchivoFoto(archivo)
    const reader = new FileReader()
    reader.onloadend = () => setFotoPreview(reader.result)
    reader.readAsDataURL(archivo)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    await onRegistro(
      { nombreCompleto, correoElectronico: correo, contrasena },
      archivoFoto
    )
  }

  return (
    <div className="formulario-contenedor">
      <h2>Crear cuenta</h2>
      <form onSubmit={handleSubmit} className="formulario">
        <div className="foto-upload-contenedor">
          <div
            className="foto-upload-circulo"
            onClick={() => inputFotoRef.current?.click()}
            role="button"
            tabIndex={0}
            onKeyDown={(e) => e.key === 'Enter' && inputFotoRef.current?.click()}
            aria-label="Seleccionar foto de perfil"
          >
            {fotoPreview ? (
              <img src={fotoPreview} alt="Vista previa de foto de perfil" className="foto-preview" />
            ) : (
              <span className="foto-placeholder-texto">📷 Foto</span>
            )}
          </div>
          <input
            ref={inputFotoRef}
            type="file"
            accept="image/*"
            onChange={handleFotoChange}
            style={{ display: 'none' }}
            aria-label="Subir foto de perfil"
          />
          <p className="foto-hint">Haz clic para añadir foto de perfil (opcional)</p>
        </div>

        <div className="campo">
          <label htmlFor="nombreCompleto">Nombre completo</label>
          <input
            id="nombreCompleto"
            type="text"
            value={nombreCompleto}
            onChange={(e) => setNombreCompleto(e.target.value)}
            required
            placeholder="Tu nombre"
          />
        </div>
        <div className="campo">
          <label htmlFor="correoReg">Correo electrónico</label>
          <input
            id="correoReg"
            type="email"
            value={correo}
            onChange={(e) => setCorreo(e.target.value)}
            required
            placeholder="correo@ejemplo.com"
          />
        </div>
        <div className="campo">
          <label htmlFor="contrasenaReg">Contraseña</label>
          <input
            id="contrasenaReg"
            type="password"
            value={contrasena}
            onChange={(e) => setContrasena(e.target.value)}
            required
            minLength={6}
            placeholder="Mínimo 6 caracteres"
          />
        </div>
        {error && <p className="error-mensaje">{error}</p>}
        <button type="submit" disabled={cargando} className="btn-primario">
          {cargando ? 'Creando cuenta...' : 'Crear cuenta'}
        </button>
      </form>
      <p className="enlace-alternativo">
        ¿Ya tienes cuenta?{' '}
        <button type="button" onClick={onIrLogin} className="btn-enlace">
          Inicia sesión
        </button>
      </p>
    </div>
  )
}

export default FormularioRegistro
