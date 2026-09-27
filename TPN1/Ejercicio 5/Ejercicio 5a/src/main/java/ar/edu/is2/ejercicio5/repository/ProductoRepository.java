package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, String> {
  List<Producto> findByEliminadoFalseOrderByNombre();

  List<Producto> findByCategoriaId(String categoriaId);

  @Query(
      "select p from Producto p left join p.categoria c where p.eliminado = false"
          + " and (:categoriaId is null or c.id = :categoriaId)"
          + " and (:texto is null or lower(p.nombre) like lower(concat('%', :texto, '%')))"
          + " order by p.nombre")
  List<Producto> buscarActivos(
      @Param("texto") String texto, @Param("categoriaId") String categoriaId);
}
