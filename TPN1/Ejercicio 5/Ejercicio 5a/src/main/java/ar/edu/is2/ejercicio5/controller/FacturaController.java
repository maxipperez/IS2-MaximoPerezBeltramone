package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/facturas")
public class FacturaController {
  private final FacturaService service;

  public FacturaController(FacturaService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("facturas", service.listarFacturas());
    return "facturas/lista";
  }
}
