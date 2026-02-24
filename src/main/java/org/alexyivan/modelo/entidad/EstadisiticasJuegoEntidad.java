package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.dto.UsuarioDTO;

import java.time.LocalDate;
import java.util.Optional;

public record EstadisiticasJuegoEntidad(long id, long idJuego,
                                        long idUsuario, float horasJugadas,
                                        int logrosObtenidos, LocalDate ultimaSesion) {

}
