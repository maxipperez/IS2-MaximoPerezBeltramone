import React, { useState } from "react";
import { createPortal } from "react-dom";

function EjemploReactDOM() {
  const [abierto, setAbierto] = useState(false);
  const [hora, setHora] = useState(new Date().toLocaleTimeString());

  return (
    <div>
      <h2>Ejemplo de React DOM</h2>

      <h3>createPortal</h3>

      <button onClick={() => setAbierto(true)}>
        Abrir ventana
      </button>

      {abierto &&
        createPortal(
          <div className="modal-fondo">
            <div className="modal">
              <h3>Ventana creada con createPortal</h3>

              <p>
                Esta ventana se muestra dentro del body de la página y no
                dentro de la tarjeta donde está el botón.
              </p>

              <button onClick={() => setAbierto(false)}>
                Cerrar
              </button>
            </div>
          </div>,
          document.body
        )}

      <hr />

      <h3>Actualización de la pantalla</h3>

      <p>Hora actual: {hora}</p>

      <button onClick={() => setHora(new Date().toLocaleTimeString())}>
        Actualizar hora
      </button>
    </div>
  );
}

export default EjemploReactDOM;
