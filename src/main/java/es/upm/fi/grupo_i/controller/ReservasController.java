package es.upm.fi.grupo_i.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.service.ReservasService;

@RestController
public class ReservasController {

    private final ReservasService reservasService;

    public ReservasController(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    @GetMapping("/reservas/{id}")
    public Reserva obtenerReserva(@PathVariable Long id) {
        return reservasService.obtenerReserva(id);
    }

    @PostMapping("/reservas")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservasService.procesarReserva(reserva);
    }

    @DeleteMapping("/reservas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarReserva(@PathVariable Long id) {
        reservasService.cancelarReserva(id);
    }
}