package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/** Línea que modela producto, cantidad y precio histórico dentro de un carrito. */
@Entity
public class ItemCarrito {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Positive private int cantidad;
  @NotNull @PositiveOrZero private Double precioUnitario;

  @ManyToOne
  @JoinColumn(name = "carrito_id")
  private CarritoCompra carrito;

  @ManyToOne
  @JoinColumn(name = "producto_id")
  private Producto producto;

  public String getId() {
    return id;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int v) {
    cantidad = v;
  }

  public Double getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(Double v) {
    precioUnitario = v;
  }

  public CarritoCompra getCarrito() {
    return carrito;
  }

  public void setCarrito(CarritoCompra v) {
    carrito = v;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto v) {
    producto = v;
  }
}
