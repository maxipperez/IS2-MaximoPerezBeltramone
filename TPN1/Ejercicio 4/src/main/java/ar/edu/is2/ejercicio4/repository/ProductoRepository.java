package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.Producto;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Repository es inferido por JpaRepository; expone consultas CRUD y las búsquedas solicitadas.
 */
public interface ProductoRepository extends JpaRepository<Producto, String> {
  /** Busca coincidencias activas, excluyendo bajas lógicas. */
  List<Producto> findByNombreContainingIgnoreCaseAndEliminadoFalse(String nombre);

  /** Busca por nombre incluso si el producto fue eliminado. */
  List<Producto> findByNombreContainingIgnoreCase(String nombre);

  List<Producto> findByEliminadoFalse();
}
