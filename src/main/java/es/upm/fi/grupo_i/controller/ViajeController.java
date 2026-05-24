package es.upm.fi.grupo_i.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import es.upm.fi.grupo_i.dto.ViajeCreateDto;
import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.mapper.ViajeMapper;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.service.ReservaService;
import es.upm.fi.grupo_i.service.ViajeService;

@RestController
public class ViajeController {
    
    private final ViajeService viajeService;
    private final ViajeMapper viajeMapper;
    private final ReservaService reservaService;
    
    public ViajeController(ViajeService viajeService, ViajeMapper viajeMapper, ReservaService reservaService) {
        this.viajeService = viajeService;
        this.viajeMapper = viajeMapper;
        this.reservaService = reservaService;
    }

    @GetMapping("/viajes")
    public Page<ViajeDto> obtenerViajes(
        @RequestParam(required = false) String destino,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return viajeService.obtenerViajes(destino, pageable).map(viajeMapper::toDto);
    }

    @GetMapping("/viajes/{id}")
    public ViajeDto obtenerViaje(@PathVariable Long id) {
        Viaje viaje = viajeService.obtenerViaje(id);
        return viajeMapper.toDto(viaje);
    }

    @PostMapping("/viajes")
    @ResponseStatus(HttpStatus.CREATED)
    public ViajeDto crearViaje(@RequestBody ViajeCreateDto dto) {
        Viaje viaje = viajeMapper.toEntity(dto);
        Viaje creado = viajeService.crearViaje(viaje);
        return viajeMapper.toDto(creado);
    }

    
    @DeleteMapping("/viajes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ViajeDto cancelarViaje(@PathVariable Long id) {
        Viaje cancelado = viajeService.cancelarViaje(id, reservaService);
        return viajeMapper.toDto(cancelado);
    }

}
