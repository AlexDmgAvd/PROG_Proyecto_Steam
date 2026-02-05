package Modelo.Entidad;

import Modelo.Dto.JuegoDTO;
import Modelo.Dto.UsuarioDTO;

import java.time.LocalDate;
import java.util.Optional;

public record ResenhaEntidad(long id, long idUsuario, long idJuego,
                             boolean recomendado, String textoAnalisis, float horasJugadas,
                             LocalDate fechaPublicacion, LocalDate ultimaFechaEdicion) {
}
