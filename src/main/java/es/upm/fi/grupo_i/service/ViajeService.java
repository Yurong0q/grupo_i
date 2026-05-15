package es.upm.fi.grupo_i.service;

import java.time.LocalDate;
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

    public Page<Viaje> buscarViajes(String origen, String destino, LocalDate fecha, Pageable pageable) {
        if (origen != null && !origen.trim().isEmpty() && destino != null && !destino.trim().isEmpty()) {
            return ViajeRepository.findByOrigenContainingIgnoreCase(origen, destino, fecha, pageable); 
        }
        return ViajeRepository.findAll(pageable);
    }

    public Optional<Viaje> getViaje(long id) {
        return ViajeRepository.findById(id);
    }

    public void crearViaje(Viaje viaje) throws Exception {
        if (viaje.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Un viaje nuevo no debe tener ID"
            );
        }
        ViajeRepository.save(viaje);
    }

    public void cancelar(){ //TODO

    }
}