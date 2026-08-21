package com.ejercicio4.mvc.repositories;

import com.ejercicio4.mvc.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ¿Qué es un Repositorio?
 * Es el componente del patrón MVC encargado de interactuar directamente con la Base de Datos.
 * Aísla a la capa de Servicios (donde va la lógica de negocio) de la lógica SQL/JPA pura.
 * 
 * @Repository: Esta anotación es un estereotipo de Spring. Le indica al contenedor de Inversión de Control 
 *              (IoC) que esta interfaz es un "Bean" de persistencia. Spring la instanciará automáticamente y 
 *              además traducirá cualquier excepción SQL nativa a excepciones estándar de Spring.
 * 
 * JpaRepository<Producto, String>: Al extender de esta interfaz de Spring Data, obtenemos "gratis" un gran 
 *                                  conjunto de métodos CRUD estándar (save, findById, findAll, delete, etc.) 
 *                                  sin tener que escribir ni una sola línea de código SQL.
 *                                  Los parámetros genéricos indican: <Entidad a manejar, Tipo de dato de la Clave Primaria>.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

    /**
     * MÉTODO 1: Búsqueda ignorando los eliminados lógicamente (SOLO ACTIVOS)
     * 
     * Aquí utilizamos una característica mágica de Spring Data JPA llamada "Query Methods" (Métodos de consulta derivados).
     * Simplemente con escribir el nombre del método siguiendo cierta convención léxica, Spring genera el SQL por detrás.
     * 'findBy' -> Instrucción de búsqueda (SELECT).
     * 'Nombre' -> Filtra por el atributo nombre.
     * 'And' -> Operador lógico AND.
     * 'EliminadoFalse' -> Agrega la condición de que el atributo boolean 'eliminado' sea falso.
     * 
     * SQL Generado por detrás: SELECT * FROM producto WHERE nombre = ? AND eliminado = false;
     * 
     * @param nombre El nombre exacto del producto.
     * @return Lista de productos que coinciden y NO están borrados lógicamente.
     */
    List<Producto> findByNombreAndEliminadoFalse(String nombre);

    /**
     * MÉTODO 2: Búsqueda global sin importar si están eliminados o no
     * 
     * Para mostrar otra forma de hacer consultas, aquí utilizamos la anotación @Query con JPQL (Java Persistence Query Language).
     * A diferencia de SQL, JPQL no consulta sobre tablas (filas/columnas), sino sobre Clases Java (Entidades/Atributos).
     * Esto hace que la consulta sea agnóstica a la base de datos subyacente (funcionará igual en MySQL, Postgres, Oracle).
     * 
     * Explicación de la consulta:
     * "SELECT p"      -> Retorna la entidad u objeto completo.
     * "FROM Producto p" -> Escanea la Entidad Producto y le asigna el alias 'p'.
     * "WHERE p.nombre LIKE :nombre" -> Filtra por el atributo 'nombre' usando similitud (LIKE).
     * 
     * @Param("nombre"): Vincula el argumento String que recibe el método con el comodín ":nombre" en la consulta JPQL.
     * 
     * Al no agregar la condición del flag 'eliminado', esta consulta traerá absolutamente todo el historial.
     */
    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE :nombre")
    List<Producto> buscarTodosPorNombre(@Param("nombre") String nombre);
}
