package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.FacturaCliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaClienteRepository extends JpaRepository<FacturaCliente, String> {
  List<FacturaCliente> findByEliminadoFalseOrderByNroFacturaDesc();
}
