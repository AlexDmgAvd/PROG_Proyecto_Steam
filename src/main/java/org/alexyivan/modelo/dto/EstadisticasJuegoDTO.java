package org.alexyivan.modelo.dto;

import java.time.LocalDate;
import java.util.Optional;

public class EstadisticasJuegoDTO {

    private long id;
    private long idJuego;
    private JuegoDTO juego;
    private long idUsuario;
    private UsuarioDTO usuario;
    private float horasJugadas;
    private int logrosObtenidos;
    private LocalDate ultimaSesion;

    public EstadisticasJuegoDTO(long id,long idJuego, Optional<JuegoDTO> juego,
                                long idUsuario, Optional<UsuarioDTO> usuario, float horasJugadas,
                                int logrosObtenidos, LocalDate ultimaSesion) {
        this.id = id;
        this.idJuego = idJuego;
        this.juego = juego.orElse(null);
        this.idUsuario = idUsuario;
        this.usuario = usuario.orElse(null);
        this.horasJugadas = horasJugadas;
        this.logrosObtenidos = logrosObtenidos;
        this.ultimaSesion = ultimaSesion;
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

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public int getLogrosObtenidos() {
        return logrosObtenidos;
    }

    public LocalDate getUltimaSesion() {
        return ultimaSesion;
    }
}
