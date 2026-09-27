import React from "react";
import EjemploHooksRendimiento from "../HooksRendimiento/EjemploHooksRendimiento.jsx";
import EjemploContexto from "../Contexto/EjemploContexto.jsx";

function App() {
  return (
    <main className="app">
      <header className="hero">
        <span className="eyebrow">Trabajo Práctico</span>
        <h1>React en acción</h1>
        <p>Ingeniería del Software II · Hooks de rendimiento y Contexto</p>
      </header>

      <section className="card"><EjemploHooksRendimiento /></section>
      <section className="card"><EjemploContexto /></section>
    </main>
  );
}

export default App;
