package org.alexyivan.mapper;

import org.alexyivan.modelo.dto.*;
import org.alexyivan.modelo.entidad.*;

public class Mapper {

    public static BibliotecaDto mapBibliotecaEntidadADto(BibliotecaEntidad entidad) {
        if (entidad == null)
            return null;

        return new BibliotecaDto(entidad.id(),
                entidad.idUsuario(),
                null,
                entidad.idJuego(),
                null,
                entidad.horasJugadasTotal(),
                entidad.ultimaFechaDeJuego(),
                entidad.estadoInstalacion(),
                entidad.fechaAdquisicion());


    }

    public static CompraDto mapCompraEntidadADto(CompraEntidad entidad) {
        if (entidad == null)
            return null;

        return new CompraDto(entidad.id(),
                entidad.idUsuario(),
                null,
                entidad.idJuego(),
                null,
                entidad.precioSinDescuento(),
                entidad.descuentoAplicado());

    }

    public static JuegoDto mapJuegoEntidadADto(JuegoEntidad entidad) {
        if (entidad == null)
            return null;

        return new JuegoDto(entidad.id(),
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

    public static ResenhaDto mapResenhaEntidadADto(ResenhaEntidad entidad) {
        if (entidad == null)
            return null;

        return new ResenhaDto(entidad.id(),
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

    public static UsuarioDto mapUsuarioEntidadADto(UsuarioEntidad entidad) {
        if (entidad == null)
            return null;

        return new UsuarioDto(entidad.id(),
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
