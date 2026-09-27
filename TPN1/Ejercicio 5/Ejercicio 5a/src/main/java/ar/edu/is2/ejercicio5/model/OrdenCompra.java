package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrdenCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true)
  private Long numero;

  private LocalDate fecha = LocalDate.now();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoOrden estado = EstadoOrden.PENDIENTE;

  private double total;

  @ManyToOne(optional = false)
  @JoinColumn(name = "proveedor_id")
  private Proveedor proveedor;

  @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DetalleOrdenCompra> detalles = new ArrayList<>();

  public void agregarDetalle(DetalleOrdenCompra detalle) {
    detalle.setOrdenCompra(this);
    detalles.add(detalle);
    calcularTotal();
  }

  public void limpiarDetalles() {
    detalles.clear();
    total = 0;
  }

  public double calcularTotal() {
    total = detalles.stream().mapToDouble(DetalleOrdenCompra::calcularSubtotal).sum();
    return total;
  }

  public int cantidadTotal() {
    return detalles.stream().mapToInt(DetalleOrdenCompra::getCantidad).sum();
  }

  public void confirmarCompra() {
    estado = EstadoOrden.CONFIRMADA;
  }

  public void cancelarCompra() {
    estado = EstadoOrden.CANCELADA;
  }

  public String getId() {
    return id;
  }

  public Long getNumero() {
    return numero;
  }

  public void setNumero(Long v) {
    numero = v;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public EstadoOrden getEstado() {
    return estado;
  }

  public double getTotal() {
    return total;
  }

  public Proveedor getProveedor() {
    return proveedor;
  }

  public void setProveedor(Proveedor v) {
    proveedor = v;
  }

  public List<DetalleOrdenCompra> getDetalles() {
    return detalles;
  }
}
