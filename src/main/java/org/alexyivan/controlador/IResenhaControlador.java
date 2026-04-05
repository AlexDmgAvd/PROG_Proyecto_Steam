package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.EstadisticasResenhaDto;
import org.alexyivan.modelo.dto.ResenhaDto;
import org.alexyivan.modelo.enums.EstadoResenhaEnum;
import org.alexyivan.modelo.form.ResenhaForm;

import java.util.List;
import java.util.Optional;

public interface IResenhaControlador {

    Optional<ResenhaDto> escribirResenha(ResenhaForm formularioResenha) throws ValidacionException;

    Optional<ResenhaDto> eliminarResenha(long id, ResenhaForm formularioResenha) throws ValidacionException;

    List<ResenhaDto> verResenhasJuego() throws ValidacionException;

    Optional<ResenhaDto> ocultarResenha(long id, ResenhaForm formularioResenha) throws ValidacionException;

    Optional<EstadisticasResenhaDto> consultarEstadisticas() throws ValidacionException;

    List<ResenhaDto> verResenhaUsuario (ResenhaForm formularioResenha, EstadoResenhaEnum estado);

}
