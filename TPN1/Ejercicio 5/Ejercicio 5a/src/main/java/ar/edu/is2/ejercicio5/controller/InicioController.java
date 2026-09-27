package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.ProductoDTO;
import ar.edu.is2.ejercicio5.service.CategoriaService;
import ar.edu.is2.ejercicio5.service.ProductoService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InicioController {
  private final ProductoService productoService;
  private final CategoriaService categoriaService;

  public InicioController(ProductoService productoService, CategoriaService categoriaService) {
    this.productoService = productoService;
    this.categoriaService = categoriaService;
  }

  @GetMapping("/")
  public String catalogo(
      @RequestParam(required = false) String texto,
      @RequestParam(required = false) String categoriaId,
      @RequestParam(required = false) String denegado,
      Model m) {
    List<ProductoDTO> productos = productoService.buscar(texto, categoriaId);
    m.addAttribute("productos", productos);
    m.addAttribute("categorias", categoriaService.listarCategorias());
    m.addAttribute("texto", texto);
    m.addAttribute("categoriaId", categoriaId);
    if (productos.isEmpty())
      m.addAttribute("sinResultados", "No se encontraron productos para el filtro seleccionado");
    if (denegado != null)
      m.addAttribute("error", "No tiene permisos para acceder a esa sección.");
    return "catalogo";
  }
}
