package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.CategoriaDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.Categoria;
import ar.edu.is2.ejercicio5.model.Producto;
import ar.edu.is2.ejercicio5.repository.CategoriaRepository;
import ar.edu.is2.ejercicio5.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaService {
  private final CategoriaRepository repo;
  private final ProductoRepository productoRepo;

  public CategoriaService(CategoriaRepository repo, ProductoRepository productoRepo) {
    this.repo = repo;
    this.productoRepo = productoRepo;
  }

  @Transactional(readOnly = true)
  public List<CategoriaDTO> listarCategorias() {
    return repo.findByEliminadoFalseOrderByNombre().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public CategoriaDTO buscarPorId(String id) {
    return aDTO(entidad(id));
  }

  public CategoriaDTO crearCategoria(CategoriaDTO dto) {
    validar(dto);
    if (repo.existsByNombreIgnoreCaseAndEliminadoFalse(dto.getNombre().trim()))
      throw new ErrorService("Ya existe una categoría con ese nombre.");
    Categoria c = new Categoria();
    c.setNombre(dto.getNombre().trim());
    return aDTO(repo.save(c));
  }

  public CategoriaDTO modificarCategoria(String id, CategoriaDTO dto) {
    validar(dto);
    Categoria c = entidad(id);
    if (!c.getNombre().equalsIgnoreCase(dto.getNombre().trim())
        && repo.existsByNombreIgnoreCaseAndEliminadoFalse(dto.getNombre().trim()))
      throw new ErrorService("Ya existe una categoría con ese nombre.");
    c.setNombre(dto.getNombre().trim());
    return aDTO(repo.save(c));
  }

  public void eliminarCategoria(String id) {
    Categoria c = entidad(id);
    for (Producto p : productoRepo.findByCategoriaId(id)) p.setCategoria(null);
    c.setEliminado(true);
    repo.save(c);
  }

  private Categoria entidad(String id) {
    return repo.findById(id)
        .filter(c -> !c.isEliminado())
        .orElseThrow(() -> new ErrorService("Categoría inexistente."));
  }

  private void validar(CategoriaDTO dto) {
    if (dto.getNombre() == null || dto.getNombre().isBlank())
      throw new ErrorService("Complete los campos obligatorios");
  }

  private CategoriaDTO aDTO(Categoria c) {
    int cantidad =
        (int) productoRepo.findByCategoriaId(c.getId()).stream().filter(p -> !p.isEliminado()).count();
    return new CategoriaDTO(c.getId(), c.getNombre(), cantidad);
  }
}
