package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Factura {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String idFactura;

  @Column(nullable = false, unique = true)
  private Long nroFactura;

  private LocalDate fecha = LocalDate.now();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoFactura estado = EstadoFactura.EMITIDA;

  private double total;
  private boolean eliminado = false;

  @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Detalle> detalles = new ArrayList<>();

  public void agregarDetalle(Detalle detalle) {
    detalle.setFactura(this);
    detalles.add(detalle);
    calcularTotal();
  }

  public double calcularTotal() {
    total = detalles.stream().mapToDouble(Detalle::calcularSubtotal).sum();
    return total;
  }

  public abstract String getTipo();

  public abstract String getContraparte();

  public String getIdFactura() {
    return idFactura;
  }

  public Long getNroFactura() {
    return nroFactura;
  }

  public void setNroFactura(Long v) {
    nroFactura = v;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public EstadoFactura getEstado() {
    return estado;
  }

  public void setEstado(EstadoFactura v) {
    estado = v;
  }

  public double getTotal() {
    return total;
  }

  public boolean isEliminado() {
    return eliminado;
  }

  public void setEliminado(boolean v) {
    eliminado = v;
  }

  public List<Detalle> getDetalles() {
    return detalles;
  }
}
