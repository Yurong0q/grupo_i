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

    public Reserva procesarReserva(Reserva peticion) {
        if (peticion.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Una reserva nueva no debe tener ID"
            );
        }

        if (peticion.getViajeId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El viajeId es obligatorio"
            );
        }

        if (peticion.getPasajeroId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El pasajeroId es obligatorio"
            );
        }

        if (peticion.getPasajeroId().equals(viajeService.obtenerViajeObligatorio(peticion.getViajeId()).getConductorId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El pasajero no puede ser el conductor del viaje"
            );
        }

        if (peticion.getNumeroPasajeros() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El numero de pasajeros debe ser mayor que 0"
            );
        }

        if (!viajeService.hayPlazasDisponibles(
                peticion.getViajeId(),
                peticion.getNumeroPasajeros())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No hay plazas disponibles para ese viaje"
            );
        }

        Reserva reserva = new Reserva(peticion.getViajeId(),peticion.getPasajeroId(),peticion.getNumeroPasajeros());

        reserva.marcarProvisional();
        reservasRepository.save(reserva);
        Long idPago = pagosFake.procesarPago(reserva.getViajeId(), reserva.getPasajeroId(), reserva.getNumeroPasajeros());
        reserva.asociarPago(idPago);
        reserva.confirmar();
        reservasRepository.save(reserva);
        viajeService.ocuparPlazas(reserva.getViajeId(),reserva.getNumeroPasajeros());
        notificacionesFake.notificarReservaConfirmada(reserva.getId());
        return reserva;
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = obtenerReserva(id);

        if (!reserva.esCancelable()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La reserva no se puede cancelar"
            );
        }

        reserva.cancelar();
        reservasRepository.save(reserva);
        pagosFake.procesarDevolucion(id);
        viajeService.liberarPlazas(reserva.getViajeId(),reserva.getNumeroPasajeros());
        notificacionesFake.notificarCancelacionReserva(id);
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
}