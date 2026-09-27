package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.DetalleOrdenCompra;
import ar.edu.is2.ejercicio5.model.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, String> {
  boolean existsByProductoIdAndOrdenCompraEstado(String productoId, EstadoOrden estado);
}
