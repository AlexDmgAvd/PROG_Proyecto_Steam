package org.alexyivan.mapper;

import org.alexyivan.modelo.dto.*;
import org.alexyivan.modelo.entidad.*;

import java.util.Optional;

public class Mapper {

    public static BibliotecaDto mapBibliotecaEntidadADto(BibliotecaEntidad entidad, UsuarioDto usuario, JuegoDto juego) {
        if (entidad == null) {
            return null;
        }

        return new BibliotecaDto(
                entidad.getId(),
                entidad.getIdUsuario(),
                usuario,
                entidad.getIdJuego(),
                juego,
                entidad.getHorasJugadasTotal(),
                entidad.getUltimaFechaDeJuego(),
                entidad.getEstadoInstalacion(),
                entidad.getFechaAdquisicion()
        );
    }

    public static CompraDto mapCompraEntidadADto(CompraEntidad entidad, UsuarioDto usuario, JuegoDto juego) {
        if (entidad == null) {
            return null;
        }

        return new CompraDto(
                entidad.getId(),
                entidad.getIdUsuario(),
                Optional.of(usuario),
                entidad.getIdJuego(),
                Optional.of(juego),
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

    public static ResenhaDto mapResenhaEntidadADto(ResenhaEntidad entidad, UsuarioDto usuario, JuegoDto juego) {
        if (entidad == null) {
            return null;
        }

        return new ResenhaDto(
                entidad.getId(),
                entidad.getIdUsuario(),
                Optional.of(usuario),
                entidad.getIdJuego(),
                Optional.of(juego),
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
