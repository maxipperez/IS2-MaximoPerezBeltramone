package ar.edu.is2.ejercicio6.repository;

import ar.edu.is2.ejercicio6.model.Aula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface AulaRepository
    extends JpaRepository<Aula, Long>, RevisionRepository<Aula, Long, Integer> {
  List<Aula> findAllByOrderByGradoAscNombreAsc();
}
