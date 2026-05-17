package es.upm.fi.grupo_i.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import es.upm.fi.grupo_i.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { 
    Optional<Usuario> findByEmail(String email);
}