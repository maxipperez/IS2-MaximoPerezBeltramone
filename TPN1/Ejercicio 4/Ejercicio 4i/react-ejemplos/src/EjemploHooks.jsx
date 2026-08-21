import { useEffect, useState } from "react";

function EjemploHooks() {
  const [contador, setContador] = useState(0);

  useEffect(() => {
    document.title = `Contador: ${contador}`;
  }, [contador]);

  return (
    <div>
      <h2>Ejemplo de Hooks</h2>

      <p>Contador: {contador}</p>

      <button onClick={() => setContador(contador + 1)}>
        Aumentar contador
      </button>
    </div>
  );
}

export default EjemploHooks;