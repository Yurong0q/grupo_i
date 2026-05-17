package es.upm.fi.grupo_i.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.service.ViajeService;

@RestController
public class ViajeController {
    
    private final ViajeService viajeService;
    
    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/viajes")
    public List<Viaje> obtenerViajes() {
        return viajeService.obtenerViajes();
    }

    @GetMapping("/viajes/{id}")
    public Viaje obtenerViaje(@PathVariable Long id) {
        return viajeService.obtenerViaje(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe un viaje con id " + id
            ));
    }

    @PostMapping("/viajes")
    @ResponseStatus(HttpStatus.CREATED)
    public Viaje crearViaje(@RequestBody Viaje viaje) {
        return viajeService.crearViaje(viaje);
    }

    @DeleteMapping("/viajes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarViaje(@PathVariable Long id) {
        viajeService.cancelarViaje(id);
    }

}
