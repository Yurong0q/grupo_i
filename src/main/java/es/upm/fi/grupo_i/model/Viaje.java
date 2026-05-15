package es.upm.fi.grupo_i.model;

import es.upm.fi.grupo_i.Precio;
import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "Viajes")

public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long conductor_id;
    private String origen;
    private String destino;
    private String[] paradas;
    private int plazas_disponibles;
    private Precio precio;
    private LocalDate fecha_salida;
    private LocalTime hora_salida;
    private int duracion_estimada;
    private ESTADO_VIAJE estado;
    
    private Viaje() {
        // Constructor vacío para JPA
    }

    private Viaje(Long conductor_id, String origen, String destino, String[] paradas, int plazas_disponibles, Precio precio, LocalDate fecha_salida, LocalTime hora_salida, int duracion_estimada) {
        this.conductor_id = conductor_id;
        this.origen = origen;
        this.destino = destino;
        this.paradas = paradas;
        this.plazas_disponibles = plazas_disponibles;
        this.precio = precio;
        this.fecha_salida = fecha_salida;
        this.hora_salida = hora_salida;
        this.duracion_estimada = duracion_estimada;
        this.estado = ESTADO_VIAJE.ACTIVO; // Por defecto, el viaje se crea como activo
    }


    public Long getId() {
        return id;
    }

    public Long getConductor_id() {
        return conductor_id;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String[] getParadas() {
        return paradas;
    }

    public int getPlazas_disponibles() {
        return plazas_disponibles;
    }

    public Precio getPrecio() {
        return precio;
    }

    public LocalDate getFecha_salida() {
        return fecha_salida;
    }

    public LocalTime getHora_salida() {
        return hora_salida;
    }

    public int getDuracion_estimada() {
        return duracion_estimada;
    }

    public ESTADO_VIAJE getEstado() {
        return estado;
    }


    public void setPlazas_disponibles(int plazas_disponibles) {
        this.plazas_disponibles = plazas_disponibles;
    }

    public void setEstado(ESTADO_VIAJE estado) {
        this.estado = estado;
    }

}
