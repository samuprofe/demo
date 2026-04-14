import TareaItem from './TareaItem'

function TareaLista({ tareas, onAlternarCompletada }) {
  if (tareas.length === 0) {
    return <div className="sin-tareas">No hay tareas aún</div>
  }

  return (
    <div className="tarea-lista">
      <ul>
        {tareas.map((tarea) => (
          <TareaItem
            key={tarea.id}
            tarea={tarea}
            onAlternarCompletada={onAlternarCompletada}
          />
        ))}
      </ul>
    </div>
  )
}

export default TareaLista
