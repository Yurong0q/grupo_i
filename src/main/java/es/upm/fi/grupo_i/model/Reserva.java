package es.upm.fi.grupo_i.model;

import java.time.LocalDate;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long viajeId;
    private Long pasajeroId;
    private int numeroPasajeros;
    private LocalDate fechaDeCreacion;
    private ESTADO_RESERVA estado;
    private Long idPago;

    protected Reserva() {
        // Constructor vacío para JPA
    }

    public Reserva(Long viajeId, Long pasajeroId, int numeroPasajeros, LocalDate fechaDeCreacion) {
        this.viajeId = viajeId;
        this.pasajeroId = pasajeroId;
        this.numeroPasajeros = numeroPasajeros;
        this.fechaDeCreacion = fechaDeCreacion;
        this.estado = ESTADO_RESERVA.PROVISIONAL; // Por defecto, la reserva se crea como provisional
    }


    public Long getId() {
        return id;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public Long getPasajeroId() {
        return pasajeroId;
    }

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public ESTADO_RESERVA getEstado() {
        return estado;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setEstado(ESTADO_RESERVA estado) {
        this.estado = estado;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }
}
