package ar.edu.is2.ejercicio7.dto;

public class FotoDTO {
  private final byte[] imagen;
  private final String mime;

  public FotoDTO(byte[] imagen, String mime) {
    this.imagen = imagen;
    this.mime = mime;
  }

  public byte[] getImagen() {
    return imagen;
  }

  public String getMime() {
    return mime;
  }
}
