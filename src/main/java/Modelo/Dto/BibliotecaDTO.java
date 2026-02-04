package Modelo.Dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BibliotecaDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private float horasJugadasTotal;
    private LocalDate ultimaFechaDeJuego;
    private enum estadoInstalacion {INSTALADO, NO_INSTALADO}

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

}
