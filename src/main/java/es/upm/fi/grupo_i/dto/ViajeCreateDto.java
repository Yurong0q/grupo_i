package es.upm.fi.grupo_i.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.upm.fi.grupo_i.model.Precio;

public record ViajeCreateDto(

    Long conductorId,
    String origen,
    String destino,
    List<String> paradas,
    int plazasDisponibles,
    Precio precio,
    LocalDate fechaSalida,
    LocalTime horaSalida,
    int duracionEstimada
) {}