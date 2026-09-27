import React, { createContext, useContext, useState } from "react";

const TemaContexto = createContext();

function Tarjeta() {
  const { tema } = useContext(TemaContexto);

  const estilo = {
    padding: "16px",
    borderRadius: "12px",
    background: tema === "claro" ? "#f3f6fc" : "#172033",
    color: tema === "claro" ? "#172033" : "#ffffff",
  };

  return <p style={estilo}>El tema actual es: {tema}</p>;
}

function BotonTema() {
  const { cambiarTema } = useContext(TemaContexto);

  return <button onClick={cambiarTema}>Cambiar tema</button>;
}

function Panel() {
  return (
    <div>
      <Tarjeta />
      <BotonTema />
    </div>
  );
}

function Saludo(props) {
  return <p>Hola {props.nombre}, este dato llegó mediante props.</p>;
}

function EjemploContexto() {
  const [tema, setTema] = useState("claro");

  function cambiarTema() {
    setTema(tema === "claro" ? "oscuro" : "claro");
  }

  return (
    <div>
      <h2>Ejemplo de Contexto</h2>

      <h3>useContext</h3>

      <TemaContexto.Provider value={{ tema, cambiarTema }}>
        <Panel />
      </TemaContexto.Provider>

      <hr />

      <h3>Props</h3>

      <Saludo nombre="Juan" />
    </div>
  );
}

export default EjemploContexto;
