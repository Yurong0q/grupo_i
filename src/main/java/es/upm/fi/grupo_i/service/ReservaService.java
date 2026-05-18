package es.upm.fi.grupo_i.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.enums.ESTADO_RESERVA;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.repository.ReservasRepository;

@Service
public class ReservaService { 
    private final ReservasRepository reservasRepository;
    private final ViajeService viajeService;
    private final PagosFake pagosFake;
    private final NotificacionesFake notificacionesFake;

    public ReservaService(
        ReservasRepository reservasRepository,
        ViajeService viajeService,
        PagosFake pagosFake,
        NotificacionesFake notificacionesFake
    ) {
        this.reservasRepository = reservasRepository;
        this.viajeService = viajeService;
        this.pagosFake = pagosFake;
        this.notificacionesFake = notificacionesFake;
    }

    public Reserva obtenerReserva(Long id) {
        return reservasRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe una reserva con id " + id
            ));
    }

    public Reserva procesarReserva(Long viajeId, Long pasajeroId, int numPasajeros, String datosPago) {
        
        if (viajeId == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El viajeId es obligatorio"
            );
        }

        if (pasajeroId == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El pasajeroId es obligatorio"
            );
        }

        if (numPasajeros <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El numero de pasajeros debe ser mayor que 0"
            );
        }
        int plazasDisponibles = viajeService.comprobarDisponibilidad(viajeId);
        if (plazasDisponibles < numPasajeros){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No hay plazas disponibles para ese viaje"
            );
        }

        if (viajeService.comprobarConductorDistintoDePasajero(viajeId, pasajeroId)){
             throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El conductor no puede ser el pasajero"
            );
        }
        
        Reserva reserva = new Reserva(viajeId, pasajeroId, numPasajeros);
        //Se crea como provisional
        reservasRepository.save(reserva);
        Long idPago = pagosFake.procesarPago(viajeId, pasajeroId, numPasajeros, datosPago);
        reserva.asociarPago(idPago);
        reserva.confirmar();
        reservasRepository.save(reserva);
        viajeService.ocuparPlazas(viajeId , numPasajeros);
        notificacionesFake.notificarReservaConfirmada(reserva.getId());
        return reserva;
    }

    public Reserva cancelarReserva(Long reservaId) {
        Reserva reserva = obtenerReserva(reservaId);
        if (!reserva.esCancelable()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La reserva no se puede cancelar"
            );
        }
        reserva.cancelar();
        reservasRepository.save(reserva);
        pagosFake.procesarDevolucion(reservaId);
        viajeService.liberarPlazas(reserva.getViajeId(),reserva.getNumeroPasajeros());
        notificacionesFake.notificarCancelacionReserva(reservaId);
        return reserva;
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

    public Page<Reserva> obtenerReservasUsuario(Long usuarioId, Pageable pageable) {
        Page<Reserva> reservas = reservasRepository.findByPasajeroId(usuarioId, pageable);
        return reservas;
    }

    public List<Reserva> obtenerReservasPorViaje(Long viajeId){
        return reservasRepository.findByViajeId(viajeId);
    }
}