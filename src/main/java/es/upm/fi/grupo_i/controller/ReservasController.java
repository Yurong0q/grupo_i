package es.upm.fi.grupo_i.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.upm.fi.grupo_i.dto.ReservaDto;
import es.upm.fi.grupo_i.mapper.ReservaMapper;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.service.ReservaService;

@RestController
@RequestMapping("/usuarios/{usuario-id}")
public class ReservasController {

    private final ReservaService reservaService;
    private final ReservaMapper reservaMapper;

    public ReservasController(ReservaService reservaService, ReservaMapper reservaMapper) {
        this.reservaService = reservaService;
        this.reservaMapper = reservaMapper;
    }

    @GetMapping("/reservas/{id}")
    public ReservaDto obtenerReserva(@PathVariable Long id) {
        Reserva reserva = reservaService.obtenerReserva(id);
        return reservaMapper.toDto(reserva);
    }

    @GetMapping("/reservas")
    public Page<ReservaDto> obtenerReservasUsuario(
        @PathVariable("usuario-id") Long usuarioId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);

        Page<Reserva> reservas= reservaService.obtenerReservasUsuario(usuarioId, pageable);
        return reservas.map(reservaMapper::toDto);
    }

    @PostMapping("/reservas")
    @ResponseStatus(HttpStatus.CREATED)
    public void procesarReserva(Long viajeId, Long pasajeroId, int numPasajeros, String datosPago) {
        reservaService.procesarReserva(viajeId, pasajeroId, numPasajeros, datosPago);
    }

    @DeleteMapping("/reservas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
    }
}