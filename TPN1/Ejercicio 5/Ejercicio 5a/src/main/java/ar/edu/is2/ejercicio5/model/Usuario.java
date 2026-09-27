package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank
  @Column(nullable = false)
  private String nombre;

  @NotBlank
  @Column(nullable = false)
  private String apellido;

  @NotBlank
  @Email
  @Column(nullable = false, unique = true)
  private String mail;

  @NotBlank
  @Column(nullable = false)
  private String clave;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Rol rol = Rol.VENDEDOR;

  private LocalDate alta = LocalDate.now();
  private LocalDate baja;

  public boolean isActivo() {
    return baja == null;
  }

  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String v) {
    nombre = v;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String v) {
    apellido = v;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String v) {
    mail = v;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String v) {
    clave = v;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol v) {
    rol = v;
  }

  public LocalDate getAlta() {
    return alta;
  }

  public LocalDate getBaja() {
    return baja;
  }

  public void setBaja(LocalDate v) {
    baja = v;
  }
}
