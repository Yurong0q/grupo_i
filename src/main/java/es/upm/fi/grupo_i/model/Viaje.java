package es.upm.fi.grupo_i.model;

import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conductor_id")
    private Long conductorId;

    private String origen;
    private String destino;

    @ElementCollection
    private List<String> paradas = new ArrayList<>();

    @Column(name = "plazas_disponibles")
    private int plazasDisponibles;

    @Embedded
    private Precio precio;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @Column(name = "hora_salida")
    private LocalTime horaSalida;

    @Column(name = "duracion_estimada")
    private int duracionEstimada;

    @Enumerated(EnumType.STRING)
    private ESTADO_VIAJE estado;

    protected Viaje() {
    }

    public Viaje(
        Long conductorId,
        String origen,
        String destino,
        List<String> paradas,
        int plazasDisponibles,
        Precio precio,
        LocalDate fechaSalida,
        LocalTime horaSalida,
        int duracionEstimada
    ) {
        this.conductorId = conductorId;
        this.origen = origen;
        this.destino = destino;
        this.paradas = paradas != null ? paradas : new ArrayList<>();
        this.plazasDisponibles = plazasDisponibles;
        this.precio = precio;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.duracionEstimada = duracionEstimada;
        this.estado = ESTADO_VIAJE.ACTIVO;
    }

    public Long getId() {
        return id;
    }

    public Long getConductorId() {
        return conductorId;
    }

    public void setConductorId(Long conductorId) {
        this.conductorId = conductorId;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<String> getParadas() {
        return paradas;
    }

    public void setParadas(List<String> paradas) {
        this.paradas = paradas != null ? paradas : new ArrayList<>();
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public void setPlazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public Precio getPrecio() {
        return precio;
    }

    public void setPrecio(Precio precio) {
        this.precio = precio;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(int duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public ESTADO_VIAJE getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_VIAJE estado) {
        this.estado = estado;
    }

    public void cancelar() {
        this.estado = ESTADO_VIAJE.CANCELADO;
    }

    public void ocuparPlazas(Long numero) {
        this.plazasDisponibles -= numero;
    }

    public void liberarPlazas(Long numero) {
        this.plazasDisponibles += numero;
    }
}