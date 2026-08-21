package com.ejercicio4.mvc.repositories;

import com.ejercicio4.mvc.models.Cliente;
import com.ejercicio4.mvc.models.enums.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio encargado del acceso a datos para la entidad Cliente.
 * 
 * @Repository: Registra este componente en el ApplicationContext de Spring, permitiendo inyectarlo 
 *              fácilmente en la capa de Servicios mediante @Autowired.
 * JpaRepository<Cliente, String>: Otorga toda la funcionalidad CRUD sobre la tabla 'cliente' y su tabla padre 'usuario'.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    /**
     * MÉTODO 1: Búsqueda ignorando los eliminados lógicamente (SOLO ACTIVOS)
     * 
     * Dado que Cliente hereda de Usuario, su "eliminado lógico" se maneja con el atributo 'estado' (un Enum), 
     * y no con un boolean estático como en Producto.
     * 
     * Utilizamos "Query Methods" de Spring Data:
     * 'findBy' -> Busca.
     * 'Nombre' -> Filtra por el atributo 'nombre' que el Cliente heredó de la clase Usuario.
     * 'And' -> Operador lógico AND.
     * 'EstadoNot' -> Filtra donde el 'estado' NO SEA IGUAL al valor pasado por parámetro.
     * 
     * Magia de Spring Data: Al ser Cliente una entidad hija (InheritanceType.JOINED), Spring generará 
     * automáticamente el SQL haciendo un INNER JOIN entre la tabla 'cliente' y la tabla 'usuario' para evaluar los campos.
     */
    List<Cliente> findByNombreAndEstadoNot(String nombre, EstadoUsuario estado);

    /**
     * MÉTODO 2: Búsqueda global sin importar si están eliminados o no
     * 
     * Utilizamos una consulta JPQL personalizada con la anotación @Query.
     * JPQL interactúa con los objetos Java (Entidades) abstrayéndonos del diseño relacional de las tablas.
     * 
     * SELECT c FROM Cliente c WHERE c.nombre = :nombre
     * - c: Es el alias del objeto Cliente.
     * - :nombre: Es el parámetro dinámico que inyectamos en tiempo de ejecución.
     * 
     * Al no filtrar por el estado, esta consulta traerá a todos los clientes (activos, bloqueados, eliminados).
     */
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    List<Cliente> buscarGlobalPorNombre(@Param("nombre") String nombre);
}
