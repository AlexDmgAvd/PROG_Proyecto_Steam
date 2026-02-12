package Modelo.Dto;

import Modelo.Enum.EstadoResenhaENUM;

import java.time.LocalDate;
import java.util.Optional;

public class ResenhaDTO {
    private long id;
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

    public ResenhaDTO(long id, long idUsuario, Optional <UsuarioDTO> usuario, long idJuego,
                      Optional <JuegoDTO> juego, boolean recomendado, String textoAnalisis, float horasJugadas,
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
}
