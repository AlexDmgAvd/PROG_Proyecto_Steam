package Modelo.Entidad;

import Modelo.Dto.JuegoDTO;
import Modelo.Dto.UsuarioDTO;

import java.time.LocalDate;
import java.util.Optional;

public record BibliotecaEntidad (long id, long idUsuario, long idJuego, float horasJugadasTotal,
                                LocalDate ultimaFechaDeJuego) {

}
