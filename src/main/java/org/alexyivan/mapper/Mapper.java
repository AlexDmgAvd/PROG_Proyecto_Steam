package org.alexyivan.mapper;

import org.alexyivan.modelo.dto.*;
import org.alexyivan.modelo.entidad.*;

public class Mapper {

    public static BibliotecaDto mapBibliotecaEntidadADto(BibliotecaEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        return new BibliotecaDto(
                entidad.getId(),
                entidad.getIdUsuario(),
                null,
                entidad.getIdJuego(),
                null,
                entidad.getHorasJugadasTotal(),
                entidad.getUltimaFechaDeJuego(),
                entidad.getEstadoInstalacion(),
                entidad.getFechaAdquisicion()
        );
    }

    public static CompraDto mapCompraEntidadADto(CompraEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        return new CompraDto(
                entidad.getId(),
                entidad.getIdUsuario(),
                null,
                entidad.getIdJuego(),
                null,
                entidad.getPrecioSinDescuento(),
                entidad.getDescuentoAplicado()
        );
    }

    public static JuegoDto mapJuegoEntidadADto(JuegoEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        return new JuegoDto(
                entidad.getId(),
                entidad.getTitulo(),
                entidad.getDescripcion(),
                entidad.getDesarrolladora(),
                entidad.getFechaPublicacion(),
                entidad.getPrecioBase(),
                entidad.getDescuentoActual(),
                entidad.getGenero(),
                entidad.getRangoEdad(),
                entidad.getEstado()
        );
    }

    public static ResenhaDto mapResenhaEntidadADto(ResenhaEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        return new ResenhaDto(
                entidad.getId(),
                entidad.getIdUsuario(),
                null,
                entidad.getIdJuego(),
                null,
                entidad.isRecomendado(),
                entidad.getTextoAnalisis(),
                entidad.getHorasJugadas(),
                entidad.getFechaPublicacion(),
                entidad.getUltimaFechaEdicion()
        );
    }

    public static UsuarioDto mapUsuarioEntidadADto(UsuarioEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        return new UsuarioDto(
                entidad.getId(),
                entidad.getNombreUsuario(),
                entidad.getEmail(),
                entidad.getNombreReal(),
                entidad.getPais(),
                entidad.getCumpleanhos(),
                entidad.getFechaRegistro(),
                entidad.getAvatar(),
                entidad.getCreditoSteam()
        );
    }
}
