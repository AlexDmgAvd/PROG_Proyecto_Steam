package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.modelo.enums.OpcionesReembolsoEnum;

import java.util.List;
import java.util.Optional;

public interface ICompraControlador {

    Optional<CompraDto> realizarCompra() throws ValidacionException;


    boolean procesarPago() throws ValidacionException;


    List<CompraDto> consultarHistorialCompras() throws ValidacionException;


    Optional<CompraDto> consultarDetallesCompra(long idCompra, long idUsuario) throws ValidacionException;


    boolean solicitarReembolso(long idCompra, OpcionesReembolsoEnum opcionesReembolso) throws ValidacionException;


    String generarFactura (long idCompra) throws ValidacionException;

}
