package ar.edu.is2.ejercicio5.dto;

public class ProveedorDTO {
  private String id;
  private String cuit;
  private String razonSocial;
  private String telefono;
  private boolean activo = true;

  public String getId() {
    return id;
  }

  public void setId(String v) {
    id = v;
  }

  public String getCuit() {
    return cuit;
  }

  public void setCuit(String v) {
    cuit = v;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String v) {
    razonSocial = v;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String v) {
    telefono = v;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean v) {
    activo = v;
  }
}
