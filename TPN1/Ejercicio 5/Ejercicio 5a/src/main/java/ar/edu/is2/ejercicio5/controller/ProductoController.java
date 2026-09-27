package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.ProductoDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.CategoriaService;
import ar.edu.is2.ejercicio5.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {
  private final ProductoService service;
  private final CategoriaService categoriaService;

  public ProductoController(ProductoService service, CategoriaService categoriaService) {
    this.service = service;
    this.categoriaService = categoriaService;
  }

  @GetMapping
  public String listar(
      @RequestParam(required = false) String texto,
      @RequestParam(required = false) String categoriaId,
      Model m) {
    m.addAttribute("productos", service.buscar(texto, categoriaId));
    m.addAttribute("categorias", categoriaService.listarCategorias());
    m.addAttribute("texto", texto);
    m.addAttribute("categoriaId", categoriaId);
    return "productos/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    return formulario(new ProductoDTO(), m);
  }

  @PostMapping
  public String crear(@ModelAttribute("producto") ProductoDTO dto, Model m, RedirectAttributes flash) {
    try {
      service.crearProducto(dto);
      flash.addFlashAttribute("exito", "Producto creado con éxito");
      return "redirect:/productos";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      return formulario(dto, m);
    }
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    return formulario(service.buscarPorId(id), m);
  }

  @PostMapping("/{id}")
  public String modificar(
      @PathVariable String id,
      @ModelAttribute("producto") ProductoDTO dto,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarProducto(id, dto);
      flash.addFlashAttribute("exito", "Producto modificado con éxito");
      return "redirect:/productos";
    } catch (ErrorService e) {
      dto.setId(id);
      m.addAttribute("error", e.getMessage());
      return formulario(dto, m);
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id, RedirectAttributes flash) {
    try {
      service.eliminarProducto(id);
      flash.addFlashAttribute("exito", "Producto eliminado");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/productos";
  }

  private String formulario(ProductoDTO dto, Model m) {
    m.addAttribute("producto", dto);
    m.addAttribute("categorias", categoriaService.listarCategorias());
    return "productos/formulario";
  }
}
