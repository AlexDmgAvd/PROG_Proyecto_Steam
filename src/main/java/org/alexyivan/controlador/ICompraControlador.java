package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.modelo.enums.OpcionesReembolsoEnum;
import org.alexyivan.modelo.form.BusquedaCompraForm;
import org.alexyivan.modelo.form.CompraForm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ICompraControlador {

    Optional<CompraDto> realizarCompra(CompraForm formularioCompra) throws ValidacionException;


    Optional<CompraDto> procesarPago(CompraForm formularioCompra, long id) throws ValidacionException;


    List<CompraDto> consultarHistorialCompras(CompraForm formularioCompra, BusquedaCompraForm opcionesBusqueda, long id) throws ValidacionException;


    Optional<CompraDto> consultarDetallesCompra(long idCompra, CompraForm formularioCompra) throws ValidacionException;


    Optional<CompraDto> solicitarReembolso(long idCompra, OpcionesReembolsoEnum opcionesReembolso) throws ValidacionException;


    String generarFactura (long idCompra) throws ValidacionException, IOException;

}
