package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.EstadisticasResenhaDTO;
import org.alexyivan.modelo.dto.ResenhaDto;

import java.util.List;
import java.util.Optional;

public interface IResenhaControlador {

    Optional<ResenhaDto> escribirResenha() throws ValidacionException;

    boolean eliminarResenha() throws ValidacionException;

    List<ResenhaDto> verResenhasJuego() throws ValidacionException;

    boolean ocultarResenha(long idUsuario, long idResenha) throws ValidacionException;

    Optional<EstadisticasResenhaDTO> consultarEstadisticas() throws ValidacionException;

    List<ResenhaDto> verResenhaUsuario (long idUsuario);

}
