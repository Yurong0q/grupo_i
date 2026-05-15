package es.upm.fi.grupo_i.controller;

import org.springframework.web.bind.annotation.PostMapping;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.service.ResenyaService;

public class ResenyaController {
    private final ResenyaService resenyaService;

    public ResenyaController(ResenyaService resenyaService) {
        this.resenyaService = resenyaService;
    }

    @PostMapping("/resenyas")
    public Resenya registrarResenya(Long usuarioId, Long viajeId, int puntuacion, String comentario) {
        return resenyaService.registrarResenya(viajeId, usuarioId, puntuacion, comentario);
    }
}
