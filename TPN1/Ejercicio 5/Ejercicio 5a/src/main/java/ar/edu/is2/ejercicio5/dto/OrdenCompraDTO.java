package ar.edu.is2.ejercicio5.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompraDTO {
  private String id;
  private Long numero;
  private LocalDate fecha;
  private String estado;
  private double total;
  private int cantidadTotal;
  private String proveedorId;
  private String proveedorRazonSocial;
  private Long nroFacturaProveedor;
  private List<ItemDTO> items = new ArrayList<>();

  public String getCodigo() {
    return numero == null ? "" : "PO-" + numero;
  }

  public boolean isPendiente() {
    return "PENDIENTE".equals(estado);
  }

  public String getId() {
    return id;
  }

  public void setId(String v) {
    id = v;
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

  public void setFecha(LocalDate v) {
    fecha = v;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String v) {
    estado = v;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double v) {
    total = v;
  }

  public int getCantidadTotal() {
    return cantidadTotal;
  }

  public void setCantidadTotal(int v) {
    cantidadTotal = v;
  }

  public String getProveedorId() {
    return proveedorId;
  }

  public void setProveedorId(String v) {
    proveedorId = v;
  }

  public String getProveedorRazonSocial() {
    return proveedorRazonSocial;
  }

  public void setProveedorRazonSocial(String v) {
    proveedorRazonSocial = v;
  }

  public Long getNroFacturaProveedor() {
    return nroFacturaProveedor;
  }

  public void setNroFacturaProveedor(Long v) {
    nroFacturaProveedor = v;
  }

  public List<ItemDTO> getItems() {
    return items;
  }

  public void setItems(List<ItemDTO> v) {
    items = v;
  }
}
