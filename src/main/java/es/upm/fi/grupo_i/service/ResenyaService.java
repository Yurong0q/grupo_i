package es.upm.fi.grupo_i.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.repository.ResenyaRepository;

@Service
public class ResenyaService {
    //TO-DO: Implementar el servicio de reseñas
    private final ResenyaRepository resenyaRepository;
    private final ViajeService viajeService;
    private final ReservaService reservaService;

    public ResenyaService(ResenyaRepository resenyaRepository, ViajeService viajeService, ReservaService reservaService) {
        this.resenyaRepository = resenyaRepository;
        this.viajeService = viajeService;
        this.reservaService = reservaService;
    }

    public Resenya registrarResenya(Long viajeId, Long autorId, int puntuacion, String comentario) {

        if (!viajeService.comprobarViajeFinalizado(viajeId)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No se puede reseñar un viaje que no ha finalizado");
        }

        if (!reservaService.comprobarReservaValida(viajeId, autorId)) {
            throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "El usuario no tiene una reserva válida para este viaje");
        }

        if (puntuacion < 0 || puntuacion > 10) {
            throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "La puntuación debe estar entre 0 y 10");
        }
        
        Resenya resenya = new Resenya(viajeId, autorId, puntuacion, comentario);
        resenyaRepository.save(resenya);

        return resenya;
    }
}
