package ar.edu.is2.ejercicio6.service;

import ar.edu.is2.ejercicio6.dto.MateriaDTO;
import ar.edu.is2.ejercicio6.model.Materia;
import ar.edu.is2.ejercicio6.repository.AulaRepository;
import ar.edu.is2.ejercicio6.repository.DocenteRepository;
import ar.edu.is2.ejercicio6.repository.MateriaRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MateriaService {
  private final MateriaRepository repo;
  private final AulaRepository aulaRepo;
  private final DocenteRepository docenteRepo;

  public MateriaService(MateriaRepository repo, AulaRepository aulaRepo, DocenteRepository docenteRepo) {
    this.repo = repo;
    this.aulaRepo = aulaRepo;
    this.docenteRepo = docenteRepo;
  }

  @Transactional(readOnly = true)
  public List<MateriaDTO> listar() {
    return repo.findAllByOrderByNombreAsc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public MateriaDTO buscar(Long id) {
    return aDTO(entidad(id));
  }

  public void guardar(MateriaDTO dto) {
    if (dto.getNombre() == null || dto.getNombre().isBlank() || dto.getAulaId() == null || dto.getDocenteId() == null)
      throw new ErrorService("Complete todos los campos.");
    Materia m = dto.getId() == null ? new Materia() : entidad(dto.getId());
    m.setNombre(dto.getNombre().trim());
    m.setAula(aulaRepo.findById(dto.getAulaId()).orElseThrow(() -> new ErrorService("Aula inexistente.")));
    m.setDocente(
        docenteRepo.findById(dto.getDocenteId()).orElseThrow(() -> new ErrorService("Docente inexistente.")));
    repo.save(m);
  }

  public void eliminar(Long id) {
    try {
      repo.delete(entidad(id));
      repo.flush();
    } catch (DataIntegrityViolationException e) {
      throw new ErrorService("No se puede eliminar la materia porque tiene notas cargadas.");
    }
  }

  private Materia entidad(Long id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Materia inexistente."));
  }

  private MateriaDTO aDTO(Materia m) {
    MateriaDTO dto = new MateriaDTO();
    dto.setId(m.getId());
    dto.setNombre(m.getNombre());
    dto.setAulaId(m.getAula().getId());
    dto.setAula(m.getAula().getGrado() + " - " + m.getAula().getNombre());
    dto.setDocenteId(m.getDocente().getId());
    dto.setDocente(m.getDocente().getNombre() + " " + m.getDocente().getApellido());
    return dto;
  }
}
