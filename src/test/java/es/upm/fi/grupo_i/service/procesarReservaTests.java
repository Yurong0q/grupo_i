package es.upm.fi.grupo_i.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.enums.DIVISA;
import es.upm.fi.grupo_i.model.*;
import es.upm.fi.grupo_i.repository.ReservasRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class procesarReservaTests {

    private Viaje viaje;
    private long pasajeroId;

    @Mock private ReservasRepository reservasRepository;
    @Mock private ViajeService viajeService;
    @Mock private PagosFake pagosFake;
    @Mock private NotificacionesFake notificacionesFake;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    public void inicializar() {
        Precio precio = new Precio(10f, DIVISA.EURO);
        viaje = new Viaje(1L, "Madrid", "Sevilla", null, 4, precio, LocalDate.now(), LocalTime.now(), 128);

        when(viajeService.comprobarDisponibilidad(anyLong())).thenReturn(4);
        when(viajeService.comprobarConductorDistintoDePasajero(anyLong(), anyLong())).thenReturn(false);
        when(pagosFake.procesarPago(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(99L);
    }

    @Test
    public void correcto() {
        assertDoesNotThrow(() ->
            reservaService.procesarReserva(5L, pasajeroId, 1, "datos pago"));
    }

    @Test
    @DisplayName("Debe fallar si viajeId es null")
    public void viajeIdNull() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            reservaService.procesarReserva(null, pasajeroId, 1, "datos pago"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Debe fallar si pasajeroId es null")
    public void pasajeroIdNull() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            reservaService.procesarReserva(viaje.getId(), null, 1, "datos pago"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Debe fallar si el pasajero es el conductor")
    public void pasajeroEsConductor() {
        when(viajeService.comprobarConductorDistintoDePasajero(viaje.getId(), viaje.getConductorId()))
            .thenReturn(true);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            reservaService.procesarReserva(viaje.getId(), viaje.getConductorId(), 1, "datos pago"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Debe fallar si el número de pasajeros no es positivo")
    public void numPasajerosNegativo() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            reservaService.procesarReserva(viaje.getId(), pasajeroId, 0, "datos pago"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Debe fallar si no hay huecos suficientes")
    public void noDisponibilidad() {
        when(viajeService.comprobarDisponibilidad(viaje.getId())).thenReturn(0);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            reservaService.procesarReserva(viaje.getId(), pasajeroId, viaje.getPlazasDisponibles() + 1, "datos pago"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }
}