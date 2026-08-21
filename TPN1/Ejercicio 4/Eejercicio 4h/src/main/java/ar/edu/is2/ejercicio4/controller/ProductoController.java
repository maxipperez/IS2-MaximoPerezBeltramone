package ar.edu.is2.ejercicio4.controller;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.Producto;
import ar.edu.is2.ejercicio4.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Controller declara un controlador MVC: sus métodos devuelven nombres de vistas Thymeleaf.
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {
  private final ProductoService service;

  public ProductoController(ProductoService s) {
    service = s;
  }

  /**
   * @GetMapping atiende la consulta HTTP de listado.
   */
  @GetMapping
  public String listar(Model m) {
    m.addAttribute("productos", service.listar());
    return "productos/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("producto", new Producto());
    return "productos/formulario";
  }

  /**
   * @PostMapping recibe y guarda el formulario.
   */
  @PostMapping
  public String guardar(@ModelAttribute Producto p, Model m) {
    try {
      service.guardar(p);
      return "redirect:/productos";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("producto", p);
      return "productos/formulario";
    }
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("producto", service.buscar(id));
    return "productos/formulario";
  }

  @PostMapping("/{id}")
  public String modificar(@PathVariable String id, @ModelAttribute Producto p, Model m) {
    try {
      service.modificar(id, p);
      return "redirect:/productos";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("producto", p);
      return "productos/formulario";
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id) {
    service.eliminar(id);
    return "redirect:/productos";
  }
}
