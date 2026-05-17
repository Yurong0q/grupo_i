package es.upm.fi.grupo_i.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nombre;
    private String password;

    protected Usuario() {
      // Constructor vacío para JPA
    }

    public Usuario(String email, String password, String nombre) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
    }

    public Long getId() { 
        return id; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getPassword() { 
        return password; 
    }
    public String getNombre() { 
        return nombre; 
    }

    protected void cambiarPassword(String password) { //TODO revisar
        this.password = password; 
    }
}