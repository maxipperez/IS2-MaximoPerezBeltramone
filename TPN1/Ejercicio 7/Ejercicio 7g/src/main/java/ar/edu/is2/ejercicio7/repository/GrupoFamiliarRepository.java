package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.GrupoFamiliar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface GrupoFamiliarRepository
    extends JpaRepository<GrupoFamiliar, Long>, RevisionRepository<GrupoFamiliar, Long, Integer> {
  List<GrupoFamiliar> findAllByOrderByNombreGrupoAsc();
}
