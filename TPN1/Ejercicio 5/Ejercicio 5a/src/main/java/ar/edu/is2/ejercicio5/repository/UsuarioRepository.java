package ar.edu.is2.ejercicio5.repository;

import ar.edu.is2.ejercicio5.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
  Optional<Usuario> findByMailIgnoreCase(String mail);

  boolean existsByMailIgnoreCase(String mail);

  boolean existsByMailIgnoreCaseAndIdNot(String mail, String id);

  List<Usuario> findAllByOrderByApellidoAscNombreAsc();
}
