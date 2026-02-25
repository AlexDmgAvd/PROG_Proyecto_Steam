package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoInstalacionENUM;

import java.time.LocalDate;

public record BibliotecaEntidad (long id, long idUsuario, long idJuego, float horasJugadasTotal,
                                LocalDate ultimaFechaDeJuego, LocalDate fechaAdquisicion, EstadoInstalacionENUM estadoInstalacion) {

}
