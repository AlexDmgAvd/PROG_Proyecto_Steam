package org.alexyivan.modelo.dto;

import org.alexyivan.modelo.enums.EstadoResenhaEnum;

import java.time.LocalDate;
import java.util.Optional;

public class ResenhaDto {
    private long id;
    private long idUsuario;
    private UsuarioDto usuario;
    private long idJuego;
    private JuegoDto juego;
    private boolean recomendado;
    private String textoAnalisis;
    private float horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate ultimaFechaEdicion;
    private EstadoResenhaEnum estado;

    public ResenhaDto(long id, long idUsuario, Optional<UsuarioDto> usuario, long idJuego,
                      Optional<JuegoDto> juego, boolean recomendado, String textoAnalisis, float horasJugadas,
                      LocalDate fechaPublicacion, LocalDate ultimaFechaEdicion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario.orElse(null);
        this.idJuego = idJuego;
        this.juego = juego.orElse(null);
        this.recomendado = recomendado;
        this.textoAnalisis = textoAnalisis;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.ultimaFechaEdicion = ultimaFechaEdicion;
    }

    public long getId() {
        return id;
    }

    public EstadoResenhaEnum getEstado() {
        return estado;
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

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTextoAnalisis() {
        return textoAnalisis;
    }

    public void setTextoAnalisis(String textoAnalisis) {
        this.textoAnalisis = textoAnalisis;
    }

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(float horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getUltimaFechaEdicion() {
        return ultimaFechaEdicion;
    }

    public void setUltimaFechaEdicion(LocalDate ultimaFechaEdicion) {
        this.ultimaFechaEdicion = ultimaFechaEdicion;
    }
}
