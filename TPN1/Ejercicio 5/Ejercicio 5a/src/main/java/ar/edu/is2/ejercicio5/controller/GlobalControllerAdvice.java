package ar.edu.is2.ejercicio5.controller;

import ar.edu.is2.ejercicio5.config.AutenticacionInterceptor;
import ar.edu.is2.ejercicio5.dto.SesionDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
  @ModelAttribute("usuarioSesion")
  public SesionDTO usuarioSesion(HttpSession session) {
    return (SesionDTO) session.getAttribute(AutenticacionInterceptor.USUARIO_SESION);
  }

  @ModelAttribute("rutaActual")
  public String rutaActual(HttpServletRequest request) {
    return request.getRequestURI().substring(request.getContextPath().length());
  }

  @ExceptionHandler(ErrorService.class)
  public String errorDeNegocio(ErrorService e, Model m) {
    m.addAttribute("mensaje", e.getMessage());
    return "error";
  }
}
