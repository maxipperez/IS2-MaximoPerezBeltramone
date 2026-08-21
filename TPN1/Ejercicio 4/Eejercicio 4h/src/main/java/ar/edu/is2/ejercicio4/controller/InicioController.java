package ar.edu.is2.ejercicio4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador de la página inicial.
 *
 * <p>La anotación {@code @Controller} registra esta clase en Spring MVC y permite retornar el
 * nombre de una plantilla Thymeleaf.
 */
@Controller
public class InicioController {
  /**
   * Atiende la ruta raíz de la aplicación.
   *
   * <p>{@code @GetMapping} vincula una petición HTTP GET a este método. El valor retornado indica
   * que Thymeleaf debe renderizar {@code templates/inicio.html}.
   */
  @GetMapping("/")
  public String inicio() {
    return "inicio";
  }
}
