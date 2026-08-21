package com.ejercicio4.mvc.exceptions;

/**
 * Excepción personalizada para la capa de servicios.
 * 
 * ¿Por qué crear una excepción personalizada?
 * En el desarrollo de software, es fundamental separar las excepciones técnicas o genéricas del sistema 
 * (como NullPointerException, SQLException o DataAccessException) de las excepciones que ocurren por reglas de negocio 
 * (ej. "Stock insuficiente", "El usuario ya existe", "Cliente dado de baja").
 * 
 * Al heredar de Exception (que es de tipo Checked), forzamos a que cualquier controlador o clase 
 * que llame a un servicio que arroje ErrorService deba manejarlo obligatoriamente (con un bloque try-catch) 
 * o declararlo en su firma. Esto nos da un control robusto sobre el flujo de la aplicación.
 */
public class ErrorService extends Exception {

    /**
     * Constructor que recibe el mensaje de error específico de negocio.
     * Llama al constructor de la superclase (Exception) para que el mensaje quede registrado.
     * 
     * @param mensaje El texto descriptivo del error (ej. "No se puede eliminar un cliente bloqueado").
     */
    public ErrorService(String mensaje) {
        super(mensaje);
    }
}
