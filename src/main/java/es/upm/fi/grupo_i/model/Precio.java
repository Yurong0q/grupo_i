package es.upm.fi.grupo_i.model;

import es.upm.fi.grupo_i.enums.DIVISA;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Precio {

    private float cantidad;
    
    @Enumerated(EnumType.STRING)
    private DIVISA moneda;

    protected Precio(){
    }
    
    public Precio(float cantidad, DIVISA moneda) {
        this.cantidad = cantidad;
        this.moneda = moneda;
    }

    public float getCantidad() {
        return cantidad;
    }
    
    public DIVISA getMoneda() {
        return moneda;
    }

}
