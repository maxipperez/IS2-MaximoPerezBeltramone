package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

/** Área que agrupa administradores. eliminado evita borrar datos físicamente. */
@Entity
public class Departamento {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank private String nombre;
  @NotBlank private String ubicacion;
  private boolean eliminado = false;

  @OneToMany(mappedBy = "departamento")
  private List<Administrador> administradores = new ArrayList<>();

  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String v) {
    nombre = v;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String v) {
    ubicacion = v;
  }

  public boolean isEliminado() {
    return eliminado;
  }

  public void setEliminado(boolean v) {
    eliminado = v;
  }

  public List<Administrador> getAdministradores() {
    return administradores;
  }
}
