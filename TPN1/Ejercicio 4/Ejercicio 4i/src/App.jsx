import React from "react";
import EjemploEstados from "../Estados/EjemploEstados.jsx";
import EjemploEventos from "../Eventos/EjemploEventos.jsx";
import EjemploHooks from "../Hooks/EjemploHooks.jsx";

function App() {
  return (
    <main className="app">
      <header className="hero">
        <span className="eyebrow">Trabajo Práctico</span>
        <h1>React en acción</h1>
        <p>Ingeniería del Software II · Estados, eventos y hooks</p>
      </header>

      <section className="card"><EjemploEstados /></section>
      <section className="card"><EjemploEventos /></section>
      <section className="card"><EjemploHooks /></section>
    </main>
  );
}

export default App;
