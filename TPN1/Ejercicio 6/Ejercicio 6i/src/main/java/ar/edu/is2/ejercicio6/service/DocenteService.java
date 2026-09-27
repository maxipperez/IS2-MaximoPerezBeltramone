package ar.edu.is2.ejercicio6.service;

import ar.edu.is2.ejercicio6.dto.CambioClaveDTO;
import ar.edu.is2.ejercicio6.dto.DocenteDTO;
import ar.edu.is2.ejercicio6.model.Docente;
import ar.edu.is2.ejercicio6.repository.DocenteRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocenteService {
  private final DocenteRepository repo;
  private final PasswordEncoder encoder;
  private final MailService mailService;

  public DocenteService(DocenteRepository repo, PasswordEncoder encoder, MailService mailService) {
    this.repo = repo;
    this.encoder = encoder;
    this.mailService = mailService;
  }

  @Transactional(readOnly = true)
  public List<DocenteDTO> listar() {
    return repo.findAllByOrderByApellidoAscNombreAsc().stream().map(this::aDTO).toList();
  }

  public void registrar(DocenteDTO dto) {
    if (vacio(dto.getNombre())
        || vacio(dto.getApellido())
        || vacio(dto.getMail())
        || dto.getSexo() == null
        || dto.getFechaNacimiento() == null)
      throw new ErrorService("Complete todos los campos.");
    String mail = dto.getMail().trim().toLowerCase();
    if (repo.existsByMail(mail)) throw new ErrorService("Ya existe un docente con ese correo.");
    validarClave(dto.getClave(), dto.getClaveRepetida());

    Docente d = new Docente();
    d.setNombre(dto.getNombre().trim());
    d.setApellido(dto.getApellido().trim());
    d.setSexo(dto.getSexo());
    d.setFechaNacimiento(dto.getFechaNacimiento());
    d.setMail(mail);
    d.setClave(encoder.encode(dto.getClave()));
    repo.save(d);

    mailService.enviarBienvenida(mail, d.getNombre());
  }

  public void cambiarClave(String mail, CambioClaveDTO dto) {
    Docente d = repo.findByMail(mail).orElseThrow(() -> new ErrorService("Docente inexistente."));
    if (dto.getActual() == null || !encoder.matches(dto.getActual(), d.getClave()))
      throw new ErrorService("La contraseña actual es incorrecta.");
    validarClave(dto.getNueva(), dto.getRepetida());
    d.setClave(encoder.encode(dto.getNueva()));
    repo.save(d);
  }

  private void validarClave(String clave, String repetida) {
    if (clave == null || clave.length() < 6)
      throw new ErrorService("La contraseña debe tener al menos 6 caracteres.");
    if (!clave.equals(repetida)) throw new ErrorService("Las contraseñas no coinciden.");
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }

  private DocenteDTO aDTO(Docente d) {
    DocenteDTO dto = new DocenteDTO();
    dto.setId(d.getId());
    dto.setNombre(d.getNombre());
    dto.setApellido(d.getApellido());
    dto.setSexo(d.getSexo());
    dto.setFechaNacimiento(d.getFechaNacimiento());
    dto.setMail(d.getMail());
    return dto;
  }
}
