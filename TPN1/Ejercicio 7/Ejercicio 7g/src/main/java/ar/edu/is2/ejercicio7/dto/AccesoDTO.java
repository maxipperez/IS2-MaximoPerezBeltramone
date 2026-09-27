package ar.edu.is2.ejercicio7.dto;

import java.time.LocalDateTime;

public class AccesoDTO {
  private final Long personaId;
  private final String persona;
  private final String tipo;
  private final LocalDateTime entrada;
  private final LocalDateTime salida;
  private final Long minutos;

  public AccesoDTO(
      Long personaId, String persona, String tipo, LocalDateTime entrada, LocalDateTime salida, Long minutos) {
    this.personaId = personaId;
    this.persona = persona;
    this.tipo = tipo;
    this.entrada = entrada;
    this.salida = salida;
    this.minutos = minutos;
  }

  public Long getPersonaId() {
    return personaId;
  }

  public String getPersona() {
    return persona;
  }

  public String getTipo() {
    return tipo;
  }

  public LocalDateTime getEntrada() {
    return entrada;
  }

  public LocalDateTime getSalida() {
    return salida;
  }

  public Long getMinutos() {
    return minutos;
  }
}
