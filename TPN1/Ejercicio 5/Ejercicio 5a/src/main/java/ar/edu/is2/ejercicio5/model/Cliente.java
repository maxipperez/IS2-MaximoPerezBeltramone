package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente {
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
  @Column(nullable = false, unique = true)
  private String dni;

  private String telefono;
  private boolean eliminado = false;

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

  public String getDni() {
    return dni;
  }

  public void setDni(String v) {
    dni = v;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String v) {
    telefono = v;
  }

  public boolean isEliminado() {
    return eliminado;
  }

  public void setEliminado(boolean v) {
    eliminado = v;
  }
}
