package ar.edu.is2.ejercicio6.repository;

import ar.edu.is2.ejercicio6.model.Nota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface NotaRepository
    extends JpaRepository<Nota, Long>, RevisionRepository<Nota, Long, Integer> {
  List<Nota> findAllByOrderByFechaDesc();

  List<Nota> findByAlumnoIdOrderByFechaDesc(Long alumnoId);
}
