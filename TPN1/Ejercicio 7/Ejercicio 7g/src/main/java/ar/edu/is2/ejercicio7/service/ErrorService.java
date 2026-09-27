package ar.edu.is2.ejercicio7.service;

public class ErrorService extends RuntimeException {
  public ErrorService(String mensaje) {
    super(mensaje);
  }
}
