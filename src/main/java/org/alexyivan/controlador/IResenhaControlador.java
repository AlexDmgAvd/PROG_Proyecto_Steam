package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.EstadisticasResenhaDTO;
import org.alexyivan.modelo.dto.ResenhaDTO;

import java.util.List;
import java.util.Optional;

public interface IResenhaControlador {

    Optional<ResenhaDTO> escribirResenha() throws ValidacionException;

    boolean eliminarResenha() throws ValidacionException;

    List<ResenhaDTO> verResenhasJuego() throws ValidacionException;

    boolean ocultarResenha(long idUsuario, long idResenha) throws ValidacionException;

    Optional<EstadisticasResenhaDTO> consultarEstadisticas() throws ValidacionException;

    List<ResenhaDTO> verResenhaUsuario (long idUsuario);

}
