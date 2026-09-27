import React from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  Link,
  useParams,
  useNavigate,
} from "react-router-dom";

const productos = [
  { id: 1, nombre: "Notebook", precio: 1200 },
  { id: 2, nombre: "Monitor", precio: 350 },
  { id: 3, nombre: "Teclado", precio: 80 },
];

function Inicio() {
  return <p>Bienvenido a la página de inicio.</p>;
}

function Productos() {
  return (
    <div>
      <p>Seleccioná un producto para ver su detalle:</p>

      <ul>
        {productos.map((producto) => (
          <li key={producto.id}>
            <Link to={`/productos/${producto.id}`}>{producto.nombre}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

function DetalleProducto() {
  const { id } = useParams();
  const navigate = useNavigate();

  const producto = productos.find((p) => p.id === Number(id));

  if (!producto) {
    return <p>El producto no existe.</p>;
  }

  return (
    <div>
      <h3>{producto.nombre}</h3>

      <p>Precio: ${producto.precio}</p>

      <button onClick={() => navigate("/productos")}>
        Volver
      </button>
    </div>
  );
}

function Contacto() {
  return <p>Podés escribirnos a contacto@ejemplo.com</p>;
}

function NoEncontrada() {
  return <p>La página que buscás no existe.</p>;
}

function EjemploReactRouter() {
  return (
    <BrowserRouter>
      <div>
        <h2>Ejemplo de React Router</h2>

        <nav className="menu">
          <Link to="/">Inicio</Link>
          <Link to="/productos">Productos</Link>
          <Link to="/contacto">Contacto</Link>
          <Link to="/otra-pagina">Página inexistente</Link>
        </nav>

        <hr />

        <Routes>
          <Route path="/" element={<Inicio />} />
          <Route path="/productos" element={<Productos />} />
          <Route path="/productos/:id" element={<DetalleProducto />} />
          <Route path="/contacto" element={<Contacto />} />
          <Route path="*" element={<NoEncontrada />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default EjemploReactRouter;
