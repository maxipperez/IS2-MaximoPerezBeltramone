package ar.edu.is2.ejercicio7;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SeguridadTest {
  @Autowired private MockMvc mvc;

  @Test
  void sinIniciarSesionRedirigeAlLogin() throws Exception {
    mvc.perform(get("/socios")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  void loginConCredencialesCorrectas() throws Exception {
    mvc.perform(formLogin("/login").userParameter("mail").passwordParam("clave").user("recepcion@club.com").password("club123"))
        .andExpect(redirectedUrl("/"));
  }

  @Test
  void loginConClaveIncorrecta() throws Exception {
    mvc.perform(formLogin("/login").userParameter("mail").passwordParam("clave").user("recepcion@club.com").password("mala"))
        .andExpect(redirectedUrl("/login?error"));
  }

  @Test
  @WithMockUser
  void usuarioAutenticadoAccedeALosSocios() throws Exception {
    mvc.perform(get("/socios")).andExpect(status().isOk()).andExpect(view().name("socios/lista"));
  }
}
