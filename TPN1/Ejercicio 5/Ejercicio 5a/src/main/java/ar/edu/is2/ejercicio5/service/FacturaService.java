package ar.edu.is2.ejercicio5.service;

import ar.edu.is2.ejercicio5.dto.FacturaDTO;
import ar.edu.is2.ejercicio5.repository.FacturaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FacturaService {
  private final FacturaRepository repo;

  public FacturaService(FacturaRepository repo) {
    this.repo = repo;
  }

  public List<FacturaDTO> listarFacturas() {
    return repo.findByEliminadoFalseOrderByNroFacturaDesc().stream()
        .map(
            f ->
                new FacturaDTO(
                    f.getIdFactura(),
                    f.getTipo(),
                    f.getNroFactura(),
                    f.getFecha(),
                    f.getContraparte(),
                    f.getTotal(),
                    f.getEstado().name()))
        .toList();
  }
}
