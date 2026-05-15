package es.upm.fi.grupo_i.dto;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;

public record ReservaDto (
    Long id,
    Long idViaje,
    Long idUsuario,
    int numPasajeros,
    ESTADO_RESERVA estado
){}