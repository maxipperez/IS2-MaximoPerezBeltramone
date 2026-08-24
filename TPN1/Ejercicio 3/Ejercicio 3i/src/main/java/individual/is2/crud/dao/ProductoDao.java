package individual.is2.crud.dao;

import individual.is2.crud.modelo.Producto;
import java.util.List;

/** Contrato DAO: separa las operaciones de datos del menú de la aplicación. */
public interface ProductoDao {
    void guardar(Producto producto);
    Producto buscarPorId(Long id);
    List<Producto> listar();
    void actualizar(Producto producto);
    void eliminar(Long id);
}
