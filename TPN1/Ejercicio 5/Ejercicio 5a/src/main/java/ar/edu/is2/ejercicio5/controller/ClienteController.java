package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.dto.ClienteDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
  private final ClienteService service;

  public ClienteController(ClienteService service) {
    this.service = service;
  }

  @GetMapping
  public String listar(Model m) {
    m.addAttribute("clientes", service.listarClientes());
    return "clientes/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model m) {
    m.addAttribute("cliente", new ClienteDTO());
    return "clientes/formulario";
  }

  @PostMapping
  public String crear(@ModelAttribute("cliente") ClienteDTO dto, Model m, RedirectAttributes flash) {
    try {
      service.crearCliente(dto);
      flash.addFlashAttribute("exito", "Cliente creado con éxito");
      return "redirect:/clientes";
    } catch (ErrorService e) {
      m.addAttribute("error", e.getMessage());
      return "clientes/formulario";
    }
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable String id, Model m) {
    m.addAttribute("cliente", service.buscarPorId(id));
    return "clientes/formulario";
  }

  @PostMapping("/{id}")
  public String modificar(
      @PathVariable String id,
      @ModelAttribute("cliente") ClienteDTO dto,
      Model m,
      RedirectAttributes flash) {
    try {
      service.modificarCliente(id, dto);
      flash.addFlashAttribute("exito", "Cliente modificado con éxito");
      return "redirect:/clientes";
    } catch (ErrorService e) {
      dto.setId(id);
      m.addAttribute("error", e.getMessage());
      return "clientes/formulario";
    }
  }

  @PostMapping("/{id}/eliminar")
  public String eliminar(@PathVariable String id, RedirectAttributes flash) {
    service.eliminarCliente(id);
    flash.addFlashAttribute("exito", "Cliente eliminado");
    return "redirect:/clientes";
  }
}
