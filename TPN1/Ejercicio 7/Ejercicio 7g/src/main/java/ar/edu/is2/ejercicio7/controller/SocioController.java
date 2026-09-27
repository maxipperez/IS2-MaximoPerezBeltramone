package ar.edu.is2.ejercicio7.controller;

import ar.edu.is2.ejercicio7.dto.FamiliarDTO;
import ar.edu.is2.ejercicio7.dto.FotoDTO;
import ar.edu.is2.ejercicio7.dto.SocioDTO;
import ar.edu.is2.ejercicio7.service.ErrorService;
import ar.edu.is2.ejercicio7.service.PagoService;
import ar.edu.is2.ejercicio7.service.SocioService;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SocioController {
  private final SocioService service;
  private final PagoService pagoService;

  public SocioController(SocioService service, PagoService pagoService) {
    this.service = service;
    this.pagoService = pagoService;
  }

  @GetMapping("/socios")
  public String listar(Model model) {
    model.addAttribute("socios", service.listar());
    return "socios/lista";
  }

  @GetMapping("/socios/nuevo")
  public String nuevo(Model model) {
    model.addAttribute("socio", new SocioDTO());
    return "socios/formulario";
  }

  @GetMapping("/socios/{id}/editar")
  public String editar(@PathVariable Long id, Model model) {
    model.addAttribute("socio", service.buscar(id));
    return "socios/formulario";
  }

  @PostMapping("/socios")
  public String guardar(
      @ModelAttribute("socio") SocioDTO dto,
      @RequestParam(required = false) MultipartFile foto,
      Model model,
      RedirectAttributes flash)
      throws IOException {
    try {
      SocioDTO guardado = service.guardar(dto, bytes(foto), mime(foto));
      flash.addFlashAttribute("exito", "Socio guardado correctamente.");
      return "redirect:/socios/" + guardado.getId();
    } catch (ErrorService e) {
      model.addAttribute("error", e.getMessage());
      return "socios/formulario";
    }
  }

  @PostMapping("/socios/{id}/eliminar")
  public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
    try {
      service.eliminar(id);
      flash.addFlashAttribute("exito", "Socio eliminado.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/socios";
  }

  @GetMapping("/socios/{id}")
  public String detalle(@PathVariable Long id, Model model) {
    SocioDTO socio = service.buscar(id);
    model.addAttribute("socio", socio);
    model.addAttribute("familiares", service.familiares(id));
    model.addAttribute("pagos", pagoService.porGrupo(socio.getGrupoId()));
    model.addAttribute("familiar", new FamiliarDTO());
    return "socios/detalle";
  }

  @PostMapping("/socios/{id}/familiares")
  public String agregarFamiliar(
      @PathVariable Long id,
      @ModelAttribute FamiliarDTO dto,
      @RequestParam(required = false) MultipartFile foto,
      RedirectAttributes flash)
      throws IOException {
    try {
      service.agregarFamiliar(id, dto, bytes(foto), mime(foto));
      flash.addFlashAttribute("exito", "Familiar agregado al grupo.");
    } catch (ErrorService e) {
      flash.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/socios/" + id;
  }

  @PostMapping("/socios/{id}/familiares/{familiarId}/quitar")
  public String quitarFamiliar(@PathVariable Long id, @PathVariable Long familiarId, RedirectAttributes flash) {
    service.quitarFamiliar(familiarId);
    flash.addFlashAttribute("exito", "El familiar se quitó del grupo.");
    return "redirect:/socios/" + id;
  }

  @GetMapping("/fotos/{personaId}")
  public ResponseEntity<byte[]> foto(@PathVariable Long personaId) {
    FotoDTO f = service.foto(personaId);
    if (f == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(f.getMime())).body(f.getImagen());
  }

  private byte[] bytes(MultipartFile foto) throws IOException {
    return foto == null || foto.isEmpty() ? null : foto.getBytes();
  }

  private String mime(MultipartFile foto) {
    return foto == null ? null : foto.getContentType();
  }
}
