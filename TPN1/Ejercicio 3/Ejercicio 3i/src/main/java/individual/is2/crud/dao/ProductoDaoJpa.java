package individual.is2.crud.dao;

import individual.is2.crud.modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/** Implementación del DAO que usa JPA (Hibernate realiza el ORM). */
public class ProductoDaoJpa implements ProductoDao {
    private final EntityManagerFactory emf;

    public ProductoDaoJpa(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void guardar(Producto producto) {
        ejecutarEnTransaccion(em -> em.persist(producto));
    }

    @Override
    public Producto buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> listar() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Producto p ORDER BY p.id", Producto.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(Producto producto) {
        ejecutarEnTransaccion(em -> em.merge(producto));
    }

    @Override
    public void eliminar(Long id) {
        ejecutarEnTransaccion(em -> {
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                em.remove(producto);
            }
        });
    }

    private void ejecutarEnTransaccion(OperacionJpa operacion) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            operacion.ejecutar(em);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @FunctionalInterface
    private interface OperacionJpa {
        void ejecutar(EntityManager em);
    }
}
