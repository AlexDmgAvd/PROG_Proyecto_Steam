package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoInstalacionEnum;

import java.time.LocalDate;

public class BibliotecaEntidad {
    private long id;
    private long idUsuario;
    private long idJuego;
    private float horasJugadasTotal;
    private LocalDate ultimaFechaDeJuego;
    private LocalDate fechaAdquisicion;
    private EstadoInstalacionEnum estadoInstalacion;

    // Constructor
    public BibliotecaEntidad(long id, long idUsuario, long idJuego, float horasJugadasTotal,
                             LocalDate ultimaFechaDeJuego, LocalDate fechaAdquisicion,
                             EstadoInstalacionEnum estadoInstalacion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.horasJugadasTotal = horasJugadasTotal;
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estadoInstalacion = estadoInstalacion;
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

    public float getHorasJugadasTotal() {
        return horasJugadasTotal;
    }

    public void setHorasJugadasTotal(float horasJugadasTotal) {
        this.horasJugadasTotal = horasJugadasTotal;
    }

    public LocalDate getUltimaFechaDeJuego() {
        return ultimaFechaDeJuego;
    }

    public void setUltimaFechaDeJuego(LocalDate ultimaFechaDeJuego) {
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public EstadoInstalacionEnum getEstadoInstalacion() {
        return estadoInstalacion;
    }

    public void setEstadoInstalacion(EstadoInstalacionEnum estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }
}
