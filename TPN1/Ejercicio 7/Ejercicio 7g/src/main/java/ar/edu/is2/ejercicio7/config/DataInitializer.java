package ar.edu.is2.ejercicio7.config;

import ar.edu.is2.ejercicio7.dto.FamiliarDTO;
import ar.edu.is2.ejercicio7.dto.SocioDTO;
import ar.edu.is2.ejercicio7.model.Usuario;
import ar.edu.is2.ejercicio7.repository.UsuarioRepository;
import ar.edu.is2.ejercicio7.service.SocioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
  private final UsuarioRepository usuarioRepo;
  private final SocioService socioService;
  private final PasswordEncoder encoder;

  public DataInitializer(UsuarioRepository usuarioRepo, SocioService socioService, PasswordEncoder encoder) {
    this.usuarioRepo = usuarioRepo;
    this.socioService = socioService;
    this.encoder = encoder;
  }

  @Override
  public void run(String... args) throws Exception {
    if (usuarioRepo.count() > 0) return;

    Usuario u = new Usuario();
    u.setMail("recepcion@club.com");
    u.setClave(encoder.encode("club123"));
    usuarioRepo.save(u);

    byte[] foto = new ClassPathResource("static/assets/images/avatar.png").getContentAsByteArray();

    SocioDTO s = new SocioDTO();
    s.setNombre("Carlos");
    s.setApellido("Fernández");
    s.setDni("25111222");
    Long socioId = socioService.guardar(s, foto, "image/png").getId();

    FamiliarDTO f = new FamiliarDTO();
    f.setNombre("Lucía");
    f.setApellido("Fernández");
    f.setDni("48333444");
    f.setParentesco("Hija");
    socioService.agregarFamiliar(socioId, f, foto, "image/png");
  }
}
