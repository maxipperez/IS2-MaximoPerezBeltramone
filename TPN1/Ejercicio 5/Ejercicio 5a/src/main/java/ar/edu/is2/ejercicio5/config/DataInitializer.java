package ar.edu.is2.ejercicio5.config;

import ar.edu.is2.ejercicio5.model.*;
import ar.edu.is2.ejercicio5.repository.*;
import ar.edu.is2.ejercicio5.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
  private final UsuarioService usuarioService;
  private final CategoriaRepository categoriaRepo;
  private final ProductoRepository productoRepo;
  private final ProveedorRepository proveedorRepo;
  private final ClienteRepository clienteRepo;

  public DataInitializer(
      UsuarioService usuarioService,
      CategoriaRepository categoriaRepo,
      ProductoRepository productoRepo,
      ProveedorRepository proveedorRepo,
      ClienteRepository clienteRepo) {
    this.usuarioService = usuarioService;
    this.categoriaRepo = categoriaRepo;
    this.productoRepo = productoRepo;
    this.proveedorRepo = proveedorRepo;
    this.clienteRepo = clienteRepo;
  }

  @Override
  @Transactional
  public void run(String... args) {
    usuarioService.crearAdministradorInicial("admin@techsales.com", "admin123");
    if (productoRepo.count() > 0) return;
    Categoria laptops = categoria("Laptops");
    Categoria monitores = categoria("Monitores");
    Categoria perifericos = categoria("Periféricos");
    Categoria redes = categoria("Redes");
    producto("Laptop Pro X1", "Notebook 14\" i7, 16 GB RAM, SSD 512 GB", 1299.0, laptops, 120, 200);
    producto("Monitor 27\" 4K", "Panel IPS UHD con HDR10", 650.0, monitores, 45, 50);
    producto("Teclado Mecánico RGB", "Switches rojos, layout español", 89.0, perifericos, 80, 100);
    producto("Mouse Inalámbrico Ergo", "Sensor óptico 4000 DPI", 55.0, perifericos, 250, 300);
    producto("Docking Station USB-C", "HDMI, Ethernet y 4 puertos USB", 149.0, perifericos, 15, 20);
    producto("Switch Gestionable 24 Puertos", "Gigabit con 4 SFP", 420.0, redes, 30, 40);
    Proveedor p = new Proveedor();
    p.setCuit("30-71234567-8");
    p.setRazonSocial("Mayorista Tech S.A.");
    p.setTelefono("261 555-0101");
    proveedorRepo.save(p);
    Cliente c = new Cliente();
    c.setNombre("Juan");
    c.setApellido("Pérez");
    c.setDni("30123456");
    c.setTelefono("261 555-0202");
    clienteRepo.save(c);
  }

  private Categoria categoria(String nombre) {
    Categoria c = new Categoria();
    c.setNombre(nombre);
    return categoriaRepo.save(c);
  }

  private void producto(
      String nombre, String descripcion, double precio, Categoria c, int stock, int maximo) {
    Producto p = new Producto();
    p.setNombre(nombre);
    p.setDescripcion(descripcion);
    p.setPrecioUnitario(precio);
    p.setCategoria(c);
    p.getStock().modificarStock(stock, maximo);
    productoRepo.save(p);
  }
}
