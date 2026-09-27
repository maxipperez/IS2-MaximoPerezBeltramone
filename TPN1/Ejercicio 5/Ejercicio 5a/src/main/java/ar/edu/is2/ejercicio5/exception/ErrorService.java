package ar.edu.is2.ejercicio5.exception;

public class ErrorService extends RuntimeException {
  public ErrorService(String mensaje) {
    super(mensaje);
  }
}
