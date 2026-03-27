package org.alexyivan.controlador;


import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.modelo.enums.EstadoCuentaEmun;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.MetodoPagoEnum;
import org.alexyivan.modelo.enums.OpcionesReembolsoEnum;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.ICompraRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraControlador implements ICompraControlador {

    private final ICompraRepo compraRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;
    private final IBibliotecaRepo bibliotecaRepo;

    public CompraControlador(ICompraRepo compraRepo, IUsuarioRepo usuarioRepo, IJuegoRepo juegoRepo, IBibliotecaRepo bibliotecaRepo) {
        this.compraRepo = compraRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
        this.bibliotecaRepo = bibliotecaRepo;
    }

    @Override
    public Optional<CompraDto> realizarCompra(long idUsuario, long idJuego, MetodoPagoEnum metodoPagoEnum) throws ValidacionException {

        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(idUsuario);
        var juego = juegoRepo.obtenerPorId(idJuego);

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (usuario.get().getEstado() != EstadoCuentaEmun.ACTIVA) {
            errores.add(new ErrorDto("id", ErrorType.ESTADO_CUENTA));
        }

        if (juego.get().getEstado() == EstadoJuegoEnum.NO_DISPONIBLE) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        //todo

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
