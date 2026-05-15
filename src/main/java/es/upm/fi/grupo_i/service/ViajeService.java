package es.upm.fi.grupo_i.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.enums.ESTADO_VIAJE;
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
        return new ArrayList<>(viajeRepository.findAll());
    }

    // Viaje por ID
    public Optional<Viaje> obtenerViaje(Long id) {
        return viajeRepository.findById(id);
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

        viaje.setEstado(ESTADO_VIAJE.CANCELADO);
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