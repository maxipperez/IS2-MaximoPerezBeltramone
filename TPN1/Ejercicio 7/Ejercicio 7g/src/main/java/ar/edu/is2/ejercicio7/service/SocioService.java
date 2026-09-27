package ar.edu.is2.ejercicio7.service;

import ar.edu.is2.ejercicio7.dto.FamiliarDTO;
import ar.edu.is2.ejercicio7.dto.FotoDTO;
import ar.edu.is2.ejercicio7.dto.SocioDTO;
import ar.edu.is2.ejercicio7.model.*;
import ar.edu.is2.ejercicio7.repository.FamiliarRepository;
import ar.edu.is2.ejercicio7.repository.PersonaRepository;
import ar.edu.is2.ejercicio7.repository.SocioRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SocioService {
  private final SocioRepository socioRepo;
  private final FamiliarRepository familiarRepo;
  private final PersonaRepository personaRepo;

  public SocioService(SocioRepository socioRepo, FamiliarRepository familiarRepo, PersonaRepository personaRepo) {
    this.socioRepo = socioRepo;
    this.familiarRepo = familiarRepo;
    this.personaRepo = personaRepo;
  }

  @Transactional(readOnly = true)
  public List<SocioDTO> listar() {
    return socioRepo.findAllByOrderByNroSocioAsc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public SocioDTO buscar(Long id) {
    return aDTO(socio(id));
  }

  public SocioDTO guardar(SocioDTO dto, byte[] imagen, String mime) {
    validarPersona(dto.getNombre(), dto.getApellido(), dto.getDni(), dto.getId());
    boolean nuevo = dto.getId() == null;
    if (nuevo && (imagen == null || imagen.length == 0))
      throw new ErrorService("La foto del rostro es obligatoria.");

    Socio s = nuevo ? new Socio() : socio(dto.getId());
    s.setNombre(dto.getNombre().trim());
    s.setApellido(dto.getApellido().trim());
    s.setDni(dto.getDni().trim());
    if (imagen != null && imagen.length > 0) s.setFoto(foto(imagen, mime));
    if (nuevo) {
      s.setNroSocio(socioRepo.ultimoNroSocio() + 1);
      GrupoFamiliar g = new GrupoFamiliar();
      g.setNombreGrupo("Familia " + s.getApellido());
      g.setTitular(s);
      s.setGrupoFamiliar(g);
    }
    return aDTO(socioRepo.save(s));
  }

  public void eliminar(Long id) {
    try {
      Socio s = socio(id);
      for (Familiar f : s.getGrupoFamiliar().getFamiliares()) f.setGrupoFamiliar(null);
      socioRepo.delete(s);
      socioRepo.flush();
    } catch (DataIntegrityViolationException e) {
      throw new ErrorService("No se puede eliminar el socio porque tiene accesos o pagos registrados.");
    }
  }

  @Transactional(readOnly = true)
  public List<FamiliarDTO> familiares(Long socioId) {
    Long grupoId = socio(socioId).getGrupoFamiliar().getId();
    return familiarRepo.findByGrupoFamiliarIdOrderByApellidoAscNombreAsc(grupoId).stream()
        .map(this::aDTO)
        .toList();
  }

  public void agregarFamiliar(Long socioId, FamiliarDTO dto, byte[] imagen, String mime) {
    validarPersona(dto.getNombre(), dto.getApellido(), dto.getDni(), null);
    if (dto.getParentesco() == null || dto.getParentesco().isBlank())
      throw new ErrorService("El parentesco es obligatorio.");
    if (imagen == null || imagen.length == 0) throw new ErrorService("La foto del rostro es obligatoria.");

    Familiar f = new Familiar();
    f.setNombre(dto.getNombre().trim());
    f.setApellido(dto.getApellido().trim());
    f.setDni(dto.getDni().trim());
    f.setParentesco(dto.getParentesco().trim());
    f.setFoto(foto(imagen, mime));
    f.setGrupoFamiliar(socio(socioId).getGrupoFamiliar());
    familiarRepo.save(f);
  }

  public void quitarFamiliar(Long familiarId) {
    Familiar f = familiarRepo.findById(familiarId).orElseThrow(() -> new ErrorService("Familiar inexistente."));
    f.setGrupoFamiliar(null);
    familiarRepo.save(f);
  }

  @Transactional(readOnly = true)
  public FotoDTO foto(Long personaId) {
    FotoRostro f = personaRepo.findById(personaId).map(Persona::getFoto).orElse(null);
    return f == null ? null : new FotoDTO(f.getImagen(), f.getMime());
  }

  private void validarPersona(String nombre, String apellido, String dni, Long id) {
    if (vacio(nombre) || vacio(apellido) || vacio(dni)) throw new ErrorService("Complete todos los campos.");
    boolean repetido = id == null ? personaRepo.existsByDni(dni.trim()) : personaRepo.existsByDniAndIdNot(dni.trim(), id);
    if (repetido) throw new ErrorService("Ya existe una persona con ese DNI.");
  }

  private FotoRostro foto(byte[] imagen, String mime) {
    if (!"image/jpeg".equals(mime) && !"image/png".equals(mime))
      throw new ErrorService("La foto debe ser JPG o PNG.");
    FotoRostro f = new FotoRostro();
    f.setImagen(imagen);
    f.setMime(mime);
    return f;
  }

  private Socio socio(Long id) {
    return socioRepo.findById(id).orElseThrow(() -> new ErrorService("Socio inexistente."));
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }

  private SocioDTO aDTO(Socio s) {
    SocioDTO dto = new SocioDTO();
    dto.setId(s.getId());
    dto.setNombre(s.getNombre());
    dto.setApellido(s.getApellido());
    dto.setDni(s.getDni());
    dto.setNroSocio(s.getNroSocio());
    dto.setFechaAlta(s.getFechaAlta());
    dto.setGrupoId(s.getGrupoFamiliar().getId());
    dto.setGrupo(s.getGrupoFamiliar().getNombreGrupo());
    return dto;
  }

  private FamiliarDTO aDTO(Familiar f) {
    FamiliarDTO dto = new FamiliarDTO();
    dto.setId(f.getId());
    dto.setNombre(f.getNombre());
    dto.setApellido(f.getApellido());
    dto.setDni(f.getDni());
    dto.setParentesco(f.getParentesco());
    return dto;
  }
}
