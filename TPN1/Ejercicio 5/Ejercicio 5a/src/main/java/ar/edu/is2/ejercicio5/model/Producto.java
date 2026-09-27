package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank
  @Column(nullable = false)
  private String nombre;

  @Column(length = 500)
  private String descripcion;

  @NotNull @PositiveOrZero private Double precioUnitario;

  private boolean eliminado = false;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
  @JoinColumn(name = "stock_id")
  private Stock stock = new Stock();

  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String v) {
    nombre = v;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String v) {
    descripcion = v;
  }

  public Double getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(Double v) {
    precioUnitario = v;
  }

  public boolean isEliminado() {
    return eliminado;
  }

  public void setEliminado(boolean v) {
    eliminado = v;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria v) {
    categoria = v;
  }

  public Stock getStock() {
    return stock;
  }
}
