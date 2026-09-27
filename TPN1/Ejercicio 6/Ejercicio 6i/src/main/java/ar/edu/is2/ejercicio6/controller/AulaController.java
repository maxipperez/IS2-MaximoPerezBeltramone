package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.dto.AulaDTO;
import ar.edu.is2.ejercicio6.service.AulaService;
import ar.edu.is2.ejercicio6.service.ErrorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aulas")
public class AulaController {
  private final AulaService service;

  public AulaController(AulaService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model model) {
    model.addAttribute("aulas", service.listar());
    return "aulas/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model) {
    model.addAttribute("aula", new AulaDTO());
    return "aulas/formulario";
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable Long id, Model model) {
    model.addAttribute("aula", service.buscar(id));
    return "aulas/formulario";
  }

  @PostMapping
  public String guardar(@ModelAttribute("aula") AulaDTO dto, Model model, RedirectAttributes flash) {
    try {
      service.guardar(dto);
      flash.addFlashAttribute("exito", "Aula guardada correctamente.");
      return "redirect:/aulas";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      return "aulas/formulario";
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
    try {
      service.eliminar(id);
      flash.addFlashAttribute("exito", "Aula eliminada.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/aulas";
  }
}
