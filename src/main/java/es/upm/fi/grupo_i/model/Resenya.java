package es.upm.fi.grupo_i.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resenyas")
public class Resenya {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long viajeId;
    private Long autorId;
    private int puntuacion;
    private String comentario;

    protected Resenya() {
        // Constructor vacío para JPA
    }

    public Resenya(Long viajeId, Long autorId, int puntuacion, String comentario) {
        this.viajeId = viajeId;
        this.autorId = autorId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }
}
