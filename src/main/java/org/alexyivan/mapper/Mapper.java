package org.alexyivan.mapper;

import org.alexyivan.modelo.dto.*;
import org.alexyivan.modelo.entidad.*;

public class Mapper {

    public static BibliotecaDTO mapBibliotecaEntidadADto(BibliotecaEntidad entidad) {
        if (entidad == null)
            return null;

        return new BibliotecaDTO(entidad.id(),
                entidad.idUsuario(),
                null,
                entidad.idJuego(),
                null,
                entidad.horasJugadasTotal(),
                entidad.ultimaFechaDeJuego());

    }

    public static CompraDTO mapCompraEntidadADto(CompraEntidad entidad) {
        if (entidad == null)
            return null;

        return new CompraDTO(entidad.id(),
                entidad.idUsuario(),
                null,
                entidad.idJuego(),
                null,
                entidad.precioSinDescuento(),
                entidad.descuentoAplicado());

    }

    public static JuegoDTO mapJuegoEntidadADto(JuegoEntidad entidad) {
        if (entidad == null)
            return null;

        return new JuegoDTO(entidad.id(),
                entidad.titulo(),
                entidad.descripcion(),
                entidad.desarrolladora(),
                entidad.fechaPublicacion(),
                entidad.precioBase(),
                entidad.descuentoActual(),
                entidad.genero(),
                entidad.rangoEdad(),
                entidad.estado());
    }

    public static ResenhaDTO mapResenhaEntidadADto(ResenhaEntidad entidad) {
        if (entidad == null)
            return null;

        return new ResenhaDTO(entidad.id(),
                entidad.idUsuario(),
                null,
                entidad.idJuego(),
                null,
                entidad.recomendado(),
                entidad.textoAnalisis(),
                entidad.horasJugadas(),
                entidad.fechaPublicacion(),
                entidad.ultimaFechaEdicion());

    }
    public static UsuarioDTO mapUsuarioEntidadADto(UsuarioEntidad entidad){
        if (entidad == null)
            return null;

        return new UsuarioDTO(entidad.id(),
                entidad.nombreUsuario(),
                entidad.email(),
                entidad.nombreReal(),
                entidad.pais(),
                entidad.cumpleanhos(),
                entidad.fechaRegistro(),
                entidad.avatar(),
                entidad.creditoSteam());
    }



}
