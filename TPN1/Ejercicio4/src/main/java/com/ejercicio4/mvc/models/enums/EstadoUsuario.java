package com.ejercicio4.mvc.models.enums;

/**
 * Enumeración que representa los distintos estados en los que puede encontrarse un Usuario.
 * Se utiliza para el control de acceso y el eliminado lógico.
 */
public enum EstadoUsuario {
    ACTIVO,
    PENDIENTE_REGISTRO,
    BLOQUEADO_TEMPORALMENTE,
    ELIMINADO
}
