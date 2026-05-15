package es.upm.fi.grupo_i;

public class Precio {

    private float cantidad;
    private String moneda;

    public Precio(float cantidad, String moneda) {
        this.cantidad = cantidad;
        this.moneda = moneda;
    }

    public float getCantidad() {
        return cantidad;
    }
    
    public String getMoneda() {
        return moneda;
    }

}
