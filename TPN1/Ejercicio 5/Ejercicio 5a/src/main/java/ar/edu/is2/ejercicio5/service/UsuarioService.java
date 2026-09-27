package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.LoginDTO;
import ar.edu.is2.ejercicio5.dto.SesionDTO;
import ar.edu.is2.ejercicio5.dto.UsuarioDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.Rol;
import ar.edu.is2.ejercicio5.model.Usuario;
import ar.edu.is2.ejercicio5.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {
  private final UsuarioRepository repo;
  private final PasswordEncoder encoder;

  public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  @Transactional(readOnly = true)
  public SesionDTO iniciarSesion(LoginDTO dto) {
    if (vacio(dto.getMail()) || vacio(dto.getClave()))
      throw new ErrorService("Ingrese su correo y su clave.");
    Usuario u =
        repo.findByMailIgnoreCase(dto.getMail().trim())
            .filter(x -> encoder.matches(dto.getClave(), x.getClave()))
            .orElseThrow(() -> new ErrorService("Correo o clave incorrectos."));
    if (!u.isActivo()) throw new ErrorService("El usuario se encuentra dado de baja.");
    return new SesionDTO(
        u.getId(), u.getNombre() + " " + u.getApellido(), u.getMail(), u.getRol().name());
  }

  public UsuarioDTO registrarUsuario(UsuarioDTO dto) {
    dto.setRol(Rol.VENDEDOR.name());
    return crearUsuario(dto);
  }

  public UsuarioDTO crearUsuario(UsuarioDTO dto) {
    validarDatos(dto);
    validarClave(dto);
    if (repo.existsByMailIgnoreCase(dto.getMail().trim()))
      throw new ErrorService("Ya existe un usuario con ese correo.");
    Usuario u = new Usuario();
    copiar(u, dto);
    u.setClave(encoder.encode(dto.getClave()));
    return aDTO(repo.save(u));
  }

  public UsuarioDTO modificarUsuario(String id, UsuarioDTO dto, String idSesion) {
    validarDatos(dto);
    if (repo.existsByMailIgnoreCaseAndIdNot(dto.getMail().trim(), id))
      throw new ErrorService("Ya existe un usuario con ese correo.");
    Usuario u = entidad(id);
    if (id.equals(idSesion) && !Rol.valueOf(dto.getRol()).equals(u.getRol()))
      throw new ErrorService("No puede cambiar su propio rol.");
    copiar(u, dto);
    if (!vacio(dto.getClave())) {
      validarClave(dto);
      u.setClave(encoder.encode(dto.getClave()));
    }
    return aDTO(repo.save(u));
  }

  public void eliminarUsuario(String id, String idSesion) {
    if (id.equals(idSesion)) throw new ErrorService("No puede darse de baja a sí mismo.");
    Usuario u = entidad(id);
    u.setBaja(LocalDate.now());
    repo.save(u);
  }

  @Transactional(readOnly = true)
  public List<UsuarioDTO> listarUsuarios() {
    return repo.findAllByOrderByApellidoAscNombreAsc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public UsuarioDTO buscarPorId(String id) {
    return aDTO(entidad(id));
  }

  public void crearAdministradorInicial(String mail, String clave) {
    if (repo.count() > 0) return;
    Usuario u = new Usuario();
    u.setNombre("Administrador");
    u.setApellido("TechSales");
    u.setMail(mail);
    u.setClave(encoder.encode(clave));
    u.setRol(Rol.ADMINISTRADOR);
    repo.save(u);
  }

  private Usuario entidad(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Usuario inexistente."));
  }

  private void copiar(Usuario u, UsuarioDTO dto) {
    u.setNombre(dto.getNombre().trim());
    u.setApellido(dto.getApellido().trim());
    u.setMail(dto.getMail().trim().toLowerCase());
    u.setRol(Rol.valueOf(dto.getRol()));
  }

  private void validarDatos(UsuarioDTO dto) {
    if (vacio(dto.getNombre()) || vacio(dto.getApellido()) || vacio(dto.getMail()))
      throw new ErrorService("Complete los campos obligatorios");
    if (!dto.getMail().trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"))
      throw new ErrorService("El correo no tiene un formato válido.");
    try {
      Rol.valueOf(dto.getRol());
    } catch (IllegalArgumentException | NullPointerException e) {
      throw new ErrorService("Rol inválido.");
    }
  }

  private void validarClave(UsuarioDTO dto) {
    if (vacio(dto.getClave()) || dto.getClave().length() < 6)
      throw new ErrorService("La clave debe tener al menos 6 caracteres.");
    if (!dto.getClave().equals(dto.getClaveRepetida()))
      throw new ErrorService("Las claves no coinciden.");
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }

  private UsuarioDTO aDTO(Usuario u) {
    UsuarioDTO dto = new UsuarioDTO();
    dto.setId(u.getId());
    dto.setNombre(u.getNombre());
    dto.setApellido(u.getApellido());
    dto.setMail(u.getMail());
    dto.setRol(u.getRol().name());
    dto.setActivo(u.isActivo());
    dto.setAlta(u.getAlta());
    return dto;
  }
}
