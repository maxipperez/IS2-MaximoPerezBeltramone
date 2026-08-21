package com.ejercicio4.mvc.models;

import jakarta.persistence.*;

/**
 * Entidad que representa a un Cliente.
 * 
 * @Entity: Define a la clase como una entidad JPA.
 * @PrimaryKeyJoinColumn: Como usamos InheritanceType.JOINED en la clase padre (Usuario), 
 *                        esta anotación es buena práctica para nombrar explícitamente la columna 
 *                        que unirá la tabla 'cliente' con la tabla 'usuario'.
 */
@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {

    /**
     * @OneToOne: Relación uno a uno. Un cliente tiene asignado un único carrito de compras activo.
     * @JoinColumn: Especifica que en la tabla 'cliente' existirá una clave foránea (carrito_id) 
     *              hacia la tabla 'carrito_compra'.
     * cascade = CascadeType.ALL: Si se crea/elimina el Cliente, se hará lo mismo con su Carrito.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrito_id", referencedColumnName = "id")
    private CarritoCompra carrito;

    public Cliente() {}

    // Implementación del método abstracto heredado
    @Override
    public boolean validarDatos() {
        return true; 
    }

    public void bloquearCliente(String user) {}

    // Getters y Setters
    public CarritoCompra getCarrito() { return carrito; }
    public void setCarrito(CarritoCompra carrito) { this.carrito = carrito; }
}
