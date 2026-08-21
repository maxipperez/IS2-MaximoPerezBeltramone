package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio de clientes. Spring Data traduce los nombres de método a consultas. */
public interface ClienteRepository extends JpaRepository<Cliente, String> {
  /** Solo clientes no eliminados. */
  List<Cliente> findByNombreContainingIgnoreCaseAndEstadoNot(String nombre, EstadoUsuario estado);

  /** Incluye todas las altas y bajas. */
  List<Cliente> findByNombreContainingIgnoreCase(String nombre);

  List<Cliente> findByEstadoNot(EstadoUsuario estado);
}
