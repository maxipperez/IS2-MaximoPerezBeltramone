package com.ejercicio4.mvc.services;

import com.ejercicio4.mvc.exceptions.ErrorService;
import com.ejercicio4.mvc.models.Cliente;
import com.ejercicio4.mvc.models.enums.EstadoUsuario;
import com.ejercicio4.mvc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Clase que gestiona la lógica de negocio (ABM) para la entidad Cliente.
 * 
 * @Service: Permite a Spring detectar e instanciar este componente en el contexto (IoC).
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void crearCliente(int documento, String nombre, String apellido, String correo, String password) throws ErrorService {
        validarNombre(nombre);

        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCorreoPersonal(correo);
        cliente.setPassword(password); // ATENCIÓN: En una app real esto debe ser encriptado con BCrypt.
        
        // Configuramos valores por defecto (ej. al nacer un cliente, su estado es ACTIVO).
        cliente.setEstado(EstadoUsuario.ACTIVO);
        cliente.setIntentosFallidos(0);
        cliente.setFechaNacimiento(new Date()); 
        
        clienteRepository.save(cliente); // INSERT en SQL
    }

    @Transactional
    public void modificarCliente(String id, String nombre, String apellido) throws ErrorService {
        validarNombre(nombre);

        Optional<Cliente> respuesta = clienteRepository.findById(id);
        
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            
            clienteRepository.save(cliente); // UPDATE en SQL
        } else {
            throw new ErrorService("El cliente que intentas modificar no existe en la base de datos.");
        }
    }

    /**
     * Eliminado lógico del Cliente.
     * Como Cliente hereda de Usuario, su borrado lógico se gestiona a través del Enum EstadoUsuario.
     */
    @Transactional
    public void eliminarCliente(String id) throws ErrorService {
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setEstado(EstadoUsuario.ELIMINADO); // <--- Baja lógica (Cambio de estado)
            clienteRepository.save(cliente);
        } else {
            throw new ErrorService("El cliente que intentas eliminar no existe.");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientesActivos() {
        // En un escenario de gran escala, este filtrado se haría en la BD con un Query Method.
        // Aquí demostramos cómo hacerlo en la capa de servicio combinando el Repository y la API Stream.
        return clienteRepository.findAll().stream()
                .filter(cliente -> cliente.getEstado() != EstadoUsuario.ELIMINADO)
                .toList();
    }

    /**
     * Valida que el nombre de usuario cumpla con los requisitos mínimos de nuestro negocio.
     */
    private void validarNombre(String nombre) throws ErrorService {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorService("Error de Validación: El nombre del cliente no puede ser nulo, ni estar vacío, ni contener solo espacios.");
        }
    }
}
