package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.OrdenCompraDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.OrdenCompraService;
import ar.edu.is2.ejercicio5.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ordenes")
public class OrdenCompraController {
  private final OrdenCompraService service;
  private final ProveedorService proveedorService;

  public OrdenCompraController(OrdenCompraService service, ProveedorService proveedorService) {
    this.service = service;
    this.proveedorService = proveedorService;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("ordenes", service.listarOrdenes());
    return "ordenes/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    return formulario(service.formularioNuevo(), m);
  }

  @PostMapping
  public String crear(
      @ModelAttribute("orden") OrdenCompraDTO dto, Model m, RedirectAttributes flash) {
    try {
      OrdenCompraDTO creada = service.crearOrdenCompra(dto);
      flash.addFlashAttribute("exito", "Orden " + creada.getCodigo() + " registrada como pendiente");
      return "redirect:/ordenes/" + creada.getId();
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      dto.setItems(service.completarItems(dto.getItems()));
      return formulario(dto, m);
    }
  }

  @GetMapping("/{id}")
  public String detalle(@PathVariable String id, Model m) {
    m.addAttribute("orden", service.buscarPorId(id));
    return "ordenes/detalle";
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    return formulario(service.formularioEditar(id), m);
  }

  @PostMapping("/{id}")
  public String modificar(
      @PathVariable String id,
      @ModelAttribute("orden") OrdenCompraDTO dto,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarOrdenCompra(id, dto);
      flash.addFlashAttribute("exito", "Orden modificada con éxito");
      return "redirect:/ordenes/" + id;
    } catch (ErrorService e) {
      dto.setId(id);
      m.addAttribute("error", e.getMessage());
      dto.setItems(service.completarItems(dto.getItems()));
      return formulario(dto, m);
    }
  }

  @PostMapping("/{id}/confirmar")
  public String confirmar(@PathVariable String id, RedirectAttributes flash) {
    try {
      service.confirmarCompra(id);
      flash.addFlashAttribute("exito", "Orden confirmada: se generó la factura y se actualizó el stock");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/ordenes/" + id;
  }

  @PostMapping("/{id}/cancelar")
  public String cancelar(@PathVariable String id, RedirectAttributes flash) {
    try {
      service.cancelarCompra(id);
      flash.addFlashAttribute("exito", "Orden cancelada");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/ordenes";
  }

  private String formulario(OrdenCompraDTO dto, Model m) {
    m.addAttribute("orden", dto);
    m.addAttribute("proveedores", proveedorService.listarActivos());
    return "ordenes/formulario";
  }
}
