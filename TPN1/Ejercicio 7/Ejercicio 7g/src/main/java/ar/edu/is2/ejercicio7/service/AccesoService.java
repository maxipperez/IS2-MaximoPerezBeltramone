package ar.edu.is2.ejercicio7.service;

import ar.edu.is2.ejercicio7.dto.AccesoDTO;
import ar.edu.is2.ejercicio7.dto.PersonaDTO;
import ar.edu.is2.ejercicio7.model.Persona;
import ar.edu.is2.ejercicio7.model.RegistroAcceso;
import ar.edu.is2.ejercicio7.model.Socio;
import ar.edu.is2.ejercicio7.repository.PersonaRepository;
import ar.edu.is2.ejercicio7.repository.RegistroAccesoRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccesoService {
  private final RegistroAccesoRepository repo;
  private final PersonaRepository personaRepo;

  public AccesoService(RegistroAccesoRepository repo, PersonaRepository personaRepo) {
    this.repo = repo;
    this.personaRepo = personaRepo;
  }

  public void registrarEntrada(Long personaId) {
    Persona p = persona(personaId);
    if (repo.findFirstByPersonaIdAndFechaHoraSalidaIsNull(personaId).isPresent())
      throw new ErrorService(p.getNombreCompleto() + " ya se encuentra dentro del club.");
    RegistroAcceso r = new RegistroAcceso();
    r.setPersona(p);
    r.setFechaHoraEntrada(LocalDateTime.now());
    repo.save(r);
  }

  public void registrarSalida(Long personaId) {
    Persona p = persona(personaId);
    RegistroAcceso r =
        repo.findFirstByPersonaIdAndFechaHoraSalidaIsNull(personaId)
            .orElseThrow(() -> new ErrorService(p.getNombreCompleto() + " no tiene un ingreso abierto."));
    r.setFechaHoraSalida(LocalDateTime.now());
    repo.save(r);
  }

  @Transactional(readOnly = true)
  public List<AccesoDTO> presentes() {
    return repo.findByFechaHoraSalidaIsNullOrderByFechaHoraEntradaDesc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<AccesoDTO> ultimos() {
    return repo.findTop20ByOrderByFechaHoraEntradaDesc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<PersonaDTO> personas() {
    return personaRepo.findAllByOrderByApellidoAscNombreAsc().stream()
        .map(p -> new PersonaDTO(p.getId(), p.getNombreCompleto(), p.getDni(), tipo(p)))
        .toList();
  }

  private Persona persona(Long id) {
    if (id == null) throw new ErrorService("Seleccione una persona.");
    return personaRepo.findById(id).orElseThrow(() -> new ErrorService("Persona inexistente."));
  }

  private String tipo(Persona p) {
    return p instanceof Socio ? "Socio" : "Familiar";
  }

  private AccesoDTO aDTO(RegistroAcceso r) {
    Long minutos =
        r.getFechaHoraSalida() == null
            ? null
            : Duration.between(r.getFechaHoraEntrada(), r.getFechaHoraSalida()).toMinutes();
    return new AccesoDTO(
        r.getPersona().getId(),
        r.getPersona().getNombreCompleto(),
        tipo(r.getPersona()),
        r.getFechaHoraEntrada(),
        r.getFechaHoraSalida(),
        minutos);
  }
}
