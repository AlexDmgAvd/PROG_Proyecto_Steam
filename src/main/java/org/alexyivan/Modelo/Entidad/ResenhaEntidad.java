package org.alexyivan.Modelo.Entidad;

import java.time.LocalDate;

public record ResenhaEntidad(long id, long idUsuario, long idJuego,
                             boolean recomendado, String textoAnalisis, float horasJugadas,
                             LocalDate fechaPublicacion, LocalDate ultimaFechaEdicion) {
}
