package es.upm.fi.grupo_i.service;

import org.springframework.stereotype.Service;

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
            throw new IllegalStateException("No se puede reseñar un viaje que no ha finalizado");
        }

        if (!reservaService.comprobarReservaValida(viajeId, autorId)) {
            throw new IllegalStateException("El usuario no tiene una reserva válida para este viaje");
        }
        Resenya resenya = new Resenya(viajeId, autorId, puntuacion, comentario);
        resenyaRepository.save(resenya);

        return resenya;
    }
}
