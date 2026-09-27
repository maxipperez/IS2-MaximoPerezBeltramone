package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Audited
public class FotoRostro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotAudited
  @Lob
  @Column(nullable = false, length = 5 * 1024 * 1024)
  private byte[] imagen;

  @Column(nullable = false)
  private String mime;

  private LocalDateTime fechaCaptura = LocalDateTime.now();

  public Long getId() {
    return id;
  }

  public byte[] getImagen() {
    return imagen;
  }

  public void setImagen(byte[] imagen) {
    this.imagen = imagen;
  }

  public String getMime() {
    return mime;
  }

  public void setMime(String mime) {
    this.mime = mime;
  }

  public LocalDateTime getFechaCaptura() {
    return fechaCaptura;
  }
}
