package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.dto.NotaDTO;
import ar.edu.is2.ejercicio6.service.AlumnoService;
import ar.edu.is2.ejercicio6.service.ErrorService;
import ar.edu.is2.ejercicio6.service.MateriaService;
import ar.edu.is2.ejercicio6.service.NotaService;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notas")
public class NotaController {
  private final NotaService service;
  private final AlumnoService alumnoService;
  private final MateriaService materiaService;

  public NotaController(NotaService service, AlumnoService alumnoService, MateriaService materiaService) {
    this.service = service;
    this.alumnoService = alumnoService;
    this.materiaService = materiaService;
  }

  @GetMapping
  public String listar(@RequestParam(required = false) Long alumnoId, Model model) {
    model.addAttribute("notas", service.listar(alumnoId));
    model.addAttribute("alumnos", alumnoService.listar());
    model.addAttribute("alumnoId", alumnoId);
    return "notas/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model) {
    NotaDTO dto = new NotaDTO();
    dto.setFecha(LocalDate.now());
    return formulario(dto, model);
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable Long id, Model model) {
    return formulario(service.buscar(id), model);
  }

  @PostMapping
  public String guardar(@ModelAttribute("nota") NotaDTO dto, Model model, RedirectAttributes flash) {
    try {
      service.guardar(dto);
      flash.addFlashAttribute("exito", "Nota guardada correctamente.");
      return "redirect:/notas";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      return formulario(dto, model);
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
    service.eliminar(id);
    flash.addFlashAttribute("exito", "Nota eliminada.");
    return "redirect:/notas";
  }

  @GetMapping("/{id}/historial")
  public String historial(@PathVariable Long id, Model model) {
    model.addAttribute("nota", service.buscar(id));
    model.addAttribute("revisiones", service.historial(id));
    return "notas/historial";
  }

  private String formulario(NotaDTO dto, Model model) {
    model.addAttribute("nota", dto);
    model.addAttribute("alumnos", alumnoService.listar());
    model.addAttribute("materias", materiaService.listar());
    return "notas/formulario";
  }
}
