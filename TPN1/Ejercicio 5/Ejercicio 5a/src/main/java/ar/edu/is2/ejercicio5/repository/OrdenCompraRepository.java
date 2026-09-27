package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.OrdenCompra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, String> {
  List<OrdenCompra> findAllByOrderByNumeroDesc();

  @Query("select coalesce(max(o.numero), 9000) from OrdenCompra o")
  Long ultimoNumero();
}
