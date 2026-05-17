package es.upm.fi.grupo_i.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.model.Usuario;
import es.upm.fi.grupo_i.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.signUp(usuario);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario login(@RequestBody Usuario usuario) {
        return usuarioService.logIn(usuario.getEmail(), usuario.getPassword());
    }

    @GetMapping("/{usuario-id}/reservas")
    public List<Reserva> obtenerReservasUsuario(@PathVariable("usuario-id") Long usuarioId) {
        return usuarioService.obtenerReservasUsuario(usuarioId);
    }

    @GetMapping("/{usuario-id}/resenyas")
    public List<Resenya> obtenerResenyasUsuario(@PathVariable("usuario-id") Long usuarioId) {
        return usuarioService.obtenerResenyasUsuario(usuarioId);
    }
}