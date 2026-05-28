package es.upm.fi.grupo_i.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.repository.ResenyaRepository;

@ExtendWith(MockitoExtension.class)
class ResenyaServiceTest {

    @Mock
    private ResenyaRepository resenyaRepository;

    @Mock
    private ViajeService viajeService;

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ResenyaService resenyaService;

    @Test
    @DisplayName("Debe fallar si viajeId es null")
    void registrarResenya_falla_si_viajeId_es_null() {
        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> resenyaService.registrarResenya(null, 2L, 8, "Buen viaje")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("viajeId"));
        verifyNoInteractions(viajeService, reservaService, resenyaRepository); //Para saber que fallo antes de tocar eso
    }

    @Test
    @DisplayName("Debe fallar si autorId es null")
    void registrarResenya_falla_si_autorId_es_null() {
        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> resenyaService.registrarResenya(1L, null, 8, "Buen viaje")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("autorId"));
        verifyNoInteractions(viajeService, reservaService, resenyaRepository);
    }

    @Test
    @DisplayName("Debe fallar si el viaje no ha finalizado")
    void registrarResenya_falla_si_viaje_no_finalizado() {
        when(viajeService.comprobarViajeFinalizado(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> resenyaService.registrarResenya(1L, 2L, 8, "Buen viaje")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("no ha finalizado"));
        verify(viajeService).comprobarViajeFinalizado(1L);
        verifyNoInteractions(reservaService, resenyaRepository);
    }

    @Test
    @DisplayName("Debe fallar si el usuario no tiene reserva válida")
    void registrarResenya_falla_si_reserva_no_valida() {
        when(viajeService.comprobarViajeFinalizado(1L)).thenReturn(true);
        when(reservaService.comprobarReservaValida(1L, 2L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> resenyaService.registrarResenya(1L, 2L, 8, "Buen viaje")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("reserva válida"));
        verify(viajeService).comprobarViajeFinalizado(1L);
        verify(reservaService).comprobarReservaValida(1L, 2L);
        verifyNoInteractions(resenyaRepository);
    }

    @Test
    @DisplayName("Debe fallar si la puntuación es menor que 0")
    void registrarResenya_falla_si_puntuacion_menor_que_0() {
        when(viajeService.comprobarViajeFinalizado(1L)).thenReturn(true);
        when(reservaService.comprobarReservaValida(1L, 2L)).thenReturn(true);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> resenyaService.registrarResenya(1L, 2L, -1, "Comentario")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("0 y 10"));
        verifyNoInteractions(resenyaRepository);
    }

    @Test
    @DisplayName("Debe fallar si la puntuación es mayor que 10")
    void registrarResenya_falla_si_puntuacion_mayor_que_10() {
        when(viajeService.comprobarViajeFinalizado(1L)).thenReturn(true);
        when(reservaService.comprobarReservaValida(1L, 2L)).thenReturn(true);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> resenyaService.registrarResenya(1L, 2L, 11, "Comentario")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("0 y 10"));
        verifyNoInteractions(resenyaRepository);
    }

    @Test
    @DisplayName("Debe registrar la reseña correctamente")
    void registrarResenya_ok() {
        when(viajeService.comprobarViajeFinalizado(1L)).thenReturn(true);
        when(reservaService.comprobarReservaValida(1L, 2L)).thenReturn(true);

        Resenya resultado = resenyaService.registrarResenya(1L, 2L, 8, "Buen viaje");

        ArgumentCaptor<Resenya> captor = ArgumentCaptor.forClass(Resenya.class);
        verify(resenyaRepository).save(captor.capture()); //capturo resenya "guardada" para verificar atributos

        Resenya guardada = captor.getValue();

        assertAll("reseña registrada",
            () -> assertNotNull(resultado),
            () -> assertEquals(1L, guardada.getViajeId()),
            () -> assertEquals(2L, guardada.getAutorId()),
            () -> assertEquals(8, guardada.getPuntuacion()),
            () -> assertEquals("Buen viaje", guardada.getComentario())
        );
    }

}