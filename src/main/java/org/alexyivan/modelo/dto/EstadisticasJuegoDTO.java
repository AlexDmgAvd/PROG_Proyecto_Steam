package org.alexyivan.modelo.dto;

import java.time.LocalDate;
import java.util.Optional;

public class EstadisticasJuegoDTO {

    private long id;
    private long idJuego;
    private JuegoDTO juego;
    private long idUsuario;
    private UsuarioDTO usuario;
    private int totalJuegos;
    private float horasJugadas;
    private int juegosInstalados;
    private long juegoMasJugados;
    private float valorTotal;
    private long juegosNuncaJugados;

    public EstadisticasJuegoDTO(long id, long idJuego, JuegoDTO juego, long idUsuario, UsuarioDTO usuario, int totalJuegos, float horasJugadas, int juegosInstalados, long juegoMasJugados, float valorTotal, long juegosNuncaJugados) {
        this.id = id;
        this.idJuego = idJuego;
        this.juego = juego;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.totalJuegos = totalJuegos;
        this.horasJugadas = horasJugadas;
        this.juegosInstalados = juegosInstalados;
        this.juegoMasJugados = juegoMasJugados;
        this.valorTotal = valorTotal;
        this.juegosNuncaJugados = juegosNuncaJugados;
    }

    public long getId() {
        return id;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public JuegoDTO getJuego() {
        return juego;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public int getTotalJuegos() {
        return totalJuegos;
    }

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public int getJuegosInstalados() {
        return juegosInstalados;
    }

    public long getJuegoMasJugados() {
        return juegoMasJugados;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public long getJuegosNuncaJugados() {
        return juegosNuncaJugados;
    }
}

