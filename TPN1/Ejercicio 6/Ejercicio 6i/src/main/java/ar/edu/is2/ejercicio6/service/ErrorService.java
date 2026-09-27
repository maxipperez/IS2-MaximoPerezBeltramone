package ar.edu.is2.ejercicio6.service;

public class ErrorService extends RuntimeException {
  public ErrorService(String mensaje) {
    super(mensaje);
  }
}
