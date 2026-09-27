package ar.edu.is2.ejercicio7.controller;

import ar.edu.is2.ejercicio7.service.AccesoService;
import ar.edu.is2.ejercicio7.service.ErrorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accesos")
public class AccesoController {
  private final AccesoService service;

  public AccesoController(AccesoService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model model) {
    model.addAttribute("personas", service.personas());
    model.addAttribute("presentes", service.presentes());
    model.addAttribute("ultimos", service.ultimos());
    return "accesos/lista";
  }

  @PostMapping("/entrada")
  public String entrada(@RequestParam(required = false) Long personaId, RedirectAttributes flash) {
    try {
      service.registrarEntrada(personaId);
      flash.addFlashAttribute("exito", "Entrada registrada.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/accesos";
  }

  @PostMapping("/salida")
  public String salida(@RequestParam(required = false) Long personaId, RedirectAttributes flash) {
    try {
      service.registrarSalida(personaId);
      flash.addFlashAttribute("exito", "Salida registrada.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/accesos";
  }
}
