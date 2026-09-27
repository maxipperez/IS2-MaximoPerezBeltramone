package ar.edu.is2.ejercicio6.config;

import ar.edu.is2.ejercicio6.model.*;
import ar.edu.is2.ejercicio6.repository.*;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
  private final DocenteRepository docenteRepo;
  private final AulaRepository aulaRepo;
  private final AlumnoRepository alumnoRepo;
  private final MateriaRepository materiaRepo;
  private final PasswordEncoder encoder;

  public DataInitializer(
      DocenteRepository docenteRepo,
      AulaRepository aulaRepo,
      AlumnoRepository alumnoRepo,
      MateriaRepository materiaRepo,
      PasswordEncoder encoder) {
    this.docenteRepo = docenteRepo;
    this.aulaRepo = aulaRepo;
    this.alumnoRepo = alumnoRepo;
    this.materiaRepo = materiaRepo;
    this.encoder = encoder;
  }

  @Override
  public void run(String... args) {
    if (docenteRepo.count() > 0) return;

    Docente d = new Docente();
    d.setNombre("Laura");
    d.setApellido("Gómez");
    d.setSexo(Sexo.FEMENINO);
    d.setFechaNacimiento(LocalDate.of(1985, 4, 12));
    d.setMail("docente@colegio.com");
    d.setClave(encoder.encode("docente123"));
    docenteRepo.save(d);

    Aula aula = new Aula();
    aula.setGrado("5to");
    aula.setNombre("Aula A");
    aulaRepo.save(aula);

    Materia m = new Materia();
    m.setNombre("Matemática");
    m.setAula(aula);
    m.setDocente(d);
    materiaRepo.save(m);

    alumno("Juan", "Pérez", "45123456", aula);
    alumno("Sofía", "López", "45987654", aula);
  }

  private void alumno(String nombre, String apellido, String dni, Aula aula) {
    Alumno a = new Alumno();
    a.setNombre(nombre);
    a.setApellido(apellido);
    a.setDni(dni);
    a.setAula(aula);
    alumnoRepo.save(a);
  }
}
