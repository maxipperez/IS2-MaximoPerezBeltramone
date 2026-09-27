package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;

@Entity
public class FacturaProveedor extends Factura {
  @ManyToOne(optional = false)
  @JoinColumn(name = "proveedor_id")
  private Proveedor proveedor;

  @OneToOne(optional = false)
  @JoinColumn(name = "orden_compra_id", unique = true)
  private OrdenCompra ordenCompra;

  public void incrementarStock() {
    for (Detalle d : getDetalles()) d.getProducto().getStock().agregarStock(d.getCantidad());
  }

  @Override
  public String getTipo() {
    return "Compra";
  }

  @Override
  public String getContraparte() {
    return proveedor.getRazonSocial();
  }

  public Proveedor getProveedor() {
    return proveedor;
  }

  public void setProveedor(Proveedor v) {
    proveedor = v;
  }

  public OrdenCompra getOrdenCompra() {
    return ordenCompra;
  }

  public void setOrdenCompra(OrdenCompra v) {
    ordenCompra = v;
  }
}
