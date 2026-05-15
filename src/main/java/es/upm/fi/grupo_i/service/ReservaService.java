package es.upm.fi.grupo_i.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.repository.ReservasRepository;

@Service
public class ReservaService {
    private final ReservasRepository reservasRepository;

    public ReservaService(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    public boolean comprobarReservaValida(Long viajeId, Long autorId) {
        // Implementar lógica para comprobar si la reserva con el ID dado está cancelada
        List<Reserva> reservaLista = reservasRepository.findByViajeIdAndPasajeroId(viajeId, autorId);
        if (reservaLista == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe una reserva con id de usuario " + autorId + " y id de viaje " + viajeId 
            );
        }
        return reservaLista.get(0).getEstado() == ESTADO_RESERVA.FINALIZADA;
    }
}
