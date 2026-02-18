package org.alexyivan.modelo.dto;

import org.alexyivan.modelo.enums.EstadoInstalacionENUM;

import java.time.LocalDate;
import java.util.Optional;

public class BibliotecaDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private float horasJugadasTotal;
    private LocalDate ultimaFechaDeJuego;
    private EstadoInstalacionENUM estadoInstalacion;

    public BibliotecaDTO(long id, long idUsuario, Optional <UsuarioDTO> usuario, long idJuego, Optional <JuegoDTO> juego,
                         float horasJugadasTotal, LocalDate ultimaFechaDeJuego) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario.orElse(null);
        this.idJuego = idJuego;
        this.juego = juego.orElse(null);
        this.horasJugadasTotal = horasJugadasTotal;
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
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
