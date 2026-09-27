package ar.edu.is2.ejercicio7;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.is2.ejercicio7.model.GrupoFamiliar;
import ar.edu.is2.ejercicio7.model.MedioPago;
import ar.edu.is2.ejercicio7.model.PagoCuota;
import ar.edu.is2.ejercicio7.repository.GrupoFamiliarRepository;
import ar.edu.is2.ejercicio7.repository.PagoCuotaRepository;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
class AuditoriaTest {
  @Autowired private PagoCuotaRepository pagoRepo;
  @Autowired private GrupoFamiliarRepository grupoRepo;
  @Autowired private PlatformTransactionManager transactionManager;

  @Test
  void registraElHistorialDeUnPagoDeCuota() {
    TransactionTemplate transaccion = new TransactionTemplate(transactionManager);
    AtomicLong id = new AtomicLong();

    transaccion.executeWithoutResult(
        estado -> {
          GrupoFamiliar grupo = grupoRepo.findAll().get(0);
          PagoCuota pago = new PagoCuota();
          pago.setGrupoFamiliar(grupo);
          pago.setPeriodo("2026-01");
          pago.setMonto(20000.0);
          pago.setMedioPago(MedioPago.EFECTIVO);
          id.set(pagoRepo.save(pago).getId());
        });

    transaccion.executeWithoutResult(
        estado -> {
          PagoCuota pago = pagoRepo.findById(id.get()).orElseThrow();
          pago.setMedioPago(MedioPago.MERCADO_PAGO);
          pagoRepo.save(pago);
        });

    var revisiones = pagoRepo.findRevisions(id.get()).getContent();
    assertEquals(2, revisiones.size());
    assertEquals(RevisionType.INSERT, revisiones.get(0).getMetadata().getRevisionType());
    assertEquals(RevisionType.UPDATE, revisiones.get(1).getMetadata().getRevisionType());
    assertEquals(MedioPago.EFECTIVO, revisiones.get(0).getEntity().getMedioPago());
    assertEquals(MedioPago.MERCADO_PAGO, revisiones.get(1).getEntity().getMedioPago());
  }
}
