package es.upm.fi.grupo_i.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.repository.ViajeRepository;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;

    public ViajeService(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    // Todos los viajes
    public List<Viaje> obtenerViajes() {
        return viajeRepository.findAll();
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

        return viajeRepository.save(viaje);
    }

    public void cancelarViaje(Long id) {
        Viaje viaje = viajeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException( //Si no lo encuentro 
                HttpStatus.NOT_FOUND,
                "No existe un viaje con id " + id
            ));

        viaje.cancelar();
        viajeRepository.save(viaje);
    }

    public boolean hayPlazasDisponibles(Long idViaje, Long numeroPasajeros) {
        Viaje viaje = obtenerViajeObligatorio(idViaje);
        return viaje.getPlazasDisponibles() >= numeroPasajeros;
    }

    public void ocuparPlazas(Long idViaje, Long numeroPasajeros) {
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

    public void liberarPlazas(Long idViaje, Long numeroPasajeros) {
        Viaje viaje = obtenerViajeObligatorio(idViaje);
        viaje.liberarPlazas(numeroPasajeros);
        viajeRepository.save(viaje);
    }
}