package ar.edu.is2.ejercicio6.dto;

import java.time.LocalDateTime;

public class RevisionDTO {
  private final Integer numero;
  private final LocalDateTime fecha;
  private final String tipo;
  private final NotaDTO nota;

  public RevisionDTO(Integer numero, LocalDateTime fecha, String tipo, NotaDTO nota) {
    this.numero = numero;
    this.fecha = fecha;
    this.tipo = tipo;
    this.nota = nota;
  }

  public Integer getNumero() {
    return numero;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public String getTipo() {
    return tipo;
  }

  public NotaDTO getNota() {
    return nota;
  }
}
