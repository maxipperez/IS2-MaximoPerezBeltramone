package ar.edu.is2.ejercicio4.service;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.*;
import ar.edu.is2.ejercicio4.repository.AdministradorRepository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de negocio para Administrador y sus datos heredados de Usuario.
 *
 * <p>{@code @Service} registra esta clase como componente de la capa de negocio.
 * {@code @Transactional} agrupa cada escritura en una transacción: se confirman todos los cambios o
 * se revierten ante un error. Las lecturas usan {@code readOnly = true} para indicar que no alteran
 * la persistencia.
 */
@Service
@Transactional
public class AdministradorService {
  private final AdministradorRepository repo;

  public AdministradorService(AdministradorRepository r) {
    repo = r;
  }

  /** Devuelve administradores vigentes, filtrando el estado ELIMINADO. */
  @Transactional(readOnly = true)
  public List<Administrador> listar() {
    return repo.findAll().stream().filter(a -> a.getEstado() != EstadoUsuario.ELIMINADO).toList();
  }

  /** Valida campos obligatorios, asigna estado ACTIVO y persiste una nueva alta. */
  public Administrador guardar(Administrador a) {
    validar(a);
    a.setEstado(EstadoUsuario.ACTIVO);
    return repo.save(a);
  }

  /** Copia los datos editables sobre la entidad persistida y luego la guarda. */
  public Administrador modificar(String id, Administrador a) {
    validar(a);
    Administrador x = buscar(id);
    x.setDocumento(a.getDocumento());
    x.setNombre(a.getNombre());
    x.setApellido(a.getApellido());
    x.setFechaNacimiento(a.getFechaNacimiento());
    x.setCorreoPersonal(a.getCorreoPersonal());
    x.setPassword(a.getPassword());
    x.setDepartamento(a.getDepartamento());
    return repo.save(x);
  }

  /** Implementa la baja lógica cambiando el estado; nunca borra físicamente la fila. */
  public void eliminar(String id) {
    Administrador a = buscar(id);
    a.setEstado(EstadoUsuario.ELIMINADO);
    repo.save(a);
  }

  /** Busca por identificador o lanza ErrorService para que el controlador informe el problema. */
  @Transactional(readOnly = true)
  public Administrador buscar(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Administrador inexistente."));
  }

  /** Centraliza reglas de campos clave: documento, nombre, correo y contraseña. */
  private void validar(Administrador a) {
    if (a == null
        || a.getDocumento() == null
        || vacio(a.getNombre())
        || vacio(a.getCorreoPersonal())
        || vacio(a.getPassword()))
      throw new ErrorService("Documento, nombre, correo y contraseña son obligatorios.");
  }

  /** Determina si un texto no contiene información válida. */
  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }
}
