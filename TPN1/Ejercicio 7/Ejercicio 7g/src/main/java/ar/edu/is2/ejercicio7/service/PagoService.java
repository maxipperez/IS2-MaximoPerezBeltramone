package ar.edu.is2.ejercicio7.service;

import ar.edu.is2.ejercicio7.dto.PagoDTO;
import ar.edu.is2.ejercicio7.model.GrupoFamiliar;
import ar.edu.is2.ejercicio7.model.PagoCuota;
import ar.edu.is2.ejercicio7.repository.GrupoFamiliarRepository;
import ar.edu.is2.ejercicio7.repository.PagoCuotaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagoService {
  private final PagoCuotaRepository repo;
  private final GrupoFamiliarRepository grupoRepo;

  public PagoService(PagoCuotaRepository repo, GrupoFamiliarRepository grupoRepo) {
    this.repo = repo;
    this.grupoRepo = grupoRepo;
  }

  public void registrar(PagoDTO dto) {
    if (dto.getGrupoId() == null || dto.getPeriodo() == null || dto.getMonto() == null || dto.getMedioPago() == null)
      throw new ErrorService("Complete todos los campos.");
    if (!dto.getPeriodo().matches("\\d{4}-\\d{2}")) throw new ErrorService("El período debe tener el formato AAAA-MM.");
    if (dto.getMonto() <= 0) throw new ErrorService("El monto debe ser mayor a cero.");
    if (repo.existsByGrupoFamiliarIdAndPeriodo(dto.getGrupoId(), dto.getPeriodo()))
      throw new ErrorService("La familia ya pagó la cuota de ese período.");

    GrupoFamiliar g = grupoRepo.findById(dto.getGrupoId()).orElseThrow(() -> new ErrorService("Familia inexistente."));
    PagoCuota p = new PagoCuota();
    p.setGrupoFamiliar(g);
    p.setPeriodo(dto.getPeriodo());
    p.setMonto(dto.getMonto());
    p.setMedioPago(dto.getMedioPago());
    repo.save(p);
  }

  @Transactional(readOnly = true)
  public List<PagoDTO> listar() {
    return repo.findAllByOrderByFechaPagoDescIdDesc().stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<PagoDTO> porGrupo(Long grupoId) {
    return repo.findByGrupoFamiliarIdOrderByPeriodoDesc(grupoId).stream().map(this::aDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<PagoDTO> grupos() {
    return grupoRepo.findAllByOrderByNombreGrupoAsc().stream()
        .map(
            g -> {
              PagoDTO dto = new PagoDTO();
              dto.setGrupoId(g.getId());
              dto.setGrupo(g.getNombreGrupo() + " (socio N° " + g.getTitular().getNroSocio() + ")");
              return dto;
            })
        .toList();
  }

  private PagoDTO aDTO(PagoCuota p) {
    PagoDTO dto = new PagoDTO();
    dto.setId(p.getId());
    dto.setGrupoId(p.getGrupoFamiliar().getId());
    dto.setGrupo(p.getGrupoFamiliar().getNombreGrupo());
    dto.setPeriodo(p.getPeriodo());
    dto.setMonto(p.getMonto());
    dto.setFechaPago(p.getFechaPago());
    dto.setMedioPago(p.getMedioPago());
    return dto;
  }
}
