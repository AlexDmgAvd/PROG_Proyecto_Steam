package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.enums.EstadoJuegoENUM;
import org.alexyivan.modelo.enums.OrdenENUM;
import org.alexyivan.modelo.enums.PegiENUM;
import org.alexyivan.modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

public interface IJuegoControlador {

    Optional<JuegoDTO> anhadirJuegoCatalogo(JuegoForm formulario) throws ValidacionException;

    List<Optional<JuegoDTO>> buscarJuegos(String titulo, String genero, float precio, PegiENUM pegi, EstadoJuegoENUM estado) throws ValidacionException;

    List<Optional<JuegoDTO>> listarTodosJuegos(OrdenENUM orden) throws ValidacionException;

    Optional<JuegoDTO> consultarJuego(long id) throws ValidacionException;

    Optional<JuegoDTO> actualizarDescuento(long id, int descuento) throws ValidacionException;

    boolean cambiarEstado(long id, EstadoJuegoENUM estado) throws ValidacionException;


}
