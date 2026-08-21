package ar.edu.is2.ejercicio4.exception;

/** Excepción de dominio que comunica reglas de negocio inválidas a los controladores MVC. */
public class ErrorService extends RuntimeException {
  public ErrorService(String mensaje) {
    super(mensaje);
  }
}
