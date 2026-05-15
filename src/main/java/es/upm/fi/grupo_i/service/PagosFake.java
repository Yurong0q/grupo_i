package es.upm.fi.grupo_i.service;

import org.springframework.stereotype.Service;

@Service
public class PagosFake {

    private Long secuenciaPagos = 1L;

    public Long procesarPago(Long viajeId, Long pasajeroId, Long numeroPasajeros) {
        return secuenciaPagos++;
    }

    public boolean procesarDevolucion(Long idReserva) {
        return true;
    }

    public Double calcularImporteDevolucion() {
        return 0.0;
    }
}