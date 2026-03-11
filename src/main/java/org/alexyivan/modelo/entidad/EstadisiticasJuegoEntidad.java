package org.alexyivan.modelo.entidad;

import java.time.LocalDate;

public record EstadisiticasJuegoEntidad(long id, long idJuego,
                                        long idUsuario, float horasJugadas,
                                        int logrosObtenidos, LocalDate ultimaSesion) {

}
