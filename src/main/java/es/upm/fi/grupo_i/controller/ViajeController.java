package es.upm.fi.grupo_i.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Page<Viaje> obtenerViajes(
        @RequestParam(required = false) String destino,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return viajeService.obtenerViajes(destino, pageable);
    }

    @GetMapping("/viajes/{id}")
    public Viaje obtenerViaje(@PathVariable Long id) {
        return viajeService.obtenerViaje(id);
    }

    @PostMapping("/viajes")
    @ResponseStatus(HttpStatus.CREATED)
    public Viaje crearViaje(@RequestBody Viaje viaje) {
        return viajeService.crearViaje(viaje);
    }

    
    @DeleteMapping("/viajes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Viaje cancelarViaje(@PathVariable Long id) {
        return viajeService.cancelarViaje(id);
    }

}
