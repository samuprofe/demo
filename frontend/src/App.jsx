import { useState } from 'react'
import { useAuth } from './hooks/useAuth'
import { useTareas } from './hooks/useTareas'
import BarraSuperior from './components/BarraSuperior'
import FormularioLogin from './components/FormularioLogin'
import FormularioRegistro from './components/FormularioRegistro'
import TareaLista from './components/TareaLista'
import './App.css'

function App() {
  const { usuario, cargando: authCargando, error: authError, login, registro, logout, fotoUrl } = useAuth()
  const { tareas, cargando: tareasCargando, error: tareasError, actualizarTarea } = useTareas(usuario?.id)
  const [vistaAuth, setVistaAuth] = useState('login')

  const handleLogin = async (correo, contrasena) => {
    await login(correo, contrasena)
  }

  const handleRegistro = async (datos, foto) => {
    await registro(datos, foto)
  }

  return (
    <div className="app">
      <BarraSuperior usuario={usuario} onLogout={logout} fotoUrl={fotoUrl} />

      <main className="contenido-principal">
        {!usuario ? (
          <div className="auth-contenedor">
            {vistaAuth === 'login' ? (
              <FormularioLogin
                onLogin={handleLogin}
                cargando={authCargando}
                error={authError}
                onIrRegistro={() => setVistaAuth('registro')}
              />
            ) : (
              <FormularioRegistro
                onRegistro={handleRegistro}
                cargando={authCargando}
                error={authError}
                onIrLogin={() => setVistaAuth('login')}
              />
            )}
          </div>
        ) : (
          <div className="tareas-contenedor">
            <h2>Mis tareas</h2>
            {tareasCargando && <p className="cargando">Cargando tareas...</p>}
            {tareasError && <p className="error-mensaje">{tareasError}</p>}
            {!tareasCargando && (
              <TareaLista tareas={tareas} onAlternarCompletada={actualizarTarea} />
            )}
          </div>
        )}
      </main>
    </div>
  )
}

export default App
