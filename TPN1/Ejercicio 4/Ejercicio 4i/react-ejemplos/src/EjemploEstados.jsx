import { useState } from "react";

function EjemploEstados() {
  const [contador, setContador] = useState(0);

  return (
    <div>
      <h2>Ejemplo de Estados</h2>

      <p>Contador: {contador}</p>

      <button onClick={() => setContador(contador + 1)}>
        Aumentar
      </button>
    </div>
  );
}

export default EjemploEstados;