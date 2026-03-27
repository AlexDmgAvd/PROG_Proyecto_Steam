package org.alexyivan.modelo.entidad;

import java.time.LocalDate;

public class EstadisiticasJuegoEntidad {
    private long id;
    private long idJuego;
    private long idUsuario;
    private float horasJugadas;
    private int logrosObtenidos;
    private LocalDate ultimaSesion;

    // Constructor
    public EstadisiticasJuegoEntidad(long id, long idJuego, long idUsuario, float horasJugadas,
                                     int logrosObtenidos, LocalDate ultimaSesion) {
        this.id = id;
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
        this.horasJugadas = horasJugadas;
        this.logrosObtenidos = logrosObtenidos;
        this.ultimaSesion = ultimaSesion;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(float horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public int getLogrosObtenidos() {
        return logrosObtenidos;
    }

    public void setLogrosObtenidos(int logrosObtenidos) {
        this.logrosObtenidos = logrosObtenidos;
    }

    public LocalDate getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(LocalDate ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }
}
