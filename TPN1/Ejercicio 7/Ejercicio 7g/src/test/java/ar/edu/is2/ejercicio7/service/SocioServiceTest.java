package ar.edu.is2.ejercicio7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.is2.ejercicio7.dto.SocioDTO;
import ar.edu.is2.ejercicio7.model.Socio;
import ar.edu.is2.ejercicio7.repository.FamiliarRepository;
import ar.edu.is2.ejercicio7.repository.PersonaRepository;
import ar.edu.is2.ejercicio7.repository.SocioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SocioServiceTest {
  @Mock private SocioRepository socioRepo;
  @Mock private FamiliarRepository familiarRepo;
  @Mock private PersonaRepository personaRepo;
  @InjectMocks private SocioService service;

  private final byte[] foto = {1, 2, 3};

  private SocioDTO socio() {
    SocioDTO dto = new SocioDTO();
    dto.setNombre("Carlos");
    dto.setApellido("Fernández");
    dto.setDni("25111222");
    return dto;
  }

  @Test
  void altaDeSocioAsignaNumeroYCreaSuGrupoFamiliar() {
    when(personaRepo.existsByDni("25111222")).thenReturn(false);
    when(socioRepo.ultimoNroSocio()).thenReturn(1005);
    when(socioRepo.save(any(Socio.class))).thenAnswer(i -> i.getArgument(0));

    SocioDTO guardado = service.guardar(socio(), foto, "image/png");

    ArgumentCaptor<Socio> captor = ArgumentCaptor.forClass(Socio.class);
    verify(socioRepo).save(captor.capture());
    Socio s = captor.getValue();
    assertEquals(1006, s.getNroSocio());
    assertNotNull(s.getFoto());
    assertSame(s, s.getGrupoFamiliar().getTitular());
    assertEquals("Familia Fernández", guardado.getGrupo());
  }

  @Test
  void rechazaUnDniDuplicado() {
    when(personaRepo.existsByDni("25111222")).thenReturn(true);

    ErrorService e = assertThrows(ErrorService.class, () -> service.guardar(socio(), foto, "image/png"));

    assertEquals("Ya existe una persona con ese DNI.", e.getMessage());
    verify(socioRepo, never()).save(any());
  }

  @Test
  void laFotoDelRostroEsObligatoriaEnElAlta() {
    when(personaRepo.existsByDni("25111222")).thenReturn(false);

    ErrorService e = assertThrows(ErrorService.class, () -> service.guardar(socio(), null, null));

    assertEquals("La foto del rostro es obligatoria.", e.getMessage());
  }

  @Test
  void soloAceptaFotosJpgOPng() {
    when(personaRepo.existsByDni("25111222")).thenReturn(false);

    ErrorService e = assertThrows(ErrorService.class, () -> service.guardar(socio(), foto, "image/gif"));

    assertEquals("La foto debe ser JPG o PNG.", e.getMessage());
  }

  @Test
  void rechazaCamposVacios() {
    SocioDTO dto = socio();
    dto.setNombre(" ");

    assertThrows(ErrorService.class, () -> service.guardar(dto, foto, "image/png"));
  }
}
