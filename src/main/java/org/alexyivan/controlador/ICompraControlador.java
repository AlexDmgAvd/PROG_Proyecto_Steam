package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.CompraDTO;

import java.util.List;
import java.util.Optional;

public interface ICompraControlador {

    Optional<CompraDTO> realizarCompra();

    boolean procesarPago();

    List<CompraDTO> consultarHistorialCompras();

    Optional<CompraDTO> consultarDetallesCompra(long idCompra, long idUsuario);



}
