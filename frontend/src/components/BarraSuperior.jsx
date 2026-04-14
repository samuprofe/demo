function BarraSuperior({ usuario, onLogout, fotoUrl }) {
  return (
    <header className="barra-superior">
      <div className="barra-titulo">
        <h1>📋 Gestor de Tareas</h1>
      </div>
      {usuario && (
        <div className="barra-usuario">
          <div className="usuario-foto-contenedor">
            {usuario.tieneFoto ? (
              <img
                src={fotoUrl(usuario.id)}
                alt={`Foto de ${usuario.nombreCompleto}`}
                className="usuario-foto"
              />
            ) : (
              <div className="usuario-foto-placeholder">
                {usuario.nombreCompleto.charAt(0).toUpperCase()}
              </div>
            )}
          </div>
          <span className="usuario-nombre">{usuario.nombreCompleto}</span>
          <button className="btn-logout" onClick={onLogout}>
            Cerrar sesión
          </button>
        </div>
      )}
    </header>
  )
}

export default BarraSuperior
