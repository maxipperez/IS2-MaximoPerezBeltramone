package ar.edu.is2.ejercicio6.repository;

import ar.edu.is2.ejercicio6.model.Docente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface DocenteRepository
    extends JpaRepository<Docente, Long>, RevisionRepository<Docente, Long, Integer> {
  Optional<Docente> findByMail(String mail);

  boolean existsByMail(String mail);

  List<Docente> findAllByOrderByApellidoAscNombreAsc();
}
