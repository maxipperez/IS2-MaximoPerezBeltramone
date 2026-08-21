import React, { useEffect, useState } from "react";

function EjemploHooks() {
  const [nombre, setNombre] = useState("");
  const [contador, setContador] = useState(0);

  useEffect(() => {
    document.title = `Clicks: ${contador}`;
  }, [contador]);

  return (
    <div>
      <h2>Ejemplo de Hooks</h2>

      <h3>useState</h3>

      <input
        type="text"
        placeholder="Ingresá tu nombre"
        value={nombre}
        onChange={(evento) => setNombre(evento.target.value)}
      />

      {nombre && <p>Bienvenido, {nombre}</p>}

      <hr />

      <h3>useState + useEffect</h3>

      <p>Cantidad de clicks: {contador}</p>

      <button onClick={() => setContador(contador + 1)}>
        Sumar click
      </button>

      <button onClick={() => setContador(0)}>
        Reiniciar
      </button>

      <p>
        Mirá el título de la pestaña del navegador cuando aumenta el contador.
      </p>
    </div>
  );
}

export default EjemploHooks;
