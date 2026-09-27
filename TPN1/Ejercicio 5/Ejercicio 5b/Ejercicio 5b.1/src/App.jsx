import React from "react";
import EjemploReactDOM from "../ReactDOM/EjemploReactDOM.jsx";
import EjemploReactRouter from "../ReactRouter/EjemploReactRouter.jsx";

function App() {
  return (
    <main className="app">
      <header className="hero">
        <span className="eyebrow">Trabajo Práctico</span>
        <h1>React en acción</h1>
        <p>Ingeniería del Software II · React DOM y React Router</p>
      </header>

      <section className="card"><EjemploReactDOM /></section>
      <section className="card"><EjemploReactRouter /></section>
    </main>
  );
}

export default App;
