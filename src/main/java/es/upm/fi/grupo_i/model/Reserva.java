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
    private Long viaje_id;
    private Long pasajero_id;
    private int numero_pasajeros;
    private LocalDate fechaDeCreacion;
    private ESTADO_RESERVA estado;
    private Long idPago;

    private Reserva() {
        // Constructor vacío para JPA
    }

    private Reserva(Long viaje_id, Long pasajero_id, int numero_pasajeros, LocalDate fechaDeCreacion) {
        this.viaje_id = viaje_id;
        this.pasajero_id = pasajero_id;
        this.numero_pasajeros = numero_pasajeros;
        this.fechaDeCreacion = fechaDeCreacion;
        this.estado = ESTADO_RESERVA.PROVISIONAL; // Por defecto, la reserva se crea como provisional
    }


    public Long getId() {
        return id;
    }

    public Long getViaje_id() {
        return viaje_id;
    }

    public Long getPasajero_id() {
        return pasajero_id;
    }

    public int getNumero_pasajeros() {
        return numero_pasajeros;
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
