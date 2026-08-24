package individual.is2.crud;

import individual.is2.crud.dao.ProductoDao;
import individual.is2.crud.dao.ProductoDaoJpa;
import individual.is2.crud.modelo.Producto;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;

/** CRUD de consola para demostrar JPA + MySQL + ORM + DAO. */
public class App {
    private static final Scanner TECLADO = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudPU");
        ProductoDao productoDao = new ProductoDaoJpa(emf);

        try {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero("Opción: ");
                switch (opcion) {
                    case 1 -> crear(productoDao);
                    case 2 -> productoDao.listar().forEach(System.out::println);
                    case 3 -> buscar(productoDao);
                    case 4 -> editar(productoDao);
                    case 5 -> eliminar(productoDao);
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opción inválida.");
                }
            } while (opcion != 0);
        } finally {
            emf.close();
            TECLADO.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- CRUD DE PRODUCTOS ---");
        System.out.println("1. Crear  2. Listar  3. Buscar  4. Editar  5. Eliminar  0. Salir");
    }

    private static void crear(ProductoDao dao) {
        Producto producto = new Producto(leerTexto("Nombre: "), leerDouble("Precio: "));
        dao.guardar(producto);
        System.out.println("Guardado con id " + producto.getId());
    }

    private static void buscar(ProductoDao dao) {
        Producto producto = dao.buscarPorId(leerLong("Id: "));
        System.out.println(producto == null ? "Producto no encontrado." : producto);
    }

    private static void editar(ProductoDao dao) {
        Producto producto = dao.buscarPorId(leerLong("Id a editar: "));
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        producto.setNombre(leerTexto("Nuevo nombre: "));
        producto.setPrecio(leerDouble("Nuevo precio: "));
        dao.actualizar(producto);
        System.out.println("Producto actualizado.");
    }

    private static void eliminar(ProductoDao dao) {
        Long id = leerLong("Id a eliminar: ");
        if (dao.buscarPorId(id) == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        dao.eliminar(id);
        System.out.println("Producto eliminado.");
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return TECLADO.nextLine();
    }

    private static int leerEntero(String mensaje) { return Integer.parseInt(leerTexto(mensaje)); }
    private static Long leerLong(String mensaje) { return Long.parseLong(leerTexto(mensaje)); }
    private static Double leerDouble(String mensaje) { return Double.parseDouble(leerTexto(mensaje)); }
}
