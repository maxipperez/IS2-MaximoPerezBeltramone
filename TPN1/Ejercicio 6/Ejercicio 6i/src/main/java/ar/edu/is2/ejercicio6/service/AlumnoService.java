package ar.edu.is2.ejercicio6.service;

import ar.edu.is2.ejercicio6.dto.AlumnoDTO;
import ar.edu.is2.ejercicio6.model.Alumno;
import ar.edu.is2.ejercicio6.repository.AlumnoRepository;
import ar.edu.is2.ejercicio6.repository.AulaRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlumnoService {
  private final AlumnoRepository repo;
  private final AulaRepository aulaRepo;

  public AlumnoService(AlumnoRepository repo, AulaRepository aulaRepo) {
    this.repo = repo;
    this.aulaRepo = aulaRepo;
  }

  @Transactional(readOnly = true)
  public List<AlumnoDTO> listar() {
    return repo.findAllByOrderByApellidoAscNombreAsc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public AlumnoDTO buscar(Long id) {
    return aDTO(entidad(id));
  }

  public void guardar(AlumnoDTO dto) {
    if (vacio(dto.getNombre()) || vacio(dto.getApellido()) || vacio(dto.getDni()) || dto.getAulaId() == null)
      throw new ErrorService("Complete todos los campos.");
    String dni = dto.getDni().trim();
    boolean repetido =
        dto.getId() == null ? repo.existsByDni(dni) : repo.existsByDniAndIdNot(dni, dto.getId());
    if (repetido) throw new ErrorService("Ya existe un alumno con ese DNI.");

    Alumno a = dto.getId() == null ? new Alumno() : entidad(dto.getId());
    a.setNombre(dto.getNombre().trim());
    a.setApellido(dto.getApellido().trim());
    a.setDni(dni);
    a.setAula(aulaRepo.findById(dto.getAulaId()).orElseThrow(() -> new ErrorService("Aula inexistente.")));
    repo.save(a);
  }

  public void eliminar(Long id) {
    try {
      repo.delete(entidad(id));
      repo.flush();
    } catch (DataIntegrityViolationException e) {
      throw new ErrorService("No se puede eliminar el alumno porque tiene notas cargadas.");
    }
  }

  private Alumno entidad(Long id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Alumno inexistente."));
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }

  private AlumnoDTO aDTO(Alumno a) {
    AlumnoDTO dto = new AlumnoDTO();
    dto.setId(a.getId());
    dto.setNombre(a.getNombre());
    dto.setApellido(a.getApellido());
    dto.setDni(a.getDni());
    dto.setAulaId(a.getAula().getId());
    dto.setAula(a.getAula().getGrado() + " - " + a.getAula().getNombre());
    return dto;
  }
}
