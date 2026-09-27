package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.config.AutenticacionInterceptor;
import ar.edu.is2.ejercicio5.dto.SesionDTO;
import ar.edu.is2.ejercicio5.dto.VentaDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.ClienteService;
import ar.edu.is2.ejercicio5.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class VentaController {
  private final VentaService service;
  private final ClienteService clienteService;

  public VentaController(VentaService service, ClienteService clienteService) {
    this.service = service;
    this.clienteService = clienteService;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("ventas", service.listarVentas());
    return "ventas/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(@RequestParam(required = false) String productoId, Model m) {
    return formulario(service.formularioNuevo(productoId), m);
  }

  @PostMapping
  public String registrar(
      @ModelAttribute("venta") VentaDTO dto, Model m, RedirectAttributes flash) {
    try {
      VentaDTO venta = service.registrarVenta(dto);
      flash.addFlashAttribute("exito", "Venta registrada. Se generó el comprobante.");
      return "redirect:/ventas/" + venta.getId();
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      dto.setItems(service.completarItems(dto.getItems()));
      return formulario(dto, m);
    }
  }

  @GetMapping("/{id}")
  public String comprobante(@PathVariable String id, Model m) {
    m.addAttribute("venta", service.buscarPorId(id));
    return "ventas/comprobante";
  }

  @PostMapping("/{id}/anular")
  public String anular(
      @PathVariable String id,
      @SessionAttribute(AutenticacionInterceptor.USUARIO_SESION) SesionDTO sesion,
      RedirectAttributes flash) {
    if (!sesion.isAdministrador()) {
      flash.addFlashAttribute("error", "Solo un administrador puede anular ventas.");
      return "redirect:/ventas/" + id;
    }
    try {
      service.anularVenta(id);
      flash.addFlashAttribute("exito", "Venta anulada y stock repuesto");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/ventas/" + id;
  }

  private String formulario(VentaDTO dto, Model m) {
    m.addAttribute("venta", dto);
    m.addAttribute("clientes", clienteService.listarClientes());
    return "ventas/formulario";
  }
}
