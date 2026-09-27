package ar.edu.is2.ejercicio6.controller;

import ar.edu.is2.ejercicio6.dto.DocenteDTO;
import ar.edu.is2.ejercicio6.model.Sexo;
import ar.edu.is2.ejercicio6.service.DocenteService;
import ar.edu.is2.ejercicio6.service.ErrorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AutenticacionController {
  private final DocenteService docenteService;

  public AutenticacionController(DocenteService docenteService) {
    this.docenteService = docenteService;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/registro")
  public String registro(Model model) {
    model.addAttribute("docente", new DocenteDTO());
    model.addAttribute("sexos", Sexo.values());
    return "registro";
  }

  @PostMapping("/registro")
  public String registrar(@ModelAttribute("docente") DocenteDTO dto, Model model, RedirectAttributes flash) {
    try {
      docenteService.registrar(dto);
      flash.addFlashAttribute("exito", "Registro exitoso. Te enviamos un correo de bienvenida.");
      return "redirect:/login";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      model.addAttribute("sexos", Sexo.values());
      return "registro";
    }
  }
}
