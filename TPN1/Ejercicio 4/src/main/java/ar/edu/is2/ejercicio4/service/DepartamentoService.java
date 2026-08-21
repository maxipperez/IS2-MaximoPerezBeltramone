package ar.edu.is2.ejercicio4.service;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.Departamento;
import ar.edu.is2.ejercicio4.repository.DepartamentoRepository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio transaccional que encapsula el ABM lógico de departamentos.
 *
 * <p>{@code @Service} identifica la capa que aplica reglas del dominio y {@code @Transactional}
 * asegura consistencia entre leer, validar y guardar. Las consultas marcadas como solo lectura no
 * modifican el estado de la base.
 */
@Service
@Transactional
public class DepartamentoService {
  private final DepartamentoRepository repo;

  public DepartamentoService(DepartamentoRepository r) {
    repo = r;
  }

  /** Recupera solo departamentos activos para no exponer bajas lógicas. */
  @Transactional(readOnly = true)
  public List<Departamento> listar() {
    return repo.findByEliminadoFalse();
  }

  /** Verifica el objeto y crea un departamento. */
  public Departamento guardar(Departamento d) {
    validar(d);
    return repo.save(d);
  }

  /** Actualiza nombre y ubicación del registro encontrado por id. */
  public Departamento modificar(String id, Departamento d) {
    validar(d);
    Departamento a = buscar(id);
    a.setNombre(d.getNombre());
    a.setUbicacion(d.getUbicacion());
    return repo.save(a);
  }

  /** Establece el flag eliminado, conservando la información de auditoría. */
  public void eliminar(String id) {
    Departamento d = buscar(id);
    d.setEliminado(true);
    repo.save(d);
  }

  /** Obtiene la entidad o genera ErrorService si no existe. */
  @Transactional(readOnly = true)
  public Departamento buscar(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Departamento inexistente."));
  }

  /** Comprueba que nombre y ubicación no sean nulos, vacíos ni espacios. */
  private void validar(Departamento d) {
    if (d == null || vacio(d.getNombre()) || vacio(d.getUbicacion()))
      throw new ErrorService("Nombre y ubicación son obligatorios.");
  }

  /** Devuelve true para cadenas nulas o formadas solo por espacios. */
  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }
}
