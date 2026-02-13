package org.alexyivan.Modelo.Entidad;

import java.time.LocalDate;

public record BibliotecaEntidad (long id, long idUsuario, long idJuego, float horasJugadasTotal,
                                LocalDate ultimaFechaDeJuego) {

}
