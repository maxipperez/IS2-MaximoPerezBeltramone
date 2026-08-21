package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acceso CRUD de administradores. */
public interface AdministradorRepository extends JpaRepository<Administrador, String> {}
