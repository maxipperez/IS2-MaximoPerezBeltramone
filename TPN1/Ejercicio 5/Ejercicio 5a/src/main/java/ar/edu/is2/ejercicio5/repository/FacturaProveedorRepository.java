package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.FacturaProveedor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaProveedorRepository extends JpaRepository<FacturaProveedor, String> {
  Optional<FacturaProveedor> findByOrdenCompraId(String ordenCompraId);
}
