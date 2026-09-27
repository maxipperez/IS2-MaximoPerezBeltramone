package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String apellido;

  @Column(nullable = false, unique = true)
  private String dni;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private FotoRostro foto;

  public String getNombreCompleto() {
    return apellido + ", " + nombre;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public FotoRostro getFoto() {
    return foto;
  }

  public void setFoto(FotoRostro foto) {
    this.foto = foto;
  }
}
