package ar.edu.is2.ejercicio6.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  private static final Logger log = LoggerFactory.getLogger(MailService.class);

  private final JavaMailSender mailSender;

  public MailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void enviarBienvenida(String mail, String nombre) {
    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(mail);
    mensaje.setSubject("Bienvenido al Sistema Escolar");
    mensaje.setText(
        "Hola "
            + nombre
            + ",\n\nTu cuenta de docente fue creada correctamente."
            + "\nYa podés ingresar al sistema con tu correo personal y tu contraseña."
            + "\n\nSistema Escolar");
    try {
      mailSender.send(mensaje);
    } catch (Exception e) {
      log.warn("No se pudo enviar el correo de bienvenida a {}: {}", mail, e.getMessage());
    }
  }
}
