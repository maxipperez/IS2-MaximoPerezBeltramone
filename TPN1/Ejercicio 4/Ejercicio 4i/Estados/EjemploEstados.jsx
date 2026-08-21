import { useState } from "react";

function EjemploEstados() {
  const [contador, setContador] = useState(0);
  const [nombre, setNombre] = useState("");

  return (
    <div>
      <h2>Ejemplo de Estados</h2>

      <h3>Contador</h3>
      <p>Valor actual: {contador}</p>

      <button onClick={() => setContador(contador + 1)}>
        Aumentar
      </button>

      <button onClick={() => setContador(contador - 1)}>
        Disminuir
      </button>

      <button onClick={() => setContador(0)}>
        Reiniciar
      </button>

      <hr />

      <h3>Nombre del usuario</h3>

      <input
        type="text"
        placeholder="Escribí tu nombre"
        value={nombre}
        onChange={(evento) => setNombre(evento.target.value)}
      />

      <p>Hola {nombre || "usuario"}</p>
    </div>
  );
}

export default EjemploEstados;