package ar.edu.is2.ejercicio7.dto;

import ar.edu.is2.ejercicio7.model.MedioPago;
import java.time.LocalDate;

public class PagoDTO {
  private Long id;
  private Long grupoId;
  private String grupo;
  private String periodo;
  private Double monto;
  private LocalDate fechaPago;
  private MedioPago medioPago;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGrupoId() {
    return grupoId;
  }

  public void setGrupoId(Long grupoId) {
    this.grupoId = grupoId;
  }

  public String getGrupo() {
    return grupo;
  }

  public void setGrupo(String grupo) {
    this.grupo = grupo;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public Double getMonto() {
    return monto;
  }

  public void setMonto(Double monto) {
    this.monto = monto;
  }

  public LocalDate getFechaPago() {
    return fechaPago;
  }

  public void setFechaPago(LocalDate fechaPago) {
    this.fechaPago = fechaPago;
  }

  public MedioPago getMedioPago() {
    return medioPago;
  }

  public void setMedioPago(MedioPago medioPago) {
    this.medioPago = medioPago;
  }
}
