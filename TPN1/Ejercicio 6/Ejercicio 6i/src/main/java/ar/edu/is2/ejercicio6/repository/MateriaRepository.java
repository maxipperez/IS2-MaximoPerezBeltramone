package ar.edu.is2.ejercicio6.repository;

import ar.edu.is2.ejercicio6.model.Materia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface MateriaRepository
    extends JpaRepository<Materia, Long>, RevisionRepository<Materia, Long, Integer> {
  List<Materia> findAllByOrderByNombreAsc();
}
