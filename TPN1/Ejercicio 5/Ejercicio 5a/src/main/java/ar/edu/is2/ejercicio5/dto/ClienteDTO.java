package ar.edu.is2.ejercicio5.dto;

public class ClienteDTO {
  private String id;
  private String nombre;
  private String apellido;
  private String dni;
  private String telefono;

  public String getNombreCompleto() {
    return nombre + " " + apellido;
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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String v) {
    apellido = v;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String v) {
    dni = v;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String v) {
    telefono = v;
  }
}
