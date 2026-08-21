package com.ejercicio4.mvc.services;

import com.ejercicio4.mvc.exceptions.ErrorService;
import com.ejercicio4.mvc.models.Producto;
import com.ejercicio4.mvc.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ¿Cuál es la responsabilidad de la capa de Servicio en el MVC?
 * El Servicio es el verdadero "cerebro" o núcleo de la aplicación. 
 * Mientras que el Modelo (M) define la estructura de los datos, el Controlador (C) atiende 
 * las peticiones HTTP y la Vista (V) dibuja las pantallas, el Servicio concentra las 
 * REGLAS DE NEGOCIO. Aquí validamos, calculamos y orquestamos antes de tocar la BD.
 * 
 * @Service: Es un estereotipo de Spring. Marca a esta clase como un Bean de lógica de negocio.
 *           Le indica al contenedor de Inversión de Control (IoC) de Spring que instancie 
 *           este objeto al iniciar la app para que pueda ser inyectado (@Autowired) en los Controladores.
 */
@Service
public class ProductoService {

    /**
     * @Autowired: Inyecta automáticamente la instancia de ProductoRepository creada por Spring.
     *             El servicio usa el repositorio para "hablar" con la base de datos sin escribir SQL.
     */
    @Autowired
    private ProductoRepository productoRepository;

    /**
     * @Transactional: Indica que este método debe ejecutarse dentro de una transacción de base de datos.
     *                 Una transacción garantiza el principio ACID (Atomicidad, Consistencia, Aislamiento, Durabilidad).
     *                 - Si todo el método se ejecuta con éxito, Spring hace un "commit" guardando los datos en MySQL.
     *                 - Si ocurre CUALQUIER excepción (por ejemplo, arrojamos un ErrorService), Spring hace un "rollback",
     *                   deshaciendo todo para que la base de datos no quede inconsistente.
     */
    @Transactional
    public void crearProducto(String nombre, double precio, int stock) throws ErrorService {
        // 1. Validar las reglas de negocio
        validarDatos(nombre, precio, stock);

        // 2. Instanciar la entidad
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        // Por defecto 'eliminado' ya es false según nuestro Modelo.

        // 3. Persistir usando el repositorio
        productoRepository.save(producto);
    }

    @Transactional
    public void modificarProducto(String id, String nombre, double precio, int stock) throws ErrorService {
        validarDatos(nombre, precio, stock);

        // El Repositorio devuelve un Optional (un envoltorio que puede tener el producto o estar vacío).
        Optional<Producto> respuesta = productoRepository.findById(id);
        
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get(); // Extraemos el producto
            
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            
            // Si el objeto ya existe y tiene un ID mapeado, .save() ejecuta un UPDATE en lugar de un INSERT.
            productoRepository.save(producto);
        } else {
            throw new ErrorService("No se pudo modificar: El producto con ID " + id + " no existe.");
        }
    }

    /**
     * MÉTODO PARA EL ELIMINADO LÓGICO (Baja Lógica)
     * En el e-commerce no borramos productos con DELETE FROM producto WHERE id=?, 
     * ya que eso rompería el historial de comprobantes pasados. 
     * En su lugar, modificamos el estado del flag booleano.
     */
    @Transactional
    public void eliminarProducto(String id) throws ErrorService {
        Optional<Producto> respuesta = productoRepository.findById(id);
        
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setEliminado(true); // <--- Eliminado lógico
            productoRepository.save(producto);
        } else {
            throw new ErrorService("No se pudo eliminar: El producto con ID " + id + " no existe.");
        }
    }

    /**
     * @Transactional(readOnly = true): Optimización clave. Le dice a Hibernate que este método 
     *                                  es SOLO DE LECTURA. Hibernate no abrirá una transacción con 
     *                                  bloqueos de escritura (locks), lo cual hace la consulta mucho más rápida.
     */
    @Transactional(readOnly = true)
    public List<Producto> listarProductosActivos() {
        // Traemos todos y utilizamos Streams de Java 8 para filtrar en memoria los que NO están eliminados.
        // (En un entorno de producción, lo ideal sería agregar un findAllByEliminadoFalse() en el Repository).
        List<Producto> todosLosProductos = productoRepository.findAll();
        
        return todosLosProductos.stream()
                .filter(producto -> !producto.isEliminado()) // Conserva solo donde eliminado sea FALSE
                .toList();
    }

    /**
     * Método privado de validación centralizada.
     * Si las reglas de negocio no se cumplen, arrojamos nuestra excepción personalizada.
     */
    private void validarDatos(String nombre, double precio, int stock) throws ErrorService {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorService("El nombre del producto no puede ser nulo o estar vacío.");
        }
        
        if (precio < 0) {
            throw new ErrorService("El precio del producto no puede ser negativo.");
        }
        
        if (stock < 0) {
            throw new ErrorService("El stock del producto no puede ser negativo.");
        }
    }
}
