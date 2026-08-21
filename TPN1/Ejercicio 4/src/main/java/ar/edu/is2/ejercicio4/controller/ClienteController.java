package ar.edu.is2.ejercicio4.controller;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.Cliente;
import ar.edu.is2.ejercicio4.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC del ABM de clientes con rutas web completas y bajas lógicas.
 *
 * <p>{@code @Controller} registra la clase en Spring MVC. {@code @RequestMapping} agrupa las rutas
 * bajo {@code /clientes}; {@code @GetMapping} entrega vistas y {@code @PostMapping} procesa
 * formularios. Model lleva datos a Thymeleaf, mientras que {@code @PathVariable} y
 * {@code @ModelAttribute} obtienen los datos provenientes de la URL y del formulario.
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {
  private final ClienteService service;

  public ClienteController(ClienteService s) {
    service = s;
  }

  /** Lista solo los clientes cuyo estado no es ELIMINADO. */
  @GetMapping
  public String listar(Model m) {
    m.addAttribute("clientes", service.listar());
    return "clientes/lista";
  }

  /** Crea el objeto que Thymeleaf enlazará a los campos para una nueva alta. */
  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("cliente", new Cliente());
    return "clientes/formulario";
  }

  /** Guarda un cliente y vuelve al formulario con el mensaje si hay ErrorService. */
  @PostMapping
  public String guardar(@ModelAttribute Cliente c, Model m) {
    try {
      service.guardar(c);
      return "redirect:/clientes";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("cliente", c);
      return "clientes/formulario";
    }
  }

  /** Carga un cliente existente para editarlo a partir del identificador de la ruta. */
  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("cliente", service.buscar(id));
    return "clientes/formulario";
  }

  /** Modifica el cliente identificado, preservando los datos capturados si falla una regla. */
  @PostMapping("/{id}")
  public String modificar(@PathVariable String id, @ModelAttribute Cliente c, Model m) {
    try {
      service.modificar(id, c);
      return "redirect:/clientes";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("cliente", c);
      return "clientes/formulario";
    }
  }

  /** Ejecuta la baja lógica del cliente a través de ClienteService. */
  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id) {
    service.eliminar(id);
    return "redirect:/clientes";
  }
}
