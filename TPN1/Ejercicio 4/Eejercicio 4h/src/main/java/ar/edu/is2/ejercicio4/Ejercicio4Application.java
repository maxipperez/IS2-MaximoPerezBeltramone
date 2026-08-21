package ar.edu.is2.ejercicio4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada. @SpringBootApplication reúne configuración, auto-configuración y escaneo de
 * componentes.
 */
@SpringBootApplication
public class Ejercicio4Application {
  /** Inicia el contenedor Spring y el servidor MVC embebido. */
  public static void main(String[] args) {
    SpringApplication.run(Ejercicio4Application.class, args);
  }
}
