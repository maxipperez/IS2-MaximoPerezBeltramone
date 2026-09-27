package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.RegistroAcceso;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface RegistroAccesoRepository
    extends JpaRepository<RegistroAcceso, Long>, RevisionRepository<RegistroAcceso, Long, Integer> {
  Optional<RegistroAcceso> findFirstByPersonaIdAndFechaHoraSalidaIsNull(Long personaId);

  List<RegistroAcceso> findByFechaHoraSalidaIsNullOrderByFechaHoraEntradaDesc();

  List<RegistroAcceso> findTop20ByOrderByFechaHoraEntradaDesc();

  List<RegistroAcceso> findByPersonaIdOrderByFechaHoraEntradaDesc(Long personaId);
}
