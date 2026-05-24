package es.upm.fi.grupo_i.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{usuario-id}")
    public Usuario cambiarPassword(@PathVariable("usuario-id") Long usuarioId, @RequestBody Usuario usuario) {
        return usuarioService.cambiarPassword(usuarioId, usuario.getPassword());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody Usuario usuario) {
        usuarioService.signUp(usuario);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public void login(@RequestBody Usuario usuario) {
        usuarioService.logIn(usuario.getEmail(), usuario.getPassword());
    }

}