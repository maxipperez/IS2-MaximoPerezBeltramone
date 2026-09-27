package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.dto.MateriaDTO;
import ar.edu.is2.ejercicio6.service.AulaService;
import ar.edu.is2.ejercicio6.service.DocenteService;
import ar.edu.is2.ejercicio6.service.ErrorService;
import ar.edu.is2.ejercicio6.service.MateriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/materias")
public class MateriaController {
  private final MateriaService service;
  private final AulaService aulaService;
  private final DocenteService docenteService;

  public MateriaController(MateriaService service, AulaService aulaService, DocenteService docenteService) {
    this.service = service;
    this.aulaService = aulaService;
    this.docenteService = docenteService;
  }

  @GetMapping
  public String listar(Model model) {
    model.addAttribute("materias", service.listar());
    return "materias/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model) {
    return formulario(new MateriaDTO(), model);
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable Long id, Model model) {
    return formulario(service.buscar(id), model);
  }

  @PostMapping
  public String guardar(@ModelAttribute("materia") MateriaDTO dto, Model model, RedirectAttributes flash) {
    try {
      service.guardar(dto);
      flash.addFlashAttribute("exito", "Materia guardada correctamente.");
      return "redirect:/materias";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      return formulario(dto, model);
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
    try {
      service.eliminar(id);
      flash.addFlashAttribute("exito", "Materia eliminada.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/materias";
  }

  private String formulario(MateriaDTO dto, Model model) {
    model.addAttribute("materia", dto);
    model.addAttribute("aulas", aulaService.listar());
    model.addAttribute("docentes", docenteService.listar());
    return "materias/formulario";
  }
}
