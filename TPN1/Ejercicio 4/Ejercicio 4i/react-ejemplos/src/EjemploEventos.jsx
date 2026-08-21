function EjemploEventos() {

  function mostrarMensaje() {
    alert("¡Presionaste el botón!");
  }

  return (
    <div>
      <h2>Ejemplo de Eventos</h2>

      <button onClick={mostrarMensaje}>
        Hacer clic
      </button>
    </div>
  );
}

export default EjemploEventos;