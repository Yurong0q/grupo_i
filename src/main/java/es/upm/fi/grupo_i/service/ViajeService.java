package es.upm.fi.grupo_i.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.repository.ViajeRepository;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final NotificacionesFake notificacionesFake;
    private final PagosFake pagosFake;

    public ViajeService(
        ViajeRepository viajeRepository,  
        NotificacionesFake notificacionesFake, 
        PagosFake pagosFake) {

        this.viajeRepository = viajeRepository;
        this.notificacionesFake = notificacionesFake;
        this.pagosFake = pagosFake;
    }

    // Todos los viajes
    public Page<Viaje> obtenerViajes(String destino, Pageable pageable) {
        Page<Viaje> viajes = (destino == null || destino.isBlank())? 
            viajeRepository.findAll(pageable): 
            viajeRepository.findByDestino(destino, pageable);

        return viajes;
    }

    public Viaje obtenerViaje(Long id) {
    return viajeRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No existe un viaje con id " + id
        ));
    }

    public void crearViaje(Viaje viaje) {
        if (viaje.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Un viaje nuevo no debe tener ID"
            );
        }

        if (viaje.getConductorId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Conductor invalido"
            );
        }

        if (viaje.getEstado() == null) {
            viaje.setEstado(ESTADO_VIAJE.ACTIVO);
        }

        if (viaje.getOrigen() == null || viaje.getOrigen().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El origen es obligatorio"
            );
        }

        if (viaje.getDestino() == null || viaje.getDestino().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El destino es obligatorio"
            );
        }

        if (viaje.getDestino().equals(viaje.getOrigen())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El origen y el destino no pueden ser iguales"
            );
        }

        if (viaje.getDuracionEstimada() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La duración estimada debe ser un valor positivo"
            );
        }    

        if (viaje.getPlazasDisponibles() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Las plazas disponibles deben ser un valor positivo"
            );
        }

        if (viaje.getPrecio() == null || viaje.getPrecio().getCantidad() < 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El precio es obligatorio y no puede ser negativo"
            );

        }
        
        Viaje creado = viajeRepository.save(viaje);
        notificacionesFake.notificarViajeCreado(creado.getId());
    }

    public Viaje cancelarViaje(Long viajeId, ReservaService reservaService) {
        Viaje viaje = obtenerViaje(viajeId);

        List<Reserva> reservas = reservaService.obtenerReservasPorViaje(viajeId);
        
        for (Reserva reserva:reservas){
            pagosFake.calcularImporteDevolucion(viajeId);
            pagosFake.realizarPagoCorrespondiente(viajeId, viaje.getConductorId(), null);
            pagosFake.procesarDevolucion(viajeId);
            reservaService.cancelarReserva(reserva.getId());
        }
        
        viaje.setEstado(ESTADO_VIAJE.CANCELADO);
        notificacionesFake.notificarCancelacionViaje(viajeId);
        viajeRepository.save(viaje);
        return viaje;
    }

    public boolean hayPlazasDisponibles(Long idViaje, int numeroPasajeros) {
        Viaje viaje = obtenerViaje(idViaje);
        return viaje.getPlazasDisponibles() >= numeroPasajeros;
    }

    public void ocuparPlazas(Long idViaje, int numeroPasajeros) {
        Viaje viaje = obtenerViaje(idViaje);

        if (viaje.getPlazasDisponibles() < numeroPasajeros) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No hay plazas suficientes en el viaje " + idViaje
            );
        }

        viaje.ocuparPlazas(numeroPasajeros);
        viajeRepository.save(viaje);
    }

    public void liberarPlazas(Long idViaje, int numeroPasajeros) {
        Viaje viaje = obtenerViaje(idViaje);
        viaje.liberarPlazas(numeroPasajeros);
        viajeRepository.save(viaje);
    }

    public boolean comprobarViajeFinalizado(Long idViaje) {
        Viaje viaje = obtenerViaje(idViaje);
        return viaje.getEstado() == ESTADO_VIAJE.FINALIZADO;
    }

    public int comprobarDisponibilidad(Long viajeId){
        Viaje viaje = obtenerViaje(viajeId);
        return viaje.getPlazasDisponibles();
    }

    public boolean comprobarConductorDistintoDePasajero(Long viajeId, Long pasajeroId){
        Viaje viaje = obtenerViaje(viajeId);
        return viaje.getConductorId().equals(pasajeroId);
    }
}