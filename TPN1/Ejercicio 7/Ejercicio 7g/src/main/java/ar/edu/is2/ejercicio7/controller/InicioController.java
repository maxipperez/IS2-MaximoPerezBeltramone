package ar.edu.is2.ejercicio7.controller;

import ar.edu.is2.ejercicio7.service.AccesoService;
import ar.edu.is2.ejercicio7.service.PagoService;
import ar.edu.is2.ejercicio7.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
  private final SocioService socioService;
  private final AccesoService accesoService;
  private final PagoService pagoService;

  public InicioController(SocioService socioService, AccesoService accesoService, PagoService pagoService) {
    this.socioService = socioService;
    this.accesoService = accesoService;
    this.pagoService = pagoService;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/")
  public String inicio(Model model) {
    model.addAttribute("socios", socioService.listar().size());
    model.addAttribute("presentes", accesoService.presentes().size());
    model.addAttribute("pagos", pagoService.listar().size());
    return "inicio";
  }
}
