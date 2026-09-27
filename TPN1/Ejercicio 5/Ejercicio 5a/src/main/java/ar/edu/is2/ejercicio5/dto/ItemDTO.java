package ar.edu.is2.ejercicio5.dto;

public class ItemDTO {
  private String productoId;
  private String productoNombre;
  private int stockDisponible;
  private Integer cantidad;
  private Double precioUnitario;
  private double subtotal;

  public ItemDTO() {}

  public ItemDTO(
      String productoId,
      String productoNombre,
      int stockDisponible,
      Integer cantidad,
      Double precioUnitario) {
    this.productoId = productoId;
    this.productoNombre = productoNombre;
    this.stockDisponible = stockDisponible;
    this.cantidad = cantidad;
    this.precioUnitario = precioUnitario;
    if (cantidad != null && precioUnitario != null) subtotal = cantidad * precioUnitario;
  }

  public boolean isSeleccionado() {
    return cantidad != null && cantidad > 0;
  }

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

  public int getStockDisponible() {
    return stockDisponible;
  }

  public void setStockDisponible(int v) {
    stockDisponible = v;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer v) {
    cantidad = v;
  }

  public Double getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(Double v) {
    precioUnitario = v;
  }

  public double getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(double v) {
    subtotal = v;
  }
}
