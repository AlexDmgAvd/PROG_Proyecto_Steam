package org.alexyivan.modelo.dto;

import org.alexyivan.modelo.enums.EstadoCompraEnum;
import org.alexyivan.modelo.enums.MetodoPagoEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class CompraDto {

    private long id;
    private long idUsuario;
    private UsuarioDto usuario;
    private long idJuego;
    private JuegoDto juego;
    private MetodoPagoEnum metodoDePago;
    private float precioSinDescuento;
    private int descuentoAplicado;
    private EstadoCompraEnum estado;
    private LocalDateTime fechaCompra;

    public CompraDto(long id, long idUsuario, Optional<UsuarioDto> usuario, long idJuego,
                     Optional<JuegoDto> juego,MetodoPagoEnum metodoDePago, float precioCompleto, int descuentoAplicado, EstadoCompraEnum estado,
                     LocalDateTime fechaCompra) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario.orElse(null);
        this.idJuego = idJuego;
        this.juego = juego.orElse(null);
        this.metodoDePago = metodoDePago;
        this.precioSinDescuento = precioCompleto;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetodoPagoEnum getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(MetodoPagoEnum metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public EstadoCompraEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompraEnum estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public JuegoDto getJuego() {
        return juego;
    }

    public void setJuego(JuegoDto juego) {
        this.juego = juego;
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
