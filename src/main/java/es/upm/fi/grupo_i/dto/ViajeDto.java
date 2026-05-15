package es.upm.fi.grupo_i.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.upm.fi.grupo_i.model.Precio;
import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;

public class ViajeDto {
    private Long id;
    private Long conductor_id;
    private String origen;
    private String destino;
    private List<String> paradas;
    private int plazas_disponibles;
    private Precio precio;
    private LocalDate fecha_salida;
    private LocalTime hora_salida;
    private int duracion_estimada;
    private ESTADO_VIAJE estado;
}