package es.upm.fi.grupo_i.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import es.upm.fi.grupo_i.model.Usuario;
import es.upm.fi.grupo_i.repository.UsuarioRepository;

@Service
public class UsuarioService { 

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarioRepository.findAll());
    }

    public Usuario signUp(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email no es válido");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario logIn(String email, String password) { 
        if (email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email y contraseña son obligatorios");
        }
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!usuario.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
        return usuario;
    }

    public Usuario cambiarPassword(Long usuarioId, String nuevaPassword) {
        if (nuevaPassword == null || nuevaPassword.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La nueva contraseña es obligatoria");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuario.cambiarPassword(nuevaPassword);
        return usuarioRepository.save(usuario);
    }

}