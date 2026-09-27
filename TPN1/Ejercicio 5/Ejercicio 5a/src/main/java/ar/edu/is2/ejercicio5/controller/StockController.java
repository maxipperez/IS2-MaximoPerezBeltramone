package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.StockDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/stock")
public class StockController {
  private final StockService service;

  public StockController(StockService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("stocks", service.listarStock());
    return "stock/lista";
  }

  @GetMapping("/{productoId}/editar")
  public String editar(@PathVariable String productoId, Model m) {
    m.addAttribute("stock", service.buscarPorProducto(productoId));
    return "stock/formulario";
  }

  @PostMapping("/{productoId}")
  public String modificar(
      @PathVariable String productoId,
      @ModelAttribute("stock") StockDTO dto,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarStock(productoId, dto);
      flash.addFlashAttribute("exito", "Stock actualizado");
      return "redirect:/stock";
    } catch (ErrorService e) {
      dto.setProductoId(productoId);
      dto.setProductoNombre(service.buscarPorProducto(productoId).getProductoNombre());
      m.addAttribute("error", e.getMessage());
      return "stock/formulario";
    }
  }
}
