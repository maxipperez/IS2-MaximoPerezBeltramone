package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.ItemDTO;
import ar.edu.is2.ejercicio5.dto.OrdenCompraDTO;
import ar.edu.is2.ejercicio5.exception.ErrorService;
import ar.edu.is2.ejercicio5.model.*;
import ar.edu.is2.ejercicio5.repository.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdenCompraService {
  private final OrdenCompraRepository repo;
  private final ProveedorRepository proveedorRepo;
  private final ProductoRepository productoRepo;
  private final FacturaRepository facturaRepo;
  private final FacturaProveedorRepository facturaProveedorRepo;

  public OrdenCompraService(
      OrdenCompraRepository repo,
      ProveedorRepository proveedorRepo,
      ProductoRepository productoRepo,
      FacturaRepository facturaRepo,
      FacturaProveedorRepository facturaProveedorRepo) {
    this.repo = repo;
    this.proveedorRepo = proveedorRepo;
    this.productoRepo = productoRepo;
    this.facturaRepo = facturaRepo;
    this.facturaProveedorRepo = facturaProveedorRepo;
  }

  @Transactional(readOnly = true)
  public List<OrdenCompraDTO> listarOrdenes() {
    return repo.findAllByOrderByNumeroDesc().stream().map(o -> aDTO(o, false)).toList();
  }

  @Transactional(readOnly = true)
  public OrdenCompraDTO buscarPorId(String id) {
    return aDTO(entidad(id), true);
  }

  @Transactional(readOnly = true)
  public OrdenCompraDTO formularioNuevo() {
    OrdenCompraDTO dto = new OrdenCompraDTO();
    dto.setItems(itemsDisponibles(Map.of()));
    return dto;
  }

  @Transactional(readOnly = true)
  public OrdenCompraDTO formularioEditar(String id) {
    OrdenCompra o = pendiente(id);
    OrdenCompraDTO dto = aDTO(o, false);
    Map<String, DetalleOrdenCompra> actuales =
        o.getDetalles().stream()
            .collect(Collectors.toMap(d -> d.getProducto().getId(), Function.identity()));
    dto.setItems(itemsDisponibles(actuales));
    return dto;
  }

  @Transactional(readOnly = true)
  public List<ItemDTO> completarItems(List<ItemDTO> enviados) {
    Map<String, ItemDTO> porProducto =
        enviados.stream()
            .filter(i -> i.getProductoId() != null)
            .collect(Collectors.toMap(ItemDTO::getProductoId, Function.identity(), (a, b) -> a));
    return productoRepo.findByEliminadoFalseOrderByNombre().stream()
        .map(
            p -> {
              ItemDTO i = porProducto.get(p.getId());
              return new ItemDTO(
                  p.getId(),
                  p.getNombre(),
                  p.getStock().getCantidad(),
                  i == null ? null : i.getCantidad(),
                  i == null || i.getPrecioUnitario() == null
                      ? p.getPrecioUnitario()
                      : i.getPrecioUnitario());
            })
        .toList();
  }

  public OrdenCompraDTO crearOrdenCompra(OrdenCompraDTO dto) {
    OrdenCompra o = new OrdenCompra();
    o.setNumero(repo.ultimoNumero() + 1);
    completar(o, dto);
    return aDTO(repo.save(o), true);
  }

  public OrdenCompraDTO modificarOrdenCompra(String id, OrdenCompraDTO dto) {
    OrdenCompra o = pendiente(id);
    completar(o, dto);
    return aDTO(repo.save(o), true);
  }

  public void confirmarCompra(String id) {
    OrdenCompra o = pendiente(id);
    FacturaProveedor f = new FacturaProveedor();
    f.setNroFactura(facturaRepo.ultimoNumero() + 1);
    f.setProveedor(o.getProveedor());
    f.setOrdenCompra(o);
    for (DetalleOrdenCompra d : o.getDetalles())
      f.agregarDetalle(new Detalle(d.getProducto(), d.getCantidad(), d.getPrecioUnitario()));
    f.incrementarStock();
    o.confirmarCompra();
    facturaRepo.save(f);
    repo.save(o);
  }

  public void cancelarCompra(String id) {
    OrdenCompra o = pendiente(id);
    o.cancelarCompra();
    repo.save(o);
  }

  private void completar(OrdenCompra o, OrdenCompraDTO dto) {
    if (dto.getProveedorId() == null || dto.getProveedorId().isBlank())
      throw new ErrorService("Seleccione un proveedor.");
    Proveedor proveedor =
        proveedorRepo
            .findById(dto.getProveedorId())
            .filter(Proveedor::isActivo)
            .orElseThrow(() -> new ErrorService("El proveedor seleccionado no está activo."));
    List<ItemDTO> seleccionados = dto.getItems().stream().filter(ItemDTO::isSeleccionado).toList();
    if (seleccionados.isEmpty())
      throw new ErrorService("La orden debe contener al menos un producto");
    o.setProveedor(proveedor);
    o.limpiarDetalles();
    for (ItemDTO i : seleccionados) {
      if (i.getPrecioUnitario() == null || i.getPrecioUnitario() < 0)
        throw new ErrorService("Indique un precio unitario válido para cada producto.");
      Producto p =
          productoRepo
              .findById(i.getProductoId())
              .filter(x -> !x.isEliminado())
              .orElseThrow(() -> new ErrorService("Producto inexistente."));
      o.agregarDetalle(new DetalleOrdenCompra(p, i.getCantidad(), i.getPrecioUnitario()));
    }
  }

  private List<ItemDTO> itemsDisponibles(Map<String, DetalleOrdenCompra> actuales) {
    return productoRepo.findByEliminadoFalseOrderByNombre().stream()
        .map(
            p -> {
              DetalleOrdenCompra d = actuales.get(p.getId());
              return new ItemDTO(
                  p.getId(),
                  p.getNombre(),
                  p.getStock().getCantidad(),
                  d == null ? null : d.getCantidad(),
                  d == null ? p.getPrecioUnitario() : d.getPrecioUnitario());
            })
        .toList();
  }

  private OrdenCompra entidad(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Orden de compra inexistente."));
  }

  private OrdenCompra pendiente(String id) {
    OrdenCompra o = entidad(id);
    if (o.getEstado() != EstadoOrden.PENDIENTE)
      throw new ErrorService("Solo se pueden gestionar órdenes en estado pendiente.");
    return o;
  }

  private OrdenCompraDTO aDTO(OrdenCompra o, boolean conDetalle) {
    OrdenCompraDTO dto = new OrdenCompraDTO();
    dto.setId(o.getId());
    dto.setNumero(o.getNumero());
    dto.setFecha(o.getFecha());
    dto.setEstado(o.getEstado().name());
    dto.setTotal(o.getTotal());
    dto.setCantidadTotal(o.cantidadTotal());
    dto.setProveedorId(o.getProveedor().getId());
    dto.setProveedorRazonSocial(o.getProveedor().getRazonSocial());
    if (conDetalle) {
      dto.setItems(
          o.getDetalles().stream()
              .map(
                  d ->
                      new ItemDTO(
                          d.getProducto().getId(),
                          d.getProducto().getNombre(),
                          d.getProducto().getStock().getCantidad(),
                          d.getCantidad(),
                          d.getPrecioUnitario()))
              .toList());
      facturaProveedorRepo
          .findByOrdenCompraId(o.getId())
          .ifPresent(f -> dto.setNroFacturaProveedor(f.getNroFactura()));
    }
    return dto;
  }
}
