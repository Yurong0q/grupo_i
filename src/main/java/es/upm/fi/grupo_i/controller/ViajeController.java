package es.upm.fi.grupo_i.controller;

import java.time.LocalDate;
import java.util.List;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.dto.ViajeDto;
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
