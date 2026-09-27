package ar.edu.is2.ejercicio5.dto;

public class ProductoDTO {
  private String id;
  private String nombre;
  private String descripcion;
  private Double precioUnitario;
  private String categoriaId;
  private String categoriaNombre;
  private int stock;
  private int stockMaximo;
  private String estadoStock;

  public String getId() {
    return id;
  }

  public void setId(String v) {
    id = v;
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

  public String getCategoriaId() {
    return categoriaId;
  }

  public void setCategoriaId(String v) {
    categoriaId = v;
  }

  public String getCategoriaNombre() {
    return categoriaNombre;
  }

  public void setCategoriaNombre(String v) {
    categoriaNombre = v;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int v) {
    stock = v;
  }

  public int getStockMaximo() {
    return stockMaximo;
  }

  public void setStockMaximo(int v) {
    stockMaximo = v;
  }

  public String getEstadoStock() {
    return estadoStock;
  }

  public void setEstadoStock(String v) {
    estadoStock = v;
  }
}
