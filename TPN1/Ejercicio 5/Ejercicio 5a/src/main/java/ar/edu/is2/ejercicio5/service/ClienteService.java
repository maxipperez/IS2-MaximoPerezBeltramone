package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.ClienteDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.Cliente;
import ar.edu.is2.ejercicio5.repository.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteService {
  private final ClienteRepository repo;

  public ClienteService(ClienteRepository repo) {
    this.repo = repo;
  }

  @Transactional(readOnly = true)
  public List<ClienteDTO> listarClientes() {
    return repo.findByEliminadoFalseOrderByApellidoAscNombreAsc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public ClienteDTO buscarPorId(String id) {
    return aDTO(entidad(id));
  }

  public ClienteDTO crearCliente(ClienteDTO dto) {
    validar(dto);
    if (repo.existsByDni(dto.getDni().trim()))
      throw new ErrorService("Ya existe un cliente con ese DNI");
    Cliente c = new Cliente();
    copiar(c, dto);
    return aDTO(repo.save(c));
  }

  public ClienteDTO modificarCliente(String id, ClienteDTO dto) {
    validar(dto);
    if (repo.existsByDniAndIdNot(dto.getDni().trim(), id))
      throw new ErrorService("Ya existe un cliente con ese DNI");
    Cliente c = entidad(id);
    copiar(c, dto);
    return aDTO(repo.save(c));
  }

  public void eliminarCliente(String id) {
    Cliente c = entidad(id);
    c.setEliminado(true);
    repo.save(c);
  }

  private Cliente entidad(String id) {
    return repo.findById(id)
        .filter(c -> !c.isEliminado())
        .orElseThrow(() -> new ErrorService("Cliente inexistente."));
  }

  private void copiar(Cliente c, ClienteDTO dto) {
    c.setNombre(dto.getNombre().trim());
    c.setApellido(dto.getApellido().trim());
    c.setDni(dto.getDni().trim());
    c.setTelefono(dto.getTelefono());
  }

  private void validar(ClienteDTO dto) {
    if (vacio(dto.getNombre()) || vacio(dto.getApellido()) || vacio(dto.getDni()))
      throw new ErrorService("Complete los campos obligatorios");
    if (!dto.getDni().trim().matches("\\d{7,8}"))
      throw new ErrorService("El DNI debe tener 7 u 8 dígitos.");
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }

  private ClienteDTO aDTO(Cliente c) {
    ClienteDTO dto = new ClienteDTO();
    dto.setId(c.getId());
    dto.setNombre(c.getNombre());
    dto.setApellido(c.getApellido());
    dto.setDni(c.getDni());
    dto.setTelefono(c.getTelefono());
    return dto;
  }
}
