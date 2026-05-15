package es.upm.fi.grupo_i.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.upm.fi.grupo_i.model.Precio;
import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;

public record ViajeDto (
    Long id,
    Long conductorId,
    String origen,
    String destino,
    List<String> paradas,
    int plazasDisponibles,
    Precio precio,
    LocalDate fechaSalida,
    LocalTime horaSalida,
    int duracionEstimada,
    ESTADO_VIAJE estado
) {}