package org.alexyivan.modelo.entidad;

public class CompraEntidad {
    private long id;
    private long idUsuario;
    private long idJuego;
    private float precioSinDescuento;
    private int descuentoAplicado;

    // Constructor
    public CompraEntidad(long id, long idUsuario, long idJuego,
                         float precioSinDescuento, int descuentoAplicado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public float getPrecioSinDescuento() {
        return precioSinDescuento;
    }

    public void setPrecioSinDescuento(float precioSinDescuento) {
        this.precioSinDescuento = precioSinDescuento;
    }

    public int getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(int descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }
}
