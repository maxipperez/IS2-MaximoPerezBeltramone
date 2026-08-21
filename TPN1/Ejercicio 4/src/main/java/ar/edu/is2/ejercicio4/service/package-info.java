/**
 * Capa de reglas de negocio.
 *
 * <p>Los servicios anotados con {@code @Service} coordinan validaciones y repositorios. La
 * anotación {@code @Transactional} delimita una transacción: las operaciones de escritura son
 * atómicas y las lecturas se marcan con {@code readOnly = true} para reflejar su intención.
 */
package ar.edu.is2.ejercicio4.service;
