package es.upm.fi.grupo_i.dto;

public record ResenyaDto(
    Long id,
    Long viajeId,
    Long autorId,
    int puntuacion,
    String comentario
) {}
