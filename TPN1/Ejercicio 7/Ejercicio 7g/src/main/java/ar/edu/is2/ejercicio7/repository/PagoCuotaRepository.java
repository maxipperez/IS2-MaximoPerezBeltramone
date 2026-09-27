package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.PagoCuota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface PagoCuotaRepository
    extends JpaRepository<PagoCuota, Long>, RevisionRepository<PagoCuota, Long, Integer> {
  List<PagoCuota> findAllByOrderByFechaPagoDescIdDesc();

  List<PagoCuota> findByGrupoFamiliarIdOrderByPeriodoDesc(Long grupoId);

  boolean existsByGrupoFamiliarIdAndPeriodo(Long grupoId, String periodo);
}
