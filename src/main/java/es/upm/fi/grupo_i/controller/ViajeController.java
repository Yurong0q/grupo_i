package es.upm.fi.grupo_i.controller;

import java.time.LocalDate;
import java.util.List;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.service.ViajeService;

@RestController
public class ViajeController {
    ViajeService viajeService;
    
    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/viajes")
    // GET /viajes?origen=Madrid&destino=Tres_Cantos&fecha=21-05-2026&limit=20&offset=0
    public Page<ViajeDto> getViajesPage(
        @RequestParam(required = false) String origen,
        @RequestParam(required = false) String destino,
        @RequestParam(required = false) LocalDate fecha,
        @RequestParam(defaultValue = "20") int limit,
        @RequestParam(defaultValue = "0") int offset,
        @PageableDefault(size = 20, page = 0) Pageable pageable){

        Page<Viaje> viajes = viajeService.buscarViajes(origen, destino, fecha, pageable);
        return viajes.map(viajeMapper::toDto);
        }

    @GetMapping("/viajes/{id}")
    ViajeDto getViaje(@PathVariable Long id) throws ResponseStatusException {
        Viaje viaje = viajeService.getViaje(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No existe un viaje con id " + id
            ));


        return viajeMapper.toDto(viaje);
    }

    @PostMapping("/viajes")
    void crearViaje(@RequestBody Viaje viaje) throws ResponseStatusException {
        viajeService.crearViaje(viaje);
      
    }

}
