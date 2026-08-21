package com.ejercicio4.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicializa la aplicación Spring Boot.
 * 
 * @SpringBootApplication: Es una anotación clave de Spring Boot. 
 *                         Equivale a usar @Configuration, @EnableAutoConfiguration y @ComponentScan a la vez. 
 *                         Le dice a Spring que escanee todos los @Controller, @Service y @Repository 
 *                         dentro de este paquete base (com.ejercicio4.mvc) y sus subpaquetes.
 */
@SpringBootApplication
public class Ejercicio4Application {

    public static void main(String[] args) {
        // Este método arranca el servidor web embebido (Tomcat por defecto) y carga toda la configuración de Spring.
        SpringApplication.run(Ejercicio4Application.class, args);
    }

}
