package es.upm.fi.grupo_i.model;


import java.time.LocalDateTime;

import es.upm.fi.grupo_i.enums.TIPO_NOTIFICACION;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuario_id;
    private TIPO_NOTIFICACION tipo; // Por ejemplo: "RESERVA_CONFIRMADA", "VIAJE_CANCELADO", etc.
    private String mensaje;
    private LocalDateTime fechaCreacion;

    private Notificacion() {
        // Constructor vacío para JPA
    }

    private Notificacion(Long usuario_id, TIPO_NOTIFICACION tipo, String mensaje, LocalDateTime fechaCreacion) {
        this.usuario_id = usuario_id;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public TIPO_NOTIFICACION getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

}
