package es.upm.fi.grupo_i.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.repository.ViajeRepository;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final NotificacionesFake notificacionesFake;

    public ViajeService(ViajeRepository viajeRepository, NotificacionesFake notificacionesFake) {
        this.viajeRepository = viajeRepository;
        this.notificacionesFake = notificacionesFake;
    }

    // Todos los viajes
    public Page<Viaje> obtenerViajes(String destino, Pageable pageable) {
        Page<Viaje> viajes = (destino == null || destino.isBlank())
            ? viajeRepository.findAll(pageable)
            : viajeRepository.findByDestino(destino, pageable);

        return viajes;
    }

    // Viaje por ID
    public Optional<Viaje> obtenerViaje(Long id) {
        return viajeRepository.findById(id);
    }

    public Viaje obtenerViajeObligatorio(Long id) {
    return viajeRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No existe un viaje con id " + id
        ));
    }

    public Viaje crearViaje(Viaje viaje) {
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

        if (viaje.getEstado() != ESTADO_VIAJE.ACTIVO) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El estado del viaje debe ser ACTIVO al crearlo"
            );

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
        
        notificacionesFake.notificarViajeCreado(viaje.getId());
        return viajeRepository.save(viaje);
    }

    public void cancelarViaje(Long id) {
        Viaje viaje = viajeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException( //Si no lo encuentro 
                HttpStatus.NOT_FOUND,
                "No existe un viaje con id " + id
            ));

        viaje.setEstado(ESTADO_VIAJE.CANCELADO);
        notificacionesFake.notificarCancelacionViaje(id);
        viajeRepository.save(viaje);
    }

    public boolean hayPlazasDisponibles(Long idViaje, int numeroPasajeros) {
        Viaje viaje = obtenerViajeObligatorio(idViaje);
        return viaje.getPlazasDisponibles() >= numeroPasajeros;
    }

    public void ocuparPlazas(Long idViaje, int numeroPasajeros) {
        Viaje viaje = obtenerViajeObligatorio(idViaje);

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
        Viaje viaje = obtenerViajeObligatorio(idViaje);
        viaje.liberarPlazas(numeroPasajeros);
        viajeRepository.save(viaje);
    }
  
    public boolean comprobarViajeFinalizado(Long id) {
        Viaje viaje = viajeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe un viaje con id " + id
            ));

        return viaje.getEstado() == ESTADO_VIAJE.FINALIZADO;
    }
}