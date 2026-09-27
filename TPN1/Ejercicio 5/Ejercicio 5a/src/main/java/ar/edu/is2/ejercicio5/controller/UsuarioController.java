package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.config.AutenticacionInterceptor;
import ar.edu.is2.ejercicio5.dto.SesionDTO;
import ar.edu.is2.ejercicio5.dto.UsuarioDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
  private final UsuarioService service;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("usuarios", service.listarUsuarios());
    return "usuarios/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("usuario", new UsuarioDTO());
    return "usuarios/formulario";
  }

  @PostMapping
  public String crear(@ModelAttribute("usuario") UsuarioDTO dto, Model m, RedirectAttributes flash) {
    try {
      service.crearUsuario(dto);
      flash.addFlashAttribute("exito", "Usuario creado con éxito");
      return "redirect:/usuarios";
    } catch (ErrorService e) {
      dto.setClave(null);
      dto.setClaveRepetida(null);
      m.addAttribute("error", e.getMessage());
      return "usuarios/formulario";
    }
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("usuario", service.buscarPorId(id));
    return "usuarios/formulario";
  }

  @PostMapping("/{id}")
  public String modificar(
      @PathVariable String id,
      @ModelAttribute("usuario") UsuarioDTO dto,
      @SessionAttribute(AutenticacionInterceptor.USUARIO_SESION) SesionDTO sesion,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarUsuario(id, dto, sesion.getId());
      flash.addFlashAttribute("exito", "Usuario modificado con éxito");
      return "redirect:/usuarios";
    } catch (ErrorService e) {
      dto.setId(id);
      dto.setClave(null);
      dto.setClaveRepetida(null);
      m.addAttribute("error", e.getMessage());
      return "usuarios/formulario";
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(
      @PathVariable String id,
      @SessionAttribute(AutenticacionInterceptor.USUARIO_SESION) SesionDTO sesion,
      RedirectAttributes flash) {
    try {
      service.eliminarUsuario(id, sesion.getId());
      flash.addFlashAttribute("exito", "Usuario dado de baja");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/usuarios";
  }
}
