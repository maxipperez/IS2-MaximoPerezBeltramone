package ar.edu.is2.ejercicio4.controller;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.Departamento;
import ar.edu.is2.ejercicio4.service.DepartamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC del ABM de departamentos.
 *
 * <p>{@code @Controller} permite responder solicitudes devolviendo plantillas Thymeleaf y
 * {@code @RequestMapping} define el prefijo {@code /departamentos}. {@code @GetMapping} muestra
 * formularios o listados; {@code @PostMapping} recibe las acciones de alta, modificación y baja.
 * Model entrega atributos a la interfaz, y ErrorService se captura para mostrar el error sin
 * descartar lo que el usuario ingresó.
 */
@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
  private final DepartamentoService service;

  public DepartamentoController(DepartamentoService s) {
    service = s;
  }

  /** Carga el listado de departamentos que no fueron eliminados lógicamente. */
  @GetMapping
  public String listar(Model m) {
    m.addAttribute("departamentos", service.listar());
    return "departamentos/lista";
  }

  /** Envía un Departamento vacío a la vista de creación. */
  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("departamento", new Departamento());
    return "departamentos/formulario";
  }

  /** Persiste un alta o vuelve a mostrar la plantilla con el mensaje de ErrorService. */
  @PostMapping
  public String guardar(@ModelAttribute Departamento d, Model m) {
    try {
      service.guardar(d);
      return "redirect:/departamentos";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("departamento", d);
      return "departamentos/formulario";
    }
  }

  /** Usa @PathVariable para localizar el departamento y preparar su edición. */
  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("departamento", service.buscar(id));
    return "departamentos/formulario";
  }

  /** Valida y actualiza el objeto cuyo identificador vino en la URL. */
  @PostMapping("/{id}")
  public String modificar(@PathVariable String id, @ModelAttribute Departamento d, Model m) {
    try {
      service.modificar(id, d);
      return "redirect:/departamentos";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("departamento", d);
      return "departamentos/formulario";
    }
  }

  /** Solicita la baja lógica sin invocar deleteById. */
  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id) {
    service.eliminar(id);
    return "redirect:/departamentos";
  }
}
