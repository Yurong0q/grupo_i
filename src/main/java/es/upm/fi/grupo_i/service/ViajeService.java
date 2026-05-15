package es.upm.fi.grupo_i.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.repository.ViajeRepository;

@Service
public class ViajeService {

    private final ViajeRepository ViajeRepository;

    public ViajeService(ViajeRepository ViajeRepository) {
        this.ViajeRepository = ViajeRepository;
    }

    // Todos los viajes
    public List<Viaje> obtenerTodosViajes() {
        return new ArrayList<>(ViajeRepository.findAll());
    }

    public Page<Viaje> obtenerTodosViajes(String nombre, Pageable pageable) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            return ViajeRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        return ViajeRepository.findAll(pageable);
    }

    public Optional<Viaje> obtenerViaje(long id) {
        return ViajeRepository.findById(id);
    }

    public void anadirViaje(Viaje viaje) throws Exception {
        if (viaje.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Un viaje nuevo no debe tener ID"
            );
        }
        ViajeRepository.save(viaje);
    }
}