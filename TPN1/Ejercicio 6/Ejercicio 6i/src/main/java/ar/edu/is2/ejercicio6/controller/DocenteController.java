package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.dto.CambioClaveDTO;
import ar.edu.is2.ejercicio6.service.DocenteService;
import ar.edu.is2.ejercicio6.service.ErrorService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DocenteController {
  private final DocenteService service;

  public DocenteController(DocenteService service) {
    this.service = service;
  }

  @GetMapping("/docentes")
  public String listar(Model model) {
    model.addAttribute("docentes", service.listar());
    return "docentes/lista";
  }

  @GetMapping("/cambiar-clave")
  public String cambiarClave(Model model) {
    model.addAttribute("cambio", new CambioClaveDTO());
    return "docentes/cambiar-clave";
  }

  @PostMapping("/cambiar-clave")
  public String guardarClave(
      @ModelAttribute("cambio") CambioClaveDTO dto, Principal principal, Model model, RedirectAttributes flash) {
    try {
      service.cambiarClave(principal.getName(), dto);
      flash.addFlashAttribute("exito", "La contraseña se cambió correctamente.");
      return "redirect:/";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      model.addAttribute("cambio", new CambioClaveDTO());
      return "docentes/cambiar-clave";
    }
  }
}
