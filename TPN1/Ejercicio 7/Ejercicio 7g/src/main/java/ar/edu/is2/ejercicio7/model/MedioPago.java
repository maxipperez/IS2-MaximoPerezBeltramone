package ar.edu.is2.ejercicio7.model;

public enum MedioPago {
  EFECTIVO("Efectivo"),
  TRANSFERENCIA("Transferencia"),
  MERCADO_PAGO("Mercado Pago");

  private final String etiqueta;

  MedioPago(String etiqueta) {
    this.etiqueta = etiqueta;
  }

  public String getEtiqueta() {
    return etiqueta;
  }
}
