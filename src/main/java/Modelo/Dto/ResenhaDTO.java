package Modelo.Dto;

import java.time.LocalDate;

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
    private enum state {PUBLICADA,OCULTA,BORRADA};


}
