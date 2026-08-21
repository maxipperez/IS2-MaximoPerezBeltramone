package ar.edu.is2.ejercicio4.service;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.*;
import ar.edu.is2.ejercicio4.repository.ClienteRepository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de Cliente. La baja cambia el enum a ELIMINADO, conforme a la jerarquía Usuario.
 *
 * <p>{@code @Service} declara una pieza de lógica de negocio inyectable. {@code @Transactional}
 * protege las operaciones de alta, modificación y baja como una única unidad de trabajo. El modo
 * {@code readOnly} usado en consultas comunica que no habrá modificaciones.
 */
@Service
@Transactional
public class ClienteService {
  private final ClienteRepository repo;

  public ClienteService(ClienteRepository r) {
    repo = r;
  }

  /** Obtiene clientes activos; el repositorio excluye el estado ELIMINADO. */
  @Transactional(readOnly = true)
  public List<Cliente> listar() {
    return repo.findByEstadoNot(EstadoUsuario.ELIMINADO);
  }

  /** Valida, crea el carrito faltante, activa al cliente y lo persiste. */
  public Cliente guardar(Cliente c) {
    validar(c);
    if (c.getCarrito() == null) c.setCarrito(new CarritoCompra());
    c.setEstado(EstadoUsuario.ACTIVO);
    return repo.save(c);
  }

  /** Valida y transfiere los campos permitidos al cliente ya persistido. */
  public Cliente modificar(String id, Cliente c) {
    validar(c);
    Cliente a = buscar(id);
    copiar(a, c);
    return repo.save(a);
  }

  /** Realiza la baja lógica mediante EstadoUsuario.ELIMINADO. */
  public void eliminar(String id) {
    Cliente c = buscar(id);
    c.setEstado(EstadoUsuario.ELIMINADO);
    repo.save(c);
  }

  /** Busca por id y transforma una ausencia en ErrorService con un mensaje útil. */
  @Transactional(readOnly = true)
  public Cliente buscar(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Cliente inexistente."));
  }

  /** Copia datos editables sin alterar id, estado, historial ni carrito existentes. */
  private void copiar(Cliente a, Cliente c) {
    a.setDocumento(c.getDocumento());
    a.setNombre(c.getNombre());
    a.setApellido(c.getApellido());
    a.setFechaNacimiento(c.getFechaNacimiento());
    a.setCorreoPersonal(c.getCorreoPersonal());
    a.setPassword(c.getPassword());
  }

  /** Exige los atributos clave definidos por el requerimiento antes de guardar. */
  private void validar(Cliente c) {
    if (c == null
        || c.getDocumento() == null
        || vacio(c.getNombre())
        || vacio(c.getCorreoPersonal())
        || vacio(c.getPassword()))
      throw new ErrorService("Documento, nombre, correo y contraseña son obligatorios.");
  }

  /** Reutiliza la comprobación de textos vacíos en las reglas de negocio. */
  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }
}
