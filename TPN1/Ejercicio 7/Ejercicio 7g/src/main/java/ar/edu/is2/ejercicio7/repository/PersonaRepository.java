package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.Persona;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
  List<Persona> findAllByOrderByApellidoAscNombreAsc();

  boolean existsByDni(String dni);

  boolean existsByDniAndIdNot(String dni, Long id);
}
