package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.StockDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.Producto;
import ar.edu.is2.ejercicio5.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockService {
  private final ProductoRepository productoRepo;

  public StockService(ProductoRepository productoRepo) {
    this.productoRepo = productoRepo;
  }

  @Transactional(readOnly = true)
  public List<StockDTO> listarStock() {
    return productoRepo.findByEliminadoFalseOrderByNombre().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public StockDTO buscarPorProducto(String productoId) {
    return aDTO(producto(productoId));
  }

  public StockDTO modificarStock(String productoId, StockDTO dto) {
    Producto p = producto(productoId);
    if (p.isEliminado()) throw new ErrorService("No es posible modificar un producto eliminado");
    if (dto.getCantidad() == null || dto.getStockMaximo() == null)
      throw new ErrorService("Complete los campos obligatorios");
    if (dto.getCantidad() < 0 || dto.getStockMaximo() < 0)
      throw new ErrorService("Las cantidades no pueden ser negativas.");
    if (dto.getStockMaximo() > 0 && dto.getCantidad() > dto.getStockMaximo())
      throw new ErrorService("La cantidad no puede superar el stock máximo.");
    p.getStock().modificarStock(dto.getCantidad(), dto.getStockMaximo());
    productoRepo.save(p);
    return aDTO(p);
  }

  private Producto producto(String id) {
    return productoRepo.findById(id).orElseThrow(() -> new ErrorService("Producto inexistente."));
  }

  private StockDTO aDTO(Producto p) {
    StockDTO dto = new StockDTO();
    dto.setProductoId(p.getId());
    dto.setProductoNombre(p.getNombre());
    dto.setCantidad(p.getStock().getCantidad());
    dto.setStockMaximo(p.getStock().getStockMaximo());
    dto.setActualizacion(p.getStock().getActualizacion());
    dto.setEstado(p.getStock().getEstado());
    return dto;
  }
}
