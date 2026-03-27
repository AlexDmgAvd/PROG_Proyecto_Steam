package org.alexyivan.modelo.dto;

import org.alexyivan.modelo.enums.EstadoInstalacionEnum;

import java.time.LocalDate;

public class BibliotecaDto {

    private long id;
    private long idUsuario;
    private UsuarioDto usuario;
    private long idJuego;
    private JuegoDto juego;
    private float horasJugadasTotal;
    private LocalDate ultimaFechaDeJuego;
    private EstadoInstalacionEnum estadoInstalacion;
    private LocalDate fechaAdquisicion;

    public BibliotecaDto(long id, long idUsuario, UsuarioDto usuario, long idJuego, JuegoDto juego,
                         float horasJugadasTotal, LocalDate ultimaFechaDeJuego, EstadoInstalacionEnum estadoInstalacion,
                         LocalDate fechaAdquisicion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.horasJugadasTotal = horasJugadasTotal;
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
        this.estadoInstalacion = estadoInstalacion;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public EstadoInstalacionEnum getEstadoInstalacion() {
        return estadoInstalacion;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
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
}
