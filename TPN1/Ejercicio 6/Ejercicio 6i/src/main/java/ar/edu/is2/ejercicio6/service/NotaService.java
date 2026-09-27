package ar.edu.is2.ejercicio6.service;

import ar.edu.is2.ejercicio6.dto.NotaDTO;
import ar.edu.is2.ejercicio6.dto.RevisionDTO;
import ar.edu.is2.ejercicio6.model.Nota;
import ar.edu.is2.ejercicio6.repository.AlumnoRepository;
import ar.edu.is2.ejercicio6.repository.MateriaRepository;
import ar.edu.is2.ejercicio6.repository.NotaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotaService {
  private final NotaRepository repo;
  private final AlumnoRepository alumnoRepo;
  private final MateriaRepository materiaRepo;

  public NotaService(NotaRepository repo, AlumnoRepository alumnoRepo, MateriaRepository materiaRepo) {
    this.repo = repo;
    this.alumnoRepo = alumnoRepo;
    this.materiaRepo = materiaRepo;
  }

  @Transactional(readOnly = true)
  public List<NotaDTO> listar(Long alumnoId) {
    List<Nota> notas =
        alumnoId == null ? repo.findAllByOrderByFechaDesc() : repo.findByAlumnoIdOrderByFechaDesc(alumnoId);
    return notas.stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public NotaDTO buscar(Long id) {
    return aDTO(entidad(id));
  }

  public void guardar(NotaDTO dto) {
    if (dto.getAlumnoId() == null || dto.getMateriaId() == null || dto.getValor() == null)
      throw new ErrorService("Complete todos los campos.");
    if (dto.getValor() < 1 || dto.getValor() > 10) throw new ErrorService("La nota debe estar entre 1 y 10.");
    Nota n = dto.getId() == null ? new Nota() : entidad(dto.getId());
    n.setValor(dto.getValor());
    n.setFecha(dto.getFecha() == null ? LocalDate.now() : dto.getFecha());
    n.setAlumno(alumnoRepo.findById(dto.getAlumnoId()).orElseThrow(() -> new ErrorService("Alumno inexistente.")));
    n.setMateria(
        materiaRepo.findById(dto.getMateriaId()).orElseThrow(() -> new ErrorService("Materia inexistente.")));
    repo.save(n);
  }

  public void eliminar(Long id) {
    repo.delete(entidad(id));
  }

  @Transactional(readOnly = true)
  public List<RevisionDTO> historial(Long id) {
    return repo.findRevisions(id).getContent().stream()
        .map(
            r ->
                new RevisionDTO(
                    r.getRequiredRevisionNumber(),
                    LocalDateTime.ofInstant(r.getRequiredRevisionInstant(), ZoneId.systemDefault()),
                    r.getMetadata().getRevisionType().name(),
                    aDTO(r.getEntity())))
        .toList();
  }

  private Nota entidad(Long id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Nota inexistente."));
  }

  private NotaDTO aDTO(Nota n) {
    NotaDTO dto = new NotaDTO();
    dto.setId(n.getId());
    dto.setValor(n.getValor());
    dto.setFecha(n.getFecha());
    dto.setAlumnoId(n.getAlumno().getId());
    dto.setAlumno(n.getAlumno().getApellido() + ", " + n.getAlumno().getNombre());
    dto.setMateriaId(n.getMateria().getId());
    dto.setMateria(n.getMateria().getNombre());
    return dto;
  }
}
