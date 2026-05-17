package es.upm.fi.grupo_i.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.model.Usuario;
import es.upm.fi.grupo_i.repository.UsuarioRepository;

@Service
public class UsuarioService { //TODO implementar Jwt

    private final UsuarioRepository usuarioRepository;
    private final ReservaService reservaService;
    private final ResenyaService resenyaService;

    public UsuarioService(UsuarioRepository usuarioRepository, ReservaService reservaService, ResenyaService resenyaService) {
        this.usuarioRepository = usuarioRepository;
        this.reservaService = reservaService;
        this.resenyaService = resenyaService;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarioRepository.findAll());
    }

    public Usuario signUp(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank() || !usuario.getEmail().contains("@")) {
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

    public List<Reserva> obtenerReservasUsuario(Long usuarioId) {
        return reservaService.obtenerReservasUsuario(usuarioId);
    }

    public List<Resenya> obtenerResenyasUsuario(Long usuarioId) {
        return resenyaService.obtenerResenyasUsuario(usuarioId);
    }
}