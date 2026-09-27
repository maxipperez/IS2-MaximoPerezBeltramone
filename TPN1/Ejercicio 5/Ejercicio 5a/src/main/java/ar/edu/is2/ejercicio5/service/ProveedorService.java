package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.ProveedorDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.Proveedor;
import ar.edu.is2.ejercicio5.repository.ProveedorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProveedorService {
  private final ProveedorRepository repo;

  public ProveedorService(ProveedorRepository repo) {
    this.repo = repo;
  }

  @Transactional(readOnly = true)
  public List<ProveedorDTO> listarProveedores() {
    return repo.findAllByOrderByRazonSocial().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<ProveedorDTO> listarActivos() {
    return repo.findByActivoTrueOrderByRazonSocial().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public ProveedorDTO buscarProveedorID(String id) {
    return aDTO(entidad(id));
  }

  public ProveedorDTO crearProveedor(ProveedorDTO dto) {
    validar(dto);
    if (repo.existsByCuit(dto.getCuit().trim()))
      throw new ErrorService("Ya existe un proveedor con ese CUIT");
    Proveedor p = new Proveedor();
    copiar(p, dto);
    p.setActivo(true);
    return aDTO(repo.save(p));
  }

  public ProveedorDTO modificarProveedor(String id, ProveedorDTO dto) {
    validar(dto);
    if (repo.existsByCuitAndIdNot(dto.getCuit().trim(), id))
      throw new ErrorService("Ya existe un proveedor con ese CUIT");
    Proveedor p = entidad(id);
    copiar(p, dto);
    p.setActivo(dto.isActivo());
    return aDTO(repo.save(p));
  }

  public void eliminarProveedor(String id) {
    Proveedor p = entidad(id);
    p.setActivo(false);
    repo.save(p);
  }

  private Proveedor entidad(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Proveedor inexistente."));
  }

  private void copiar(Proveedor p, ProveedorDTO dto) {
    p.setCuit(dto.getCuit().trim());
    p.setRazonSocial(dto.getRazonSocial().trim());
    p.setTelefono(dto.getTelefono());
  }

  private void validar(ProveedorDTO dto) {
    if (vacio(dto.getCuit()) || vacio(dto.getRazonSocial()))
      throw new ErrorService("Complete los campos obligatorios");
    if (!dto.getCuit().trim().matches("\\d{2}-?\\d{8}-?\\d"))
      throw new ErrorService("El CUIT debe tener 11 dígitos (ej: 30-12345678-9).");
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }

  private ProveedorDTO aDTO(Proveedor p) {
    ProveedorDTO dto = new ProveedorDTO();
    dto.setId(p.getId());
    dto.setCuit(p.getCuit());
    dto.setRazonSocial(p.getRazonSocial());
    dto.setTelefono(p.getTelefono());
    dto.setActivo(p.isActivo());
    return dto;
  }
}
