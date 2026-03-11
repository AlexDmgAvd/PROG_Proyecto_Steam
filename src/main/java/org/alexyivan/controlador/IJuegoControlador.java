package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaJuegoEnum;
import org.alexyivan.modelo.form.BusquedaJuegosForm;
import org.alexyivan.modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

public interface IJuegoControlador {

    Optional<JuegoDto> anhadirJuegoCatalogo(JuegoForm formulario) throws ValidacionException;

    List<JuegoDto> buscarJuegos(BusquedaJuegosForm busquedaJuegos) throws ValidacionException;

    List<JuegoDto> listarTodosJuegos(OrdenBusquedaJuegoEnum orden) throws ValidacionException;

    Optional<JuegoDto> consultarJuego(long id) throws ValidacionException;

    Optional<JuegoDto> actualizarDescuento(long id, int descuento) throws ValidacionException;

    boolean cambiarEstado(long id, EstadoJuegoEnum estado) throws ValidacionException;


}
