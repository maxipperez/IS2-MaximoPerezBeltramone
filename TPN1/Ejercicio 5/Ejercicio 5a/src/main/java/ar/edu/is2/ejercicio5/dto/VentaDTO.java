package ar.edu.is2.ejercicio5.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDTO {
  private String id;
  private Long nroFactura;
  private LocalDate fecha;
  private String estado;
  private double total;
  private String clienteId;
  private String clienteNombre;
  private String clienteDni;
  private List<ItemDTO> items = new ArrayList<>();

  public boolean isEmitida() {
    return "EMITIDA".equals(estado);
  }

  public String getId() {
    return id;
  }

  public void setId(String v) {
    id = v;
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

  public String getClienteId() {
    return clienteId;
  }

  public void setClienteId(String v) {
    clienteId = v;
  }

  public String getClienteNombre() {
    return clienteNombre;
  }

  public void setClienteNombre(String v) {
    clienteNombre = v;
  }

  public String getClienteDni() {
    return clienteDni;
  }

  public void setClienteDni(String v) {
    clienteDni = v;
  }

  public List<ItemDTO> getItems() {
    return items;
  }

  public void setItems(List<ItemDTO> v) {
    items = v;
  }
}
