package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.service.AlumnoService;
import ar.edu.is2.ejercicio6.service.AulaService;
import ar.edu.is2.ejercicio6.service.DocenteService;
import ar.edu.is2.ejercicio6.service.MateriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
  private final AlumnoService alumnoService;
  private final AulaService aulaService;
  private final MateriaService materiaService;
  private final DocenteService docenteService;

  public InicioController(
      AlumnoService alumnoService,
      AulaService aulaService,
      MateriaService materiaService,
      DocenteService docenteService) {
    this.alumnoService = alumnoService;
    this.aulaService = aulaService;
    this.materiaService = materiaService;
    this.docenteService = docenteService;
  }

  @GetMapping("/")
  public String inicio(Model model) {
    model.addAttribute("alumnos", alumnoService.listar().size());
    model.addAttribute("aulas", aulaService.listar().size());
    model.addAttribute("materias", materiaService.listar().size());
    model.addAttribute("docentes", docenteService.listar().size());
    return "inicio";
  }
}
