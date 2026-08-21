package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio de ítems. */
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, String> {}
