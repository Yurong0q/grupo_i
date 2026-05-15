package es.upm.fi.grupo_i.model;

import es.upm.fi.grupo_i.enums.DIVISA;
import jakarta.persistence.Embeddable;

@Embeddable
public class Precio {

    private float cantidad;
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
