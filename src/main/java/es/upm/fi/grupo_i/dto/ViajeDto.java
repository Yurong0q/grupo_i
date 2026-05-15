package es.upm.fi.grupo_i.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.upm.fi.grupo_i.model.Precio;
import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;

public class ViajeDto {
    private Long id;
    private Long conductorId;
    private String origen;
    private String destino;
    private List<String> paradas;
    private int plazasDisponibles;
    private Precio precio;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private int duracionEstimada;
    private ESTADO_VIAJE estado;
}