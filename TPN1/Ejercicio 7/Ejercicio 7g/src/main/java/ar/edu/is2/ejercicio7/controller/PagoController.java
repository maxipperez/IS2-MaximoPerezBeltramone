package ar.edu.is2.ejercicio7.controller;

import ar.edu.is2.ejercicio7.dto.PagoDTO;
import ar.edu.is2.ejercicio7.model.MedioPago;
import ar.edu.is2.ejercicio7.service.ErrorService;
import ar.edu.is2.ejercicio7.service.PagoService;
import java.time.YearMonth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pagos")
public class PagoController {
  private final PagoService service;

  public PagoController(PagoService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(@RequestParam(required = false) Long grupoId, Model model) {
    PagoDTO pago = new PagoDTO();
    pago.setGrupoId(grupoId);
    pago.setPeriodo(YearMonth.now().toString());
    return vista(pago, model);
  }

  @PostMapping
  public String registrar(@ModelAttribute("pago") PagoDTO dto, Model model, RedirectAttributes flash) {
    try {
      service.registrar(dto);
      flash.addFlashAttribute("exito", "Pago registrado correctamente.");
      return "redirect:/pagos";
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      return vista(dto, model);
    }
  }

  private String vista(PagoDTO pago, Model model) {
    model.addAttribute("pago", pago);
    model.addAttribute("pagos", service.listar());
    model.addAttribute("grupos", service.grupos());
    model.addAttribute("medios", MedioPago.values());
    return "pagos/lista";
  }
}
