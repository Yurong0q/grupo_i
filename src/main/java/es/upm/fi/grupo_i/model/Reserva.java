package es.upm.fi.grupo_i.model;

import java.time.LocalDateTime;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    
    @Column(name = "viaje_id")
    private Long viajeId;

    @Column(name = "pasajero_id")
    private Long pasajeroId;

    @Column(name = "numero_pasajeros")
    private int numeroPasajeros;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private ESTADO_RESERVA estado;

    @Column(name = "id_pago")
    private Long idPago;

    protected Reserva() {
        // Constructor vacío para JPA
    }

    public Reserva(Long viajeId, Long pasajeroId, int numeroPasajeros) {
        this.viajeId = viajeId;
        this.pasajeroId = pasajeroId;
        this.numeroPasajeros = numeroPasajeros;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = ESTADO_RESERVA.PROVISIONAL; // Por defecto lo marco como provisional
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public ESTADO_RESERVA getEstado() {
        return estado;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void marcarProvisional() {
        this.estado = ESTADO_RESERVA.PROVISIONAL;
    }

    public void confirmar() {
        this.estado = ESTADO_RESERVA.CONFIRMADA;
    }

    public void cancelar() {
        this.estado = ESTADO_RESERVA.CANCELADA;
    }

    public boolean esCancelable() {
        return this.estado == ESTADO_RESERVA.PROVISIONAL
            || this.estado == ESTADO_RESERVA.CONFIRMADA;
    }

    public void asociarPago(Long idPago) {
        this.idPago = idPago;
    }
}
