package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.dto.AlumnoDTO;
import ar.edu.is2.ejercicio6.service.AlumnoService;
import ar.edu.is2.ejercicio6.service.AulaService;
import ar.edu.is2.ejercicio6.service.ErrorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
  private final AlumnoService service;
  private final AulaService aulaService;

  public AlumnoController(AlumnoService service, AulaService aulaService) {
    this.service = service;
    this.aulaService = aulaService;
  }

  @GetMapping
  public String listar(Model model) {
    model.addAttribute("alumnos", service.listar());
    return "alumnos/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model) {
    return formulario(new AlumnoDTO(), model);
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable Long id, Model model) {
    return formulario(service.buscar(id), model);
  }

  @PostMapping
  public String guardar(@ModelAttribute("alumno") AlumnoDTO dto, Model model, RedirectAttributes flash) {
    try {
      service.guardar(dto);
      flash.addFlashAttribute("exito", "Alumno guardado correctamente.");
      return "redirect:/alumnos";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      return formulario(dto, model);
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
    try {
      service.eliminar(id);
      flash.addFlashAttribute("exito", "Alumno eliminado.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/alumnos";
  }

  private String formulario(AlumnoDTO dto, Model model) {
    model.addAttribute("alumno", dto);
    model.addAttribute("aulas", aulaService.listar());
    return "alumnos/formulario";
  }
}
