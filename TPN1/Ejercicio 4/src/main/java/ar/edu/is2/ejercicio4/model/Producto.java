package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/** Producto vendible. El flag eliminado implementa una baja recuperable. */
@Entity
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank private String nombre;
  @NotNull @PositiveOrZero private Double precio;
  @NotNull @PositiveOrZero private Integer stock;
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

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double v) {
    precio = v;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer v) {
    stock = v;
  }

  public boolean isEliminado() {
    return eliminado;
  }

  public void setEliminado(boolean v) {
    eliminado = v;
  }
}
