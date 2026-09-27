package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.Socio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

public interface SocioRepository
    extends JpaRepository<Socio, Long>, RevisionRepository<Socio, Long, Integer> {
  List<Socio> findAllByOrderByNroSocioAsc();

  @Query("select coalesce(max(s.nroSocio), 1000) from Socio s")
  Integer ultimoNroSocio();
}
