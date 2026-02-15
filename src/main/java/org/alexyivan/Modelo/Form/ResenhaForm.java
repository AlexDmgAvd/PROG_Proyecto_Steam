package org.alexyivan.Modelo.Form;

import org.alexyivan.Modelo.Dto.JuegoDTO;
import org.alexyivan.Modelo.Dto.UsuarioDTO;
import org.alexyivan.Modelo.Enum.EstadoResenhaENUM;

import java.time.LocalDate;

public class ResenhaForm {

    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private boolean recomendado;
    private String textoAnalisis;
    private float horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate ultimaFechaEdicion;
    private EstadoResenhaENUM estado;

    public ResenhaForm(long idUsuario, UsuarioDTO usuario, long idJuego, JuegoDTO juego,
                       boolean recomendado, String textoAnalisis, float horasJugadas, LocalDate fechaPublicacion,
                       LocalDate ultimaFechaEdicion, EstadoResenhaENUM estado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.recomendado = recomendado;
        this.textoAnalisis = textoAnalisis;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.ultimaFechaEdicion = ultimaFechaEdicion;
        this.estado = estado;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public JuegoDTO getJuego() {
        return juego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public String getTextoAnalisis() {
        return textoAnalisis;
    }

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public LocalDate getUltimaFechaEdicion() {
        return ultimaFechaEdicion;
    }

    public EstadoResenhaENUM getEstado() {
        return estado;
    }
}
