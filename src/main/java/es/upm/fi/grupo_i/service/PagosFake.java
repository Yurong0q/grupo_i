package es.upm.fi.grupo_i.service;

import org.springframework.stereotype.Service;

@Service
public class PagosFake {

    private Long secuenciaPagos = 1L;

    public Long procesarPago(Long viajeId, Long pasajeroId, int numeroPasajeros, String datosPago) { // Para comprar reserva
        return secuenciaPagos++;
    }

    public boolean procesarDevolucion(Long reservaId) {
        return true;
    }

    public boolean realizarPagoCorrespondiente(Long importe, Long idConductor, String datosPago){ // Para cancelar viaje
        return true;
    }

    public Double calcularImporteDevolucion(Long reservaId) {
        return 0.0;
    }
}