package es.upm.fi.grupo_i.dto;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;

import java.time.LocalDateTime;

public record ReservaDto (
    Long id,
    Long viajeId,
    Long pasajeroId,
    int numeroPasajeros,
    LocalDateTime fechaCreacion,
    ESTADO_RESERVA estado,
    Long idPago
){}