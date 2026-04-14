function TareaItem({ tarea, onAlternarCompletada }) {
  const handleChange = () => {
    onAlternarCompletada(tarea.id, tarea.completada)
  }

  return (
    <li className="tarea-item">
      <input
        type="checkbox"
        checked={tarea.completada}
        onChange={handleChange}
        id={`tarea-${tarea.id}`}
      />
      <label htmlFor={`tarea-${tarea.id}`} className={tarea.completada ? 'completada' : ''}>
        <strong>{tarea.titulo}</strong>
        {tarea.texto && <p>{tarea.texto}</p>}
        {tarea.fechaLimite && (
          <small>
            Fecha límite: {new Date(tarea.fechaLimite).toLocaleDateString('es-ES')}
          </small>
        )}
      </label>
    </li>
  )
}

export default TareaItem
