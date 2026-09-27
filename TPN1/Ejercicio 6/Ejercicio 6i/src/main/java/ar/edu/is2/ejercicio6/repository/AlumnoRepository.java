package ar.edu.is2.ejercicio6.repository;

import ar.edu.is2.ejercicio6.model.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface AlumnoRepository
    extends JpaRepository<Alumno, Long>, RevisionRepository<Alumno, Long, Integer> {
  List<Alumno> findAllByOrderByApellidoAscNombreAsc();

  boolean existsByDni(String dni);

  boolean existsByDniAndIdNot(String dni, Long id);
}
