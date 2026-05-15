package es.upm.fi.grupo_i.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacionesFake {

    public void notificarReservaConfirmada(Long idReserva) {
        System.out.println("Notificacion fake: reserva confirmada " + idReserva);
    }

    public void notificarCancelacionReserva(Long idReserva) {
        System.out.println("Notificacion fake: reserva cancelada " + idReserva);
    }

    public void notificarCancelacionViaje(Long idViaje) {
        System.out.println("Notificacion fake: viaje cancelado " + idViaje);
    }

    public void notificarViajeCreado(Long idViaje) {
        System.out.println("Notificacion fake: viaje creado " + idViaje);
    }
}