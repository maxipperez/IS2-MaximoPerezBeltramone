package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
  List<Cliente> findByEliminadoFalseOrderByApellidoAscNombreAsc();

  boolean existsByDni(String dni);

  boolean existsByDniAndIdNot(String dni, String id);
}
