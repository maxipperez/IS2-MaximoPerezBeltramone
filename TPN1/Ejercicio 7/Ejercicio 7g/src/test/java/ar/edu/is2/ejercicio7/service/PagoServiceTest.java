package ar.edu.is2.ejercicio7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.is2.ejercicio7.dto.PagoDTO;
import ar.edu.is2.ejercicio7.model.GrupoFamiliar;
import ar.edu.is2.ejercicio7.model.MedioPago;
import ar.edu.is2.ejercicio7.model.PagoCuota;
import ar.edu.is2.ejercicio7.repository.GrupoFamiliarRepository;
import ar.edu.is2.ejercicio7.repository.PagoCuotaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {
  @Mock private PagoCuotaRepository repo;
  @Mock private GrupoFamiliarRepository grupoRepo;
  @InjectMocks private PagoService service;

  private PagoDTO pago(String periodo, double monto, MedioPago medio) {
    PagoDTO dto = new PagoDTO();
    dto.setGrupoId(1L);
    dto.setPeriodo(periodo);
    dto.setMonto(monto);
    dto.setMedioPago(medio);
    return dto;
  }

  @ParameterizedTest
  @EnumSource(MedioPago.class)
  void registraElPagoConCadaMedioDePago(MedioPago medio) {
    GrupoFamiliar grupo = new GrupoFamiliar();
    when(repo.existsByGrupoFamiliarIdAndPeriodo(1L, "2026-09")).thenReturn(false);
    when(grupoRepo.findById(1L)).thenReturn(Optional.of(grupo));

    service.registrar(pago("2026-09", 25000, medio));

    ArgumentCaptor<PagoCuota> captor = ArgumentCaptor.forClass(PagoCuota.class);
    verify(repo).save(captor.capture());
    assertEquals(medio, captor.getValue().getMedioPago());
    assertEquals(25000, captor.getValue().getMonto());
    assertSame(grupo, captor.getValue().getGrupoFamiliar());
  }

  @Test
  void rechazaUnMontoMenorOIgualACero() {
    ErrorService e = assertThrows(ErrorService.class, () -> service.registrar(pago("2026-09", 0, MedioPago.EFECTIVO)));

    assertEquals("El monto debe ser mayor a cero.", e.getMessage());
    verify(repo, never()).save(any());
  }

  @Test
  void rechazaUnPeriodoConFormatoInvalido() {
    assertThrows(ErrorService.class, () -> service.registrar(pago("09/2026", 100, MedioPago.EFECTIVO)));
  }

  @Test
  void noPermitePagarDosVecesElMismoPeriodo() {
    when(repo.existsByGrupoFamiliarIdAndPeriodo(1L, "2026-09")).thenReturn(true);

    ErrorService e =
        assertThrows(ErrorService.class, () -> service.registrar(pago("2026-09", 100, MedioPago.TRANSFERENCIA)));

    assertEquals("La familia ya pagó la cuota de ese período.", e.getMessage());
    verify(repo, never()).save(any());
  }
}
