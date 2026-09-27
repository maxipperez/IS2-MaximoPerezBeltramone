package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.ProveedorDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
  private final ProveedorService service;

  public ProveedorController(ProveedorService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("proveedores", service.listarProveedores());
    return "proveedores/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("proveedor", new ProveedorDTO());
    return "proveedores/formulario";
  }

  @PostMapping
  public String crear(
      @ModelAttribute("proveedor") ProveedorDTO dto, Model m, RedirectAttributes flash) {
    try {
      service.crearProveedor(dto);
      flash.addFlashAttribute("exito", "Proveedor creado con éxito");
      return "redirect:/proveedores";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      return "proveedores/formulario";
    }
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("proveedor", service.buscarProveedorID(id));
    return "proveedores/formulario";
  }

  @PostMapping("/{id}")
  public String modificar(
      @PathVariable String id,
      @ModelAttribute("proveedor") ProveedorDTO dto,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarProveedor(id, dto);
      flash.addFlashAttribute("exito", "Proveedor modificado con éxito");
      return "redirect:/proveedores";
    } catch (ErrorService e) {
      dto.setId(id);
      m.addAttribute("error", e.getMessage());
      return "proveedores/formulario";
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id, RedirectAttributes flash) {
    service.eliminarProveedor(id);
    flash.addFlashAttribute("exito", "Proveedor dado de baja");
    return "redirect:/proveedores";
  }
}
