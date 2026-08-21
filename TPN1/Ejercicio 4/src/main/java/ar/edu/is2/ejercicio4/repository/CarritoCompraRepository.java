package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.CarritoCompra;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio de carritos. */
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, String> {}
