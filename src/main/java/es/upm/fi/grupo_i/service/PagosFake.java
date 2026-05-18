package es.upm.fi.grupo_i.service;

import org.springframework.stereotype.Service;

@Service
public class PagosFake {

    private Long secuenciaPagos = 1L;

    public Long procesarPago(Long viajeId, Long pasajeroId, int numeroPasajeros, String datosPago) {
        return secuenciaPagos++;
    }

    public boolean procesarDevolucion(Long reservaId) {
        return true;
    }

    public Double calcularImporteDevolucion(Long reservaId) {
        return 0.0;
    }
}