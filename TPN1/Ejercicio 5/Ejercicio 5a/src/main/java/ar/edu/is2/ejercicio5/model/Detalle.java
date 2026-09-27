package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class Detalle {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Positive private int cantidad;
  private double precioUnitario;
  private double subtotal;
  private boolean eliminado = false;

  @ManyToOne(optional = false)
  @JoinColumn(name = "producto_id")
  private Producto producto;

  @ManyToOne(optional = false)
  @JoinColumn(name = "factura_id")
  private Factura factura;

  protected Detalle() {}

  public Detalle(Producto producto, int cantidad, double precioUnitario) {
    this.producto = producto;
    this.cantidad = cantidad;
    this.precioUnitario = precioUnitario;
    calcularSubtotal();
  }

  public double calcularSubtotal() {
    subtotal = cantidad * precioUnitario;
    return subtotal;
  }

  public String getId() {
    return id;
  }

  public int getCantidad() {
    return cantidad;
  }

  public double getPrecioUnitario() {
    return precioUnitario;
  }

  public double getSubtotal() {
    return subtotal;
  }

  public boolean isEliminado() {
    return eliminado;
  }

  public Producto getProducto() {
    return producto;
  }

  public Factura getFactura() {
    return factura;
  }

  void setFactura(Factura v) {
    factura = v;
  }
}
