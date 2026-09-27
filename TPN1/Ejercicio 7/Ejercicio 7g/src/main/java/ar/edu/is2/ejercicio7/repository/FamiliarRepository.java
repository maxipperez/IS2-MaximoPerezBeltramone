package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.Familiar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface FamiliarRepository
    extends JpaRepository<Familiar, Long>, RevisionRepository<Familiar, Long, Integer> {
  List<Familiar> findByGrupoFamiliarIdOrderByApellidoAscNombreAsc(Long grupoId);
}
