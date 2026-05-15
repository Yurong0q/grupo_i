package es.upm.fi.grupo_i.dto;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;

public class ReservaDto {
    private Long id;
    private Long idViaje;
    private Long idUsuario;
    private int numPasajeros;
    private ESTADO_RESERVA estado;
}