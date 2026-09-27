package ar.edu.is2.ejercicio5.dto;

public class CategoriaDTO {
  private String id;
  private String nombre;
  private int cantidadProductos;

  public CategoriaDTO() {}

  public CategoriaDTO(String id, String nombre, int cantidadProductos) {
    this.id = id;
    this.nombre = nombre;
    this.cantidadProductos = cantidadProductos;
  }

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

  public int getCantidadProductos() {
    return cantidadProductos;
  }

  public void setCantidadProductos(int v) {
    cantidadProductos = v;
  }
}
