package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, String> {
  List<Proveedor> findAllByOrderByRazonSocial();

  List<Proveedor> findByActivoTrueOrderByRazonSocial();

  boolean existsByCuit(String cuit);

  boolean existsByCuitAndIdNot(String cuit, String id);
}
