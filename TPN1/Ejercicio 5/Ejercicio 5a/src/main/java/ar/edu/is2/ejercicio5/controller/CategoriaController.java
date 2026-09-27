package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.CategoriaDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
  private final CategoriaService service;

  public CategoriaController(CategoriaService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("categorias", service.listarCategorias());
    return "categorias/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("categoria", new CategoriaDTO());
    return "categorias/formulario";
  }

  @PostMapping
  public String crear(
      @ModelAttribute("categoria") CategoriaDTO dto, Model m, RedirectAttributes flash) {
    try {
      service.crearCategoria(dto);
      flash.addFlashAttribute("exito", "Categoría creada con éxito");
      return "redirect:/categorias";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      return "categorias/formulario";
    }
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("categoria", service.buscarPorId(id));
    return "categorias/formulario";
  }

  @PostMapping("/{id}")
  public String modificar(
      @PathVariable String id,
      @ModelAttribute("categoria") CategoriaDTO dto,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarCategoria(id, dto);
      flash.addFlashAttribute("exito", "Categoría modificada con éxito");
      return "redirect:/categorias";
    } catch (ErrorService e) {
      dto.setId(id);
      m.addAttribute("error", e.getMessage());
      return "categorias/formulario";
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id, RedirectAttributes flash) {
    service.eliminarCategoria(id);
    flash.addFlashAttribute("exito", "Categoría eliminada. Sus productos quedaron sin categoría.");
    return "redirect:/categorias";
  }
}
