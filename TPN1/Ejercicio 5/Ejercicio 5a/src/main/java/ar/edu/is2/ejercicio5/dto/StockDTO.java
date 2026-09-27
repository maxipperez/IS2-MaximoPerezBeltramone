package ar.edu.is2.ejercicio5.dto;

import java.time.LocalDateTime;

public class StockDTO {
  private String productoId;
  private String productoNombre;
  private Integer cantidad;
  private Integer stockMaximo;
  private LocalDateTime actualizacion;
  private String estado;

  public String getProductoId() {
    return productoId;
  }

  public void setProductoId(String v) {
    productoId = v;
  }

  public String getProductoNombre() {
    return productoNombre;
  }

  public void setProductoNombre(String v) {
    productoNombre = v;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer v) {
    cantidad = v;
  }

  public Integer getStockMaximo() {
    return stockMaximo;
  }

  public void setStockMaximo(Integer v) {
    stockMaximo = v;
  }

  public LocalDateTime getActualizacion() {
    return actualizacion;
  }

  public void setActualizacion(LocalDateTime v) {
    actualizacion = v;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String v) {
    estado = v;
  }
}
