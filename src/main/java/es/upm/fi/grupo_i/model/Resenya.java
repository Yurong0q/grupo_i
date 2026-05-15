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
    private Long viaje_id;
    private Long autor_id;
    private int puntuacion;
    private String comentario;

    private Resenya() {
        // Constructor vacío para JPA
    }

    private Resenya(Long viaje_id, Long autor_id, int puntuacion, String comentario) {
        this.viaje_id = viaje_id;
        this.autor_id = autor_id;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public Long getViaje_id() {
        return viaje_id;
    }

    public Long getAutor_id() {
        return autor_id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }
}
