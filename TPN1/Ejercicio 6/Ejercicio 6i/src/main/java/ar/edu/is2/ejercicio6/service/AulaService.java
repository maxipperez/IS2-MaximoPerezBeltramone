package ar.edu.is2.ejercicio6.service;

import ar.edu.is2.ejercicio6.dto.AulaDTO;
import ar.edu.is2.ejercicio6.model.Aula;
import ar.edu.is2.ejercicio6.repository.AulaRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AulaService {
  private final AulaRepository repo;

  public AulaService(AulaRepository repo) {
    this.repo = repo;
  }

  @Transactional(readOnly = true)
  public List<AulaDTO> listar() {
    return repo.findAllByOrderByGradoAscNombreAsc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public AulaDTO buscar(Long id) {
    return aDTO(entidad(id));
  }

  public void guardar(AulaDTO dto) {
    if (dto.getNombre() == null || dto.getNombre().isBlank() || dto.getGrado() == null || dto.getGrado().isBlank())
      throw new ErrorService("Complete todos los campos.");
    Aula a = dto.getId() == null ? new Aula() : entidad(dto.getId());
    a.setNombre(dto.getNombre().trim());
    a.setGrado(dto.getGrado().trim());
    repo.save(a);
  }

  public void eliminar(Long id) {
    try {
      repo.delete(entidad(id));
      repo.flush();
    } catch (DataIntegrityViolationException e) {
      throw new ErrorService("No se puede eliminar el aula porque tiene alumnos o materias asociados.");
    }
  }

  private Aula entidad(Long id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Aula inexistente."));
  }

  private AulaDTO aDTO(Aula a) {
    AulaDTO dto = new AulaDTO();
    dto.setId(a.getId());
    dto.setNombre(a.getNombre());
    dto.setGrado(a.getGrado());
    return dto;
  }
}
