package ar.edu.is2.ejercicio7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.is2.ejercicio7.model.RegistroAcceso;
import ar.edu.is2.ejercicio7.model.Socio;
import ar.edu.is2.ejercicio7.repository.PersonaRepository;
import ar.edu.is2.ejercicio7.repository.RegistroAccesoRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccesoServiceTest {
  @Mock private RegistroAccesoRepository repo;
  @Mock private PersonaRepository personaRepo;
  @InjectMocks private AccesoService service;

  private Socio socio;

  @BeforeEach
  void setUp() {
    socio = new Socio();
    socio.setNombre("Carlos");
    socio.setApellido("Fernández");
    when(personaRepo.findById(1L)).thenReturn(Optional.of(socio));
  }

  @Test
  void registraLaEntradaSiLaPersonaNoEstaDentro() {
    when(repo.findFirstByPersonaIdAndFechaHoraSalidaIsNull(1L)).thenReturn(Optional.empty());

    service.registrarEntrada(1L);

    ArgumentCaptor<RegistroAcceso> captor = ArgumentCaptor.forClass(RegistroAcceso.class);
    verify(repo).save(captor.capture());
    assertSame(socio, captor.getValue().getPersona());
    assertNotNull(captor.getValue().getFechaHoraEntrada());
    assertNull(captor.getValue().getFechaHoraSalida());
  }

  @Test
  void noPermiteUnSegundoIngresoSinEgreso() {
    when(repo.findFirstByPersonaIdAndFechaHoraSalidaIsNull(1L)).thenReturn(Optional.of(new RegistroAcceso()));

    ErrorService e = assertThrows(ErrorService.class, () -> service.registrarEntrada(1L));

    assertTrue(e.getMessage().contains("ya se encuentra dentro"));
    verify(repo, never()).save(any());
  }

  @Test
  void noPermiteUnEgresoSinIngresoAbierto() {
    when(repo.findFirstByPersonaIdAndFechaHoraSalidaIsNull(1L)).thenReturn(Optional.empty());

    ErrorService e = assertThrows(ErrorService.class, () -> service.registrarSalida(1L));

    assertTrue(e.getMessage().contains("no tiene un ingreso abierto"));
  }

  @Test
  void registraLaSalidaDelIngresoAbierto() {
    RegistroAcceso abierto = new RegistroAcceso();
    abierto.setPersona(socio);
    abierto.setFechaHoraEntrada(LocalDateTime.now().minusHours(2));
    when(repo.findFirstByPersonaIdAndFechaHoraSalidaIsNull(1L)).thenReturn(Optional.of(abierto));

    service.registrarSalida(1L);

    assertNotNull(abierto.getFechaHoraSalida());
    verify(repo).save(abierto);
  }
}
