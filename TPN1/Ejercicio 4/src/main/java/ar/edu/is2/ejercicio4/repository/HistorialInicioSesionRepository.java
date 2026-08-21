package ar.edu.is2.ejercicio4.repository;

import ar.edu.is2.ejercicio4.model.HistorialInicioSesion;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio de auditoría. */
public interface HistorialInicioSesionRepository
    extends JpaRepository<HistorialInicioSesion, Long> {}
