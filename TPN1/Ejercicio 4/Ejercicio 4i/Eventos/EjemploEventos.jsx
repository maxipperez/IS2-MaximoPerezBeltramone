import { useState } from "react";

function EjemploEventos() {
  const [mensaje, setMensaje] = useState("Todavía no ocurrió ningún evento");
  const [texto, setTexto] = useState("");

  function manejarClick() {
    setMensaje("Hiciste clic en el botón");
  }

  function manejarCambio(evento) {
    setTexto(evento.target.value);
  }

  function manejarEnvio(evento) {
    evento.preventDefault();

    setMensaje(`Formulario enviado: ${texto}`);
  }

  return (
    <div>
      <h2>Ejemplo de Eventos</h2>

      <button onClick={manejarClick}>
        Hacer clic
      </button>

      <p>{mensaje}</p>

      <hr />

      <form onSubmit={manejarEnvio}>
        <label>Escribí un mensaje: </label>

        <input
          type="text"
          value={texto}
          onChange={manejarCambio}
        />

        <button type="submit">
          Enviar
        </button>
      </form>

      <p>Texto actual: {texto}</p>
    </div>
  );
}

export default EjemploEventos;