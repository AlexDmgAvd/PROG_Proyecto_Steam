package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoCompraEnum;
import org.alexyivan.modelo.enums.MetodoPagoEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CompraEntidad {
    private long id;
    private long idUsuario;
    private long idJuego;
    private MetodoPagoEnum metodoDePago;
    private float precioSinDescuento;
    private int descuentoAplicado;
    private EstadoCompraEnum estado;
    private LocalDateTime fechaCompra;

    // Constructor
    public CompraEntidad(long id, long idUsuario, long idJuego, MetodoPagoEnum metodoDePago,
                         float precioSinDescuento, int descuentoAplicado, EstadoCompraEnum estado,
                         LocalDateTime fechaCompra) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.metodoDePago = metodoDePago;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
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

    public MetodoPagoEnum getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(MetodoPagoEnum metodoDePago) {
        this.metodoDePago = metodoDePago;
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

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
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

    public EstadoCompraEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompraEnum estado) {
        this.estado = estado;
    }

    public void setDescuentoAplicado(int descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }
}
