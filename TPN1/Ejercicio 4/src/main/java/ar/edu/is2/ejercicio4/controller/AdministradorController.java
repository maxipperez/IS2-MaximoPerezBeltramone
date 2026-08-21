package ar.edu.is2.ejercicio4.controller;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.Administrador;
import ar.edu.is2.ejercicio4.service.AdministradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC del ABM de administradores, el segundo subtipo concreto de Usuario.
 *
 * <p>{@code @Controller} indica a Spring que la clase atiende solicitudes web y retorna vistas.
 * {@code @RequestMapping} define el prefijo {@code /administradores}. Cada {@code @GetMapping}
 * muestra una pantalla y cada {@code @PostMapping} procesa datos enviados desde un formulario.
 * {@link Model} transporta datos y mensajes a Thymeleaf; {@code @ModelAttribute} convierte los
 * campos HTTP en un objeto Administrador y {@code @PathVariable} lee el id presente en la URL.
 */
@Controller
@RequestMapping("/administradores")
public class AdministradorController {
  private final AdministradorService service;

  public AdministradorController(AdministradorService s) {
    service = s;
  }

  /** Lista los administradores activos y entrega la vista de tabla. */
  @GetMapping
  public String listar(Model m) {
    m.addAttribute("administradores", service.listar());
    return "administradores/lista";
  }

  /** Entrega un Administrador vacío a la vista de alta. */
  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("administrador", new Administrador());
    return "administradores/formulario";
  }

  /** Guarda el alta; si el servicio lanza ErrorService vuelve al formulario con su mensaje. */
  @PostMapping
  public String guardar(@ModelAttribute Administrador a, Model m) {
    try {
      service.guardar(a);
      return "redirect:/administradores";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("administrador", a);
      return "administradores/formulario";
    }
  }

  /** Busca el registro cuyo id se recibió en la URL y lo envía al formulario de edición. */
  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("administrador", service.buscar(id));
    return "administradores/formulario";
  }

  /** Procesa la modificación sin perder los valores recibidos si ocurre una validación inválida. */
  @PostMapping("/{id}")
  public String modificar(@PathVariable String id, @ModelAttribute Administrador a, Model m) {
    try {
      service.modificar(id, a);
      return "redirect:/administradores";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      m.addAttribute("administrador", a);
      return "administradores/formulario";
    }
  }

  /** Solicita la baja lógica; la operación no elimina físicamente la fila de la base. */
  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id) {
    service.eliminar(id);
    return "redirect:/administradores";
  }
}
