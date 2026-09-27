import React, { useState } from "react";

function Contador() {
  const [valor, setValor] = useState(0);

  return (
    <div>
      <h2>Ejemplo de Jest y React Testing Library</h2>

      <p>Valor: {valor}</p>

      <button onClick={() => setValor(valor + 1)}>
        Sumar
      </button>

      <button onClick={() => setValor(valor - 1)} disabled={valor === 0}>
        Restar
      </button>

      <button onClick={() => setValor(0)}>
        Reiniciar
      </button>

      <p>Ejecutá npm test para correr las pruebas de este componente.</p>
    </div>
  );
}

export default Contador;
