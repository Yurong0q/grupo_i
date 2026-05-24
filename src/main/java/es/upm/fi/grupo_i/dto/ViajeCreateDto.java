package es.upm.fi.grupo_i.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.upm.fi.grupo_i.model.Precio;
import io.swagger.v3.oas.annotations.media.Schema;

public record ViajeCreateDto(

    @Schema(example = "1")
    Long conductorId,

    @Schema(example = "Madrid")
    String origen,

    @Schema(example = "Tres Cantos")
    String destino,

    @Schema(example = "[\"Moncloa\"]")
    List<String> paradas,

    @Schema(example = "3")
    int plazasDisponibles,

    Precio precio,

    @Schema(example = "2026-05-24")
    LocalDate fechaSalida,

    @Schema(example = "08:30:00", type = "string")
    LocalTime horaSalida,

    @Schema(example = "90")
    int duracionEstimada
) {}