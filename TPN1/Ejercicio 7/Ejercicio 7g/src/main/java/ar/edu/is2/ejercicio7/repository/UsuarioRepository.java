package ar.edu.is2.ejercicio7.repository;

import ar.edu.is2.ejercicio7.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByMail(String mail);
}
