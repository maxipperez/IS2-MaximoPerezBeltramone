package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;

/** Subtipo de Usuario y propietario de un único carrito. */
@Entity
public class Cliente extends Usuario {
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "carrito_id")
  private CarritoCompra carrito;

  public CarritoCompra getCarrito() {
    return carrito;
  }

  public void setCarrito(CarritoCompra v) {
    carrito = v;
  }
}
