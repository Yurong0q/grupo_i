package es.upm.fi.grupo_i.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.fi.grupo_i.dto.ResenyaDto;
import es.upm.fi.grupo_i.mapper.ResenyaMapper;
import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.service.ResenyaService;

@RestController
@RequestMapping("/usuarios/{usuario-id}")
public class ResenyaController {
    private final ResenyaService resenyaService;
    private final ResenyaMapper resenyaMapper;

    public ResenyaController(ResenyaService resenyaService, ResenyaMapper resenyaMapper) {
        this.resenyaService = resenyaService;
        this.resenyaMapper = resenyaMapper;
    }

    @PostMapping("/resenyas")
    public void registrarResenya(Long viajeId, @PathVariable("usuario-id") Long autorId, int puntuacion, String comentario) {
        resenyaService.registrarResenya(viajeId, autorId, puntuacion, comentario);
    }

    //Paginacion de resenyas de un usuario
    @GetMapping("/resenyas")
    public Page<ResenyaDto> obtenerResenyasUsuario(
        @PathVariable("usuario-id") Long usuarioId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Resenya> resenyas = resenyaService.obtenerResenyasUsuario(usuarioId, pageable);

        return resenyas.map(resenyaMapper::toDto);
    }
}