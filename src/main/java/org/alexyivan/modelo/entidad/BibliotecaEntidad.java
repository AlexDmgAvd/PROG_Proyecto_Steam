package org.alexyivan.modelo.entidad;

import java.time.LocalDate;

public record BibliotecaEntidad (long id, long idUsuario, long idJuego, float horasJugadasTotal,
                                LocalDate ultimaFechaDeJuego) {

}
