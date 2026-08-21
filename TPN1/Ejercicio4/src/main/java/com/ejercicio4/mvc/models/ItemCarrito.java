package com.ejercicio4.mvc.models;

import jakarta.persistence.*;

/**
 * Entidad intermedia que representa una cantidad específica de un Producto dentro de un Carrito.
 */
@Entity
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double precioUnitario;

    /**
     * @ManyToOne: Varios ítems pertenecen a un único carrito.
     * @JoinColumn: Especifica la clave foránea en BD hacia el carrito.
     */
    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private CarritoCompra carrito;

    /**
     * @ManyToOne: Varios ítems (incluso de distintos carritos) pueden apuntar al mismo Producto.
     * @JoinColumn: Especifica la clave foránea en BD hacia el producto.
     */
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public ItemCarrito() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public CarritoCompra getCarrito() { return carrito; }
    public void setCarrito(CarritoCompra carrito) { this.carrito = carrito; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}
