package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.config.AutenticacionInterceptor;
import ar.edu.is2.ejercicio5.dto.LoginDTO;
import ar.edu.is2.ejercicio5.dto.UsuarioDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AutenticacionController {
  private final UsuarioService service;

  public AutenticacionController(UsuarioService service) {
    this.service = service;
  }

  @GetMapping("/login")
  public String login(HttpSession session, Model m) {
    if (session.getAttribute(AutenticacionInterceptor.USUARIO_SESION) != null) return "redirect:/";
    m.addAttribute("login", new LoginDTO());
    return "login";
  }

  @PostMapping("/login")
  public String iniciarSesion(
      @ModelAttribute("login") LoginDTO dto, HttpServletRequest request, Model m) {
    try {
      var sesion = service.iniciarSesion(dto);
      request.getSession().invalidate();
      request.getSession(true).setAttribute(AutenticacionInterceptor.USUARIO_SESION, sesion);
      return "redirect:/";
    } catch (ErrorService e) {
      dto.setClave(null);
      m.addAttribute("error", e.getMessage());
      return "login";
    }
  }

  @GetMapping("/registro")
  public String registro(Model m) {
    m.addAttribute("usuario", new UsuarioDTO());
    return "registro";
  }

  @PostMapping("/registro")
  public String registrar(
      @ModelAttribute("usuario") UsuarioDTO dto, Model m, RedirectAttributes flash) {
    try {
      service.registrarUsuario(dto);
      flash.addFlashAttribute("exito", "Registro exitoso. Ya puede iniciar sesión.");
      return "redirect:/login";
    } catch (ErrorService e) {
      dto.setClave(null);
      dto.setClaveRepetida(null);
      m.addAttribute("error", e.getMessage());
      return "registro";
    }
  }

  @PostMapping("/logout")
  public String cerrarSesion(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }
}
