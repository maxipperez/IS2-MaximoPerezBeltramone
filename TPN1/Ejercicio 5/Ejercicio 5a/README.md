# Ejercicio 5a — TechSales: compras, stock y ventas (MVC con IA)

Proyecto Spring Boot MVC basado en los diagramas de clases, prototipos e historias de usuario del **Ejercicio 5a grupal** (empresa de venta de productos de tecnología que repone stock mediante órdenes de compra a proveedores mayoristas). Incluye Web MVC, JPA, Thymeleaf, MySQL y DTOs entre las capas de servicio y controlador. El acceso al sistema es con usuario (correo) y contraseña.

Plantilla HTML/CSS Bootstrap 5: **InApp – Free Inventory Admin Dashboard** de ThemeWagon (https://themewagon.com/themes/inapp/), licencia MIT.

## Ejecutar

```powershell
cd 'TPN1/Ejercicio 5/Ejercicio 5a'
mvn -s maven-settings.xml spring-boot:run
```

Abrir `http://localhost:8080`. Iniciar **MySQL** desde XAMPP; la aplicación crea la base `ejercicio5` en el primer arranque. Si el usuario `root` tiene contraseña, actualizar `spring.datasource.password` en `application.properties`.

Usuario administrador inicial: `admin@techsales.com` / `admin123`. Desde `/registro` se pueden crear cuentas de vendedor.

## Modelo

| Diagrama grupal | Implementación |
|---|---|
| Producto – Stock (1..1, posee) | `Producto` compone su `Stock` (cantidad, stock máximo, actualización) |
| Categoría ◇ Producto (agrupa) | Al eliminar una categoría los productos quedan sin categoría |
| Proveedor 1 – 0..* OrdenCompra | `OrdenCompra` (PENDIENTE, CONFIRMADA, CANCELADA) |
| OrdenCompra ◆ DetalleOrdenCompra 1..* | Detalle con cantidad, precio unitario y subtotal |
| Factura (abstracta) ◆ Detalle | Herencia JOINED: `FacturaProveedor` y `FacturaCliente` |
| FacturaProveedor.incrementarStock | Se genera al confirmar la recepción de una orden de compra |
| FacturaCliente.disminuirStock | Se genera al registrar una venta a un `Cliente` |
| Acceso con usuario y contraseña | `Usuario` con rol ADMINISTRADOR o VENDEDOR y clave cifrada con BCrypt |

## Funcionalidades

| Módulo | Ruta | Rol |
|---|---|---|
| Catálogo con búsqueda y filtro por categoría | `/` | Todos |
| Ventas y comprobante | `/ventas` | Todos (anular: administrador) |
| Clientes | `/clientes` | Todos |
| Stock | `/stock` | Administrador |
| Órdenes de compra | `/ordenes` | Administrador |
| Productos, categorías, proveedores | `/productos`, `/categorias`, `/proveedores` | Administrador |
| Facturas de compra y venta | `/facturas` | Administrador |
| Usuarios | `/usuarios` | Administrador |

Reglas de las historias de usuario implementadas: alta de producto con stock inicial cero y campos obligatorios, no modificar productos eliminados, no eliminar productos con órdenes pendientes, CUIT y DNI únicos, orden de compra con al menos un producto, venta con stock suficiente. Las bajas son lógicas.

## Estructura

```
src/main/java/ar/edu/is2/ejercicio5
├── config        Interceptor de autenticación, BCrypt y datos iniciales
├── controller    Controladores MVC (reciben y envían DTOs)
├── dto           Objetos de transferencia entre capas
├── exception     ErrorService
├── model         Entidades JPA y enumeraciones
├── repository    Interfaces Spring Data JPA
└── service       Reglas de negocio y conversión entidad ⇄ DTO
```
