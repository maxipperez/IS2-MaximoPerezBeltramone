package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
public class Stock {
  public static final int STOCK_BAJO = 20;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @PositiveOrZero private int cantidad = 0;
  @PositiveOrZero private int stockMaximo = 0;
  private LocalDateTime actualizacion = LocalDateTime.now();

  public void agregarStock(int cantidad) {
    this.cantidad += cantidad;
    actualizacion = LocalDateTime.now();
  }

  public void eliminarStock(int cantidad) {
    this.cantidad -= cantidad;
    actualizacion = LocalDateTime.now();
  }

  public void modificarStock(int cantidad, int stockMaximo) {
    this.cantidad = cantidad;
    this.stockMaximo = stockMaximo;
    actualizacion = LocalDateTime.now();
  }

  public int consultarStock() {
    return cantidad;
  }

  public String getEstado() {
    if (cantidad <= 0) return "Sin stock";
    if (cantidad <= STOCK_BAJO) return "Stock bajo";
    return "Óptimo";
  }

  public String getId() {
    return id;
  }

  public int getCantidad() {
    return cantidad;
  }

  public int getStockMaximo() {
    return stockMaximo;
  }

  public LocalDateTime getActualizacion() {
    return actualizacion;
  }
}
