package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.ProductoDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.Categoria;
import ar.edu.is2.ejercicio5.model.EstadoOrden;
import ar.edu.is2.ejercicio5.model.Producto;
import ar.edu.is2.ejercicio5.repository.CategoriaRepository;
import ar.edu.is2.ejercicio5.repository.DetalleOrdenCompraRepository;
import ar.edu.is2.ejercicio5.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductoService {
  private final ProductoRepository repo;
  private final CategoriaRepository categoriaRepo;
  private final DetalleOrdenCompraRepository detalleOrdenRepo;

  public ProductoService(
      ProductoRepository repo,
      CategoriaRepository categoriaRepo,
      DetalleOrdenCompraRepository detalleOrdenRepo) {
    this.repo = repo;
    this.categoriaRepo = categoriaRepo;
    this.detalleOrdenRepo = detalleOrdenRepo;
  }

  @Transactional(readOnly = true)
  public List<ProductoDTO> listar() {
    return repo.findByEliminadoFalseOrderByNombre().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<ProductoDTO> buscar(String texto, String categoriaId) {
    return repo.buscarActivos(vacioANull(texto), vacioANull(categoriaId)).stream()
        .map(this::aDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public ProductoDTO buscarPorId(String id) {
    return aDTO(entidad(id));
  }

  public ProductoDTO crearProducto(ProductoDTO dto) {
    validar(dto);
    Producto p = new Producto();
    copiar(p, dto);
    p.getStock().modificarStock(0, valor(dto.getStockMaximo()));
    return aDTO(repo.save(p));
  }

  public ProductoDTO modificarProducto(String id, ProductoDTO dto) {
    Producto p = entidad(id);
    if (p.isEliminado()) throw new ErrorService("No es posible modificar un producto eliminado");
    validar(dto);
    copiar(p, dto);
    return aDTO(repo.save(p));
  }

  public void eliminarProducto(String id) {
    Producto p = entidad(id);
    if (detalleOrdenRepo.existsByProductoIdAndOrdenCompraEstado(id, EstadoOrden.PENDIENTE))
      throw new ErrorService("No es posible eliminar un producto con transacciones activas");
    p.setEliminado(true);
    repo.save(p);
  }

  private Producto entidad(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Producto inexistente."));
  }

  private void copiar(Producto p, ProductoDTO dto) {
    p.setNombre(dto.getNombre().trim());
    p.setDescripcion(dto.getDescripcion());
    p.setPrecioUnitario(dto.getPrecioUnitario());
    p.setCategoria(categoria(dto.getCategoriaId()));
  }

  private Categoria categoria(String id) {
    if (id == null || id.isBlank()) return null;
    return categoriaRepo
        .findById(id)
        .filter(c -> !c.isEliminado())
        .orElseThrow(() -> new ErrorService("Categoría inexistente."));
  }

  private void validar(ProductoDTO dto) {
    if (dto.getNombre() == null || dto.getNombre().isBlank() || dto.getPrecioUnitario() == null)
      throw new ErrorService("Complete los campos obligatorios");
    if (dto.getPrecioUnitario() < 0) throw new ErrorService("El precio no puede ser negativo.");
    if (dto.getStockMaximo() < 0) throw new ErrorService("El stock máximo no puede ser negativo.");
  }

  private int valor(Integer v) {
    return v == null ? 0 : v;
  }

  private String vacioANull(String s) {
    return s == null || s.isBlank() ? null : s.trim();
  }

  ProductoDTO aDTO(Producto p) {
    ProductoDTO dto = new ProductoDTO();
    dto.setId(p.getId());
    dto.setNombre(p.getNombre());
    dto.setDescripcion(p.getDescripcion());
    dto.setPrecioUnitario(p.getPrecioUnitario());
    if (p.getCategoria() != null && !p.getCategoria().isEliminado()) {
      dto.setCategoriaId(p.getCategoria().getId());
      dto.setCategoriaNombre(p.getCategoria().getNombre());
    }
    dto.setStock(p.getStock().getCantidad());
    dto.setStockMaximo(p.getStock().getStockMaximo());
    dto.setEstadoStock(p.getStock().getEstado());
    return dto;
  }
}
