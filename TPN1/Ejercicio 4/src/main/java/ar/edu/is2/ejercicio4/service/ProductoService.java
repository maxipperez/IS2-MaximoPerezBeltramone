package ar.edu.is2.ejercicio4.service;

import ar.edu.is2.ejercicio4.exception.ErrorService;
import ar.edu.is2.ejercicio4.model.Producto;
import ar.edu.is2.ejercicio4.repository.ProductoRepository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Service registra la capa de reglas de negocio; @Transactional hace atómicas sus operaciones de
 * escritura.
 */
@Service
@Transactional
public class ProductoService {
  private final ProductoRepository repo;

  public ProductoService(ProductoRepository r) {
    repo = r;
  }

  /** Lista exclusivamente productos vigentes. */
  @Transactional(readOnly = true)
  public List<Producto> listar() {
    return repo.findByEliminadoFalse();
  }

  /** Valida y persiste una alta. */
  public Producto guardar(Producto p) {
    validar(p);
    return repo.save(p);
  }

  /** Actualiza campos permitidos manteniendo el identificador y el estado de baja anterior. */
  public Producto modificar(String id, Producto p) {
    validar(p);
    Producto actual = buscar(id);
    actual.setNombre(p.getNombre());
    actual.setPrecio(p.getPrecio());
    actual.setStock(p.getStock());
    return repo.save(actual);
  }

  /** Marca como eliminado; nunca ejecuta deleteById. */
  public void eliminar(String id) {
    Producto p = buscar(id);
    p.setEliminado(true);
    repo.save(p);
  }

  /** Busca por id o informa un ErrorService claro a la vista. */
  @Transactional(readOnly = true)
  public Producto buscar(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Producto inexistente."));
  }

  /** Centraliza las validaciones estrictas de campos clave. */
  private void validar(Producto p) {
    if (p == null || vacio(p.getNombre()))
      throw new ErrorService("El nombre del producto es obligatorio.");
    if (p.getPrecio() == null || p.getPrecio() < 0)
      throw new ErrorService("El precio debe ser válido.");
    if (p.getStock() == null || p.getStock() < 0)
      throw new ErrorService("El stock debe ser válido.");
  }

  private boolean vacio(String s) {
    return s == null || s.isBlank();
  }
}
