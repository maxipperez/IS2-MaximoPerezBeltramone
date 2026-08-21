package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio de direcciones. */
public interface DireccionRepository extends JpaRepository<Direccion, String> {}
