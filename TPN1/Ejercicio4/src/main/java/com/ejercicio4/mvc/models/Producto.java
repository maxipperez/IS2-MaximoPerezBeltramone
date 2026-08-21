package com.ejercicio4.mvc.models;

import jakarta.persistence.*;

/**
 * Entidad que representa un Producto disponible para la venta.
 */
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stock;

    /**
     * Atributo para implementar el Eliminado Lógico (Soft Delete).
     * En bases de datos de eCommerce no se recomienda eliminar registros (DELETE FROM...)
     * porque rompería el historial de compras y carritos pasados. 
     * Se marca este flag en 'true' y los repositorios solo devuelven los 'eliminado = false'.
     */
    @Column(nullable = false)
    private boolean eliminado = false;

    public Producto() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
}
