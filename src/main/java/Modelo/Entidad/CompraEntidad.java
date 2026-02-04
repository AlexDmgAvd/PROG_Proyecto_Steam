package Modelo.Entidad;

import Modelo.Dto.JuegoDTO;
import Modelo.Dto.UsuarioDTO;

import java.util.Optional;

public record CompraEntidad(long id, long idUsuario, long idJuego,
                            float precioCompleto, int descuentoAplicado) {
}
