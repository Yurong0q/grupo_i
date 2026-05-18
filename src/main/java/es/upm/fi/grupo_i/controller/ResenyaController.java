package es.upm.fi.grupo_i.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.service.ResenyaService;

@RestController
public class ResenyaController {
    private final ResenyaService resenyaService;

    public ResenyaController(ResenyaService resenyaService) {
        this.resenyaService = resenyaService;
    }


    @PostMapping("/resenyas")
    public Resenya registrarResenya(Long viajeId, Long autorId, int puntuacion, String comentario) {
        return resenyaService.registrarResenya(viajeId, autorId, puntuacion, comentario);
    }
}