package ar.edu.is2.ejercicio7.dto;

public class PersonaDTO {
  private final Long id;
  private final String nombre;
  private final String dni;
  private final String tipo;

  public PersonaDTO(Long id, String nombre, String dni, String tipo) {
    this.id = id;
    this.nombre = nombre;
    this.dni = dni;
    this.tipo = tipo;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDni() {
    return dni;
  }

  public String getTipo() {
    return tipo;
  }
}
