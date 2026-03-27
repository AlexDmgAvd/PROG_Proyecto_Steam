package org.alexyivan.modelo.entidad;

import java.time.LocalDate;

public class ResenhaEntidad {
    private long id;
    private long idUsuario;
    private long idJuego;
    private boolean recomendado;
    private String textoAnalisis;
    private float horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate ultimaFechaEdicion;

    // Constructor
    public ResenhaEntidad(long id, long idUsuario, long idJuego,
                          boolean recomendado, String textoAnalisis, float horasJugadas,
                          LocalDate fechaPublicacion, LocalDate ultimaFechaEdicion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.textoAnalisis = textoAnalisis;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.ultimaFechaEdicion = ultimaFechaEdicion;
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