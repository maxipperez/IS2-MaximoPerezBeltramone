import React, { memo, useCallback, useMemo, useState } from "react";

const Boton = memo(function Boton({ onClick, texto }) {
  console.log("Se mostró el botón");

  return <button onClick={onClick}>{texto}</button>;
});

function EjemploHooksRendimiento() {
  const [numeros, setNumeros] = useState([5, 10, 15]);
  const [contador, setContador] = useState(0);

  const suma = useMemo(() => {
    console.log("Calculando la suma...");

    return numeros.reduce((total, numero) => total + numero, 0);
  }, [numeros]);

  const agregarNumero = useCallback(() => {
    setNumeros((lista) => [...lista, Math.floor(Math.random() * 10) + 1]);
  }, []);

  return (
    <div>
      <h2>Ejemplo de Hooks de rendimiento</h2>

      <h3>useMemo</h3>

      <p>Números: {numeros.join(", ")}</p>

      <p>Suma: {suma}</p>

      <h3>useCallback + memo</h3>

      <Boton onClick={agregarNumero} texto="Agregar número" />

      <hr />

      <h3>Estado sin relación</h3>

      <p>Contador: {contador}</p>

      <button onClick={() => setContador(contador + 1)}>
        Sumar al contador
      </button>

      <p>
        Abrí la consola del navegador para ver cuándo se calcula la suma y
        cuándo se muestra el botón.
      </p>
    </div>
  );
}

export default EjemploHooksRendimiento;
