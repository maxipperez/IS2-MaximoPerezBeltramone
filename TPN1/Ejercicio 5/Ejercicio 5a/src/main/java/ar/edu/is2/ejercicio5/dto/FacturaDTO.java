package ar.edu.is2.ejercicio5.dto;

import java.time.LocalDate;

public class FacturaDTO {
  private String id;
  private String tipo;
  private Long nroFactura;
  private LocalDate fecha;
  private String contraparte;
  private double total;
  private String estado;

  public FacturaDTO(
      String id,
      String tipo,
      Long nroFactura,
      LocalDate fecha,
      String contraparte,
      double total,
      String estado) {
    this.id = id;
    this.tipo = tipo;
    this.nroFactura = nroFactura;
    this.fecha = fecha;
    this.contraparte = contraparte;
    this.total = total;
    this.estado = estado;
  }

  public String getId() {
    return id;
  }

  public String getTipo() {
    return tipo;
  }

  public Long getNroFactura() {
    return nroFactura;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public String getContraparte() {
    return contraparte;
  }

  public double getTotal() {
    return total;
  }

  public String getEstado() {
    return estado;
  }
}
