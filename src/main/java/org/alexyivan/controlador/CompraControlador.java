package org.alexyivan.controlador;


import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.modelo.enums.OpcionesReembolsoEnum;
import org.alexyivan.repositorio.interfaces.ICompraRepo;

import java.util.List;
import java.util.Optional;

public class CompraControlador implements ICompraControlador {

    private final ICompraRepo compraRepo;

    public CompraControlador(ICompraRepo compraRepo) {
        this.compraRepo = compraRepo;
    }

    @Override
    public Optional<CompraDto> realizarCompra() throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public boolean procesarPago() throws ValidacionException {
        return false;
    }

    @Override
    public List<CompraDto> consultarHistorialCompras() throws ValidacionException {
        return List.of();
    }

    @Override
    public Optional<CompraDto> consultarDetallesCompra(long idCompra, long idUsuario) throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public boolean solicitarReembolso(long idCompra, OpcionesReembolsoEnum opcionesReembolso) throws ValidacionException {
        return false;
    }

    @Override
    public String generarFactura(long idCompra) throws ValidacionException {
        return "";
    }
}
