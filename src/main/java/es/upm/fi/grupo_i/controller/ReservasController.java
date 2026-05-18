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

import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.service.ReservaService;

@RestController
@RequestMapping("/usuarios/{usuario-id}")
public class ReservasController {

    private final ReservaService reservaService;

    public ReservasController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/reservas/{id}")
    public Reserva obtenerReserva(@PathVariable Long id) {
        return reservaService.obtenerReserva(id);
    }

    @GetMapping("/reservas")
    public Page<Reserva> obtenerReservasUsuario(
        @PathVariable("usuario-id") Long usuarioId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);

        return reservaService.obtenerReservasUsuario(usuarioId, pageable);
    }

    @PostMapping("/reservas")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva procesarReserva(@PathVariable Long viajeId, Long pasajeroId, int numPasajeros, String datosPago) {
        return reservaService.procesarReserva(viajeId, pasajeroId, numPasajeros, datosPago);
    }

    @DeleteMapping("/reservas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Reserva cancelarReserva(@PathVariable Long id) {
        return reservaService.cancelarReserva(id);
    }
}