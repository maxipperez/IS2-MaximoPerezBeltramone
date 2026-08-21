package com.ejercicio4.mvc.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entidad que representa un Carrito de Compra (estado temporal de los productos elegidos).
 */
@Entity
public class CarritoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Temporal(TemporalType.TIMESTAMP) // Almacena fecha y hora en BD
    private Date fechaCreacion;

    /**
     * @OneToMany: Un carrito contiene múltiples 'ItemCarrito'.
     * mappedBy = "carrito": Relación bidireccional mapeada por la clase hija (ItemCarrito).
     * cascade = CascadeType.ALL: Operaciones como guardar un carrito, guardarán automáticamente sus ítems.
     * orphanRemoval = true: Si un ítem es eliminado de esta lista (List), JPA lo eliminará también de la tabla en BD.
     */
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    public CarritoCompra() {}

    public void agregarProducto(Producto p, int cantidad) {
        // Lógica de dominio para agregar ítems
    }

    public void quitarProducto(Producto p) {
        // Lógica de dominio para remover ítems
    }

    public double calcularTotal() {
        return items.stream().mapToDouble(i -> i.getCantidad() * i.getPrecioUnitario()).sum();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public List<ItemCarrito> getItems() { return items; }
    public void setItems(List<ItemCarrito> items) { this.items = items; }
}
