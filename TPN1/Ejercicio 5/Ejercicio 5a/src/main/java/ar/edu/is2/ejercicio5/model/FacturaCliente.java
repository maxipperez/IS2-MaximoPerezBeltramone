package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;

@Entity
public class FacturaCliente extends Factura {
  @ManyToOne(optional = false)
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  public void disminuirStock() {
    for (Detalle d : getDetalles()) d.getProducto().getStock().eliminarStock(d.getCantidad());
  }

  public void reponerStock() {
    for (Detalle d : getDetalles()) d.getProducto().getStock().agregarStock(d.getCantidad());
  }

  @Override
  public String getTipo() {
    return "Venta";
  }

  @Override
  public String getContraparte() {
    return cliente.getNombre() + " " + cliente.getApellido();
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente v) {
    cliente = v;
  }
}
