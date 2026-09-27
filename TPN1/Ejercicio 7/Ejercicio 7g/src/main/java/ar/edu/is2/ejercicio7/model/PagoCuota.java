package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"grupo_familiar_id", "periodo"}))
public class PagoCuota {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private GrupoFamiliar grupoFamiliar;

  @Column(nullable = false)
  private String periodo;

  @Column(nullable = false)
  private Double monto;

  @Column(nullable = false)
  private LocalDate fechaPago = LocalDate.now();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MedioPago medioPago;

  public Long getId() {
    return id;
  }

  public GrupoFamiliar getGrupoFamiliar() {
    return grupoFamiliar;
  }

  public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
    this.grupoFamiliar = grupoFamiliar;
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

  public MedioPago getMedioPago() {
    return medioPago;
  }

  public void setMedioPago(MedioPago medioPago) {
    this.medioPago = medioPago;
  }
}
