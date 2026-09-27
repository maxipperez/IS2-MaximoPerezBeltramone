package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.ItemDTO;
import ar.edu.is2.ejercicio5.dto.VentaDTO;
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
public class VentaService {
  private final FacturaClienteRepository repo;
  private final FacturaRepository facturaRepo;
  private final ClienteRepository clienteRepo;
  private final ProductoRepository productoRepo;

  public VentaService(
      FacturaClienteRepository repo,
      FacturaRepository facturaRepo,
      ClienteRepository clienteRepo,
      ProductoRepository productoRepo) {
    this.repo = repo;
    this.facturaRepo = facturaRepo;
    this.clienteRepo = clienteRepo;
    this.productoRepo = productoRepo;
  }

  @Transactional(readOnly = true)
  public List<VentaDTO> listarVentas() {
    return repo.findByEliminadoFalseOrderByNroFacturaDesc().stream()
        .map(f -> aDTO(f, false))
        .toList();
  }

  @Transactional(readOnly = true)
  public VentaDTO buscarPorId(String id) {
    return aDTO(entidad(id), true);
  }

  @Transactional(readOnly = true)
  public VentaDTO formularioNuevo(String productoId) {
    VentaDTO dto = new VentaDTO();
    dto.setItems(
        productoRepo.findByEliminadoFalseOrderByNombre().stream()
            .map(
                p ->
                    new ItemDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getStock().getCantidad(),
                        p.getId().equals(productoId) ? 1 : null,
                        p.getPrecioUnitario()))
            .toList());
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
                  p.getPrecioUnitario());
            })
        .toList();
  }

  public VentaDTO registrarVenta(VentaDTO dto) {
    if (dto.getClienteId() == null || dto.getClienteId().isBlank())
      throw new ErrorService("Seleccione un cliente.");
    Cliente cliente =
        clienteRepo
            .findById(dto.getClienteId())
            .filter(c -> !c.isEliminado())
            .orElseThrow(() -> new ErrorService("Cliente inexistente."));
    List<ItemDTO> seleccionados = dto.getItems().stream().filter(ItemDTO::isSeleccionado).toList();
    if (seleccionados.isEmpty())
      throw new ErrorService("La venta debe contener al menos un producto");
    FacturaCliente f = new FacturaCliente();
    f.setCliente(cliente);
    for (ItemDTO i : seleccionados) {
      Producto p =
          productoRepo
              .findById(i.getProductoId())
              .filter(x -> !x.isEliminado())
              .orElseThrow(() -> new ErrorService("Producto inexistente."));
      if (p.getStock().consultarStock() < i.getCantidad())
        throw new ErrorService("Stock insuficiente para completar la venta");
      f.agregarDetalle(new Detalle(p, i.getCantidad(), p.getPrecioUnitario()));
    }
    f.setNroFactura(facturaRepo.ultimoNumero() + 1);
    f.disminuirStock();
    return aDTO(repo.save(f), true);
  }

  public void anularVenta(String id) {
    FacturaCliente f = entidad(id);
    if (f.getEstado() != EstadoFactura.EMITIDA)
      throw new ErrorService("La venta ya se encuentra anulada.");
    f.reponerStock();
    f.setEstado(EstadoFactura.ANULADA);
    repo.save(f);
  }

  private FacturaCliente entidad(String id) {
    return repo.findById(id).orElseThrow(() -> new ErrorService("Venta inexistente."));
  }

  private VentaDTO aDTO(FacturaCliente f, boolean conDetalle) {
    VentaDTO dto = new VentaDTO();
    dto.setId(f.getIdFactura());
    dto.setNroFactura(f.getNroFactura());
    dto.setFecha(f.getFecha());
    dto.setEstado(f.getEstado().name());
    dto.setTotal(f.getTotal());
    dto.setClienteId(f.getCliente().getId());
    dto.setClienteNombre(f.getContraparte());
    dto.setClienteDni(f.getCliente().getDni());
    if (conDetalle)
      dto.setItems(
          f.getDetalles().stream()
              .map(
                  d ->
                      new ItemDTO(
                          d.getProducto().getId(),
                          d.getProducto().getNombre(),
                          d.getProducto().getStock().getCantidad(),
                          d.getCantidad(),
                          d.getPrecioUnitario()))
              .toList());
    return dto;
  }
}
