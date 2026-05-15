package es.upm.fi.grupo_i.service;

import es.upm.fi.grupo_i.model.Resenya;
import es.upm.fi.grupo_i.repository.ResenyaRepository;

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

    public Resenya registrarResenya(Long viajeId, Long usuarioId, int puntuacion, String comentario) {

        if (!viajeService.comprobarViajeFinalizado(viajeId)) {
            throw new IllegalStateException("No se puede reseñar un viaje que no ha finalizado");
        }

        if (!reservaService.comprobarReservaValida(viajeId, usuarioId)) {
            throw new IllegalStateException("El usuario no tiene una reserva válida para este viaje");
        }
        Resenya resenya = new Resenya(viajeId, usuarioId, puntuacion, comentario)
        resenyaRepository.save(resenya);

        return resenya;
    }
}
