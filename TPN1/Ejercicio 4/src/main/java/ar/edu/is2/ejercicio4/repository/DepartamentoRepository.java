package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.Departamento;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acceso CRUD de departamentos. */
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
  List<Departamento> findByEliminadoFalse();
}
