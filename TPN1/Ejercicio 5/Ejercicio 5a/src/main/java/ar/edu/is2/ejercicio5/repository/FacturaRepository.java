package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.Factura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaRepository extends JpaRepository<Factura, String> {
  List<Factura> findByEliminadoFalseOrderByNroFacturaDesc();

  @Query("select coalesce(max(f.nroFactura), 0) from Factura f")
  Long ultimoNumero();
}
