import { useEffect, useState } from 'react';
import {
  addDoc,
  collection,
  deleteDoc,
  doc,
  onSnapshot,
  orderBy,
  query,
  serverTimestamp,
  updateDoc,
} from 'firebase/firestore';
import { db, firebaseIsConfigured } from './firebase.js';

const initialForm = { nombre: '', categoria: '', precio: '', stock: '' };

function App() {
  const [productos, setProductos] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState(null);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(firebaseIsConfigured);

  useEffect(() => {
    if (!firebaseIsConfigured) return undefined;

    const productsQuery = query(collection(db, 'productos'), orderBy('creadoEn', 'desc'));
    const unsubscribe = onSnapshot(
      productsQuery,
      (snapshot) => {
        setProductos(snapshot.docs.map((item) => ({ id: item.id, ...item.data() })));
        setLoading(false);
      },
      (error) => {
        setMessage(`No se pudieron cargar los datos: ${error.message}`);
        setLoading(false);
      },
    );
    return unsubscribe;
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((previous) => ({ ...previous, [name]: value }));
  };

  const resetForm = () => {
    setForm(initialForm);
    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!firebaseIsConfigured) return;

    const producto = {
      nombre: form.nombre.trim(),
      categoria: form.categoria.trim(),
      precio: Number(form.precio),
      stock: Number(form.stock),
    };

    try {
      if (editingId) {
        await updateDoc(doc(db, 'productos', editingId), producto);
        setMessage('Producto actualizado correctamente.');
      } else {
        await addDoc(collection(db, 'productos'), { ...producto, creadoEn: serverTimestamp() });
        setMessage('Producto creado correctamente.');
      }
      resetForm();
    } catch (error) {
      setMessage(`No se pudo guardar: ${error.message}`);
    }
  };

  const editProduct = (producto) => {
    setForm({
      nombre: producto.nombre,
      categoria: producto.categoria,
      precio: producto.precio,
      stock: producto.stock,
    });
    setEditingId(producto.id);
    setMessage('Editando producto seleccionado.');
  };

  const removeProduct = async (id) => {
    if (!window.confirm('¿Eliminar este producto?')) return;
    try {
      await deleteDoc(doc(db, 'productos', id));
      setMessage('Producto eliminado correctamente.');
    } catch (error) {
      setMessage(`No se pudo eliminar: ${error.message}`);
    }
  };

  return (
    <main className="container">
      <header>
        <p className="eyebrow">Ejercicio 3j · React + Firebase</p>
        <h1>ABM de productos</h1>
        <p>Alta, baja y modificación de productos usando Cloud Firestore en tiempo real.</p>
      </header>

      {!firebaseIsConfigured && (
        <p className="notice">Falta configurar Firebase. Copiá <code>.env.example</code> como <code>.env</code> y completá las credenciales.</p>
      )}
      {message && <p className="message">{message}</p>}

      <section className="layout">
        <form onSubmit={handleSubmit} className="card">
          <h2>{editingId ? 'Modificar producto' : 'Nuevo producto'}</h2>
          <label>Nombre<input name="nombre" value={form.nombre} onChange={handleChange} required /></label>
          <label>Categoría<input name="categoria" value={form.categoria} onChange={handleChange} required /></label>
          <label>Precio<input name="precio" type="number" min="0" step="0.01" value={form.precio} onChange={handleChange} required /></label>
          <label>Stock<input name="stock" type="number" min="0" step="1" value={form.stock} onChange={handleChange} required /></label>
          <div className="actions">
            <button disabled={!firebaseIsConfigured}>{editingId ? 'Guardar cambios' : 'Crear producto'}</button>
            {editingId && <button type="button" className="secondary" onClick={resetForm}>Cancelar</button>}
          </div>
        </form>

        <section className="card">
          <h2>Productos registrados</h2>
          {loading && <p>Cargando productos…</p>}
          {!loading && productos.length === 0 && <p>Aún no hay productos cargados.</p>}
          {productos.length > 0 && (
            <div className="table-wrapper"><table>
              <thead><tr><th>Nombre</th><th>Categoría</th><th>Precio</th><th>Stock</th><th>Acciones</th></tr></thead>
              <tbody>{productos.map((producto) => (
                <tr key={producto.id}>
                  <td>{producto.nombre}</td><td>{producto.categoria}</td><td>${producto.precio.toFixed(2)}</td><td>{producto.stock}</td>
                  <td><button className="link" onClick={() => editProduct(producto)}>Editar</button><button className="link danger" onClick={() => removeProduct(producto.id)}>Eliminar</button></td>
                </tr>
              ))}</tbody>
            </table></div>
          )}
        </section>
      </section>
    </main>
  );
}

export default App;
