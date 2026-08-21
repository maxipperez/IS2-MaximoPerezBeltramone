package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

/** Carrito. @OneToMany con orphanRemoval elimina ítems que ya no pertenecen al carrito. */
@Entity
public class CarritoCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private LocalDate fechaCreacion = LocalDate.now();

  @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemCarrito> items = new ArrayList<>();

  public String getId() {
    return id;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(LocalDate v) {
    fechaCreacion = v;
  }

  public List<ItemCarrito> getItems() {
    return items;
  }

  /** Conserva ambos lados de la relación al agregar. */
  public void agregarProducto(Producto p, int cantidad) {
    ItemCarrito i = new ItemCarrito();
    i.setProducto(p);
    i.setCantidad(cantidad);
    i.setPrecioUnitario(p.getPrecio());
    i.setCarrito(this);
    items.add(i);
  }

  /** Suma subtotales de cada ítem. */
  public double calcularTotal() {
    return items.stream().mapToDouble(i -> i.getPrecioUnitario() * i.getCantidad()).sum();
  }
}
