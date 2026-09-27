import React from "react";
import Contador from "../Jest/EjemploContador.jsx";

function App() {
  return (
    <main className="app">
      <header className="hero">
        <span className="eyebrow">Trabajo Práctico</span>
        <h1>React en acción</h1>
        <p>Ingeniería del Software II · Jest y React Testing Library</p>
      </header>

      <section className="card"><Contador /></section>
    </main>
  );
}

export default App;
