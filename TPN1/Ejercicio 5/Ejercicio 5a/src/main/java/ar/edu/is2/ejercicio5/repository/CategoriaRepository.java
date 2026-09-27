package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, String> {
  List<Categoria> findByEliminadoFalseOrderByNombre();

  boolean existsByNombreIgnoreCaseAndEliminadoFalse(String nombre);
}
