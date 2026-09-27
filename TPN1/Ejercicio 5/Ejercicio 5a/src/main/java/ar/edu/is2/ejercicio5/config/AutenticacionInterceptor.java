package ar.edu.is2.ejercicio5.config;

import ar.edu.is2.ejercicio5.dto.SesionDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AutenticacionInterceptor implements HandlerInterceptor {
  public static final String USUARIO_SESION = "usuarioSesion";

  private static final List<String> RUTAS_ADMINISTRADOR =
      List.of("/productos", "/stock", "/categorias", "/proveedores", "/ordenes", "/facturas", "/usuarios");

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession(false);
    SesionDTO usuario = session == null ? null : (SesionDTO) session.getAttribute(USUARIO_SESION);
    if (usuario == null) {
      response.sendRedirect(request.getContextPath() + "/login");
      return false;
    }
    String ruta = request.getRequestURI().substring(request.getContextPath().length());
    boolean soloAdministrador =
        RUTAS_ADMINISTRADOR.stream().anyMatch(r -> ruta.equals(r) || ruta.startsWith(r + "/"));
    if (soloAdministrador && !usuario.isAdministrador()) {
      response.sendRedirect(request.getContextPath() + "/?denegado");
      return false;
    }
    return true;
  }
}
