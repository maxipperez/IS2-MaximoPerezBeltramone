package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class DetalleOrdenCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Positive private int cantidad;
  @PositiveOrZero private double precioUnitario;
  private double subtotal;

  @ManyToOne(optional = false)
  @JoinColumn(name = "producto_id")
  private Producto producto;

  @ManyToOne(optional = false)
  @JoinColumn(name = "orden_compra_id")
  private OrdenCompra ordenCompra;

  protected DetalleOrdenCompra() {}

  public DetalleOrdenCompra(Producto producto, int cantidad, double precioUnitario) {
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

  public Producto getProducto() {
    return producto;
  }

  public OrdenCompra getOrdenCompra() {
    return ordenCompra;
  }

  void setOrdenCompra(OrdenCompra v) {
    ordenCompra = v;
  }
}
