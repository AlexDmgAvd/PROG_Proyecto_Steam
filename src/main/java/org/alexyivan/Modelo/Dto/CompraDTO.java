package org.alexyivan.Modelo.Dto;

import org.alexyivan.Modelo.Enum.EstadoCompraENUM;
import org.alexyivan.Modelo.Enum.MetodoPagoENUM;

import java.util.Optional;

public class CompraDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private MetodoPagoENUM metodoDePago;
    private float precioSinDescuento;
    private int descuentoAplicado;
    private EstadoCompraENUM estado;

    public CompraDTO(long id, long idUsuario, Optional <UsuarioDTO> usuario, long idJuego,
                     Optional <JuegoDTO> juego, float precioCompleto, int descuentoAplicado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario.orElse(null);
        this.idJuego = idJuego;
        this.juego = juego.orElse(null);
        this.precioSinDescuento = precioCompleto;
        this.descuentoAplicado = descuentoAplicado;
    }

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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public JuegoDTO getJuego() {
        return juego;
    }

    public void setJuego(JuegoDTO juego) {
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
