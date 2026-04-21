package org.alexyivan.controlador;


import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.enums.*;
import org.alexyivan.modelo.form.*;
import org.alexyivan.repositorio.inmemory.BibliotecaRepoInMemory;
import org.alexyivan.repositorio.inmemory.CompraRepoInMemory;
import org.alexyivan.repositorio.inmemory.JuegoRepoInMemory;
import org.alexyivan.repositorio.inmemory.UsuarioRepoInMemory;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.ICompraRepo;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompraControlador implements ICompraControlador {

    private final ICompraRepo compraRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IBibliotecaRepo bibliotecaRepo;
    private final IJuegoRepo juegoRepo;

    public CompraControlador(ICompraRepo compraRepo, IUsuarioRepo usuarioRepo,
                             IBibliotecaRepo bibliotecaRepo, IJuegoRepo juegoRepo) {
        this.compraRepo = compraRepo;
        this.usuarioRepo = usuarioRepo;
        this.bibliotecaRepo = bibliotecaRepo;
        this.juegoRepo = juegoRepo;
    }

    @Override
    public Optional<CompraDto> realizarCompra(CompraForm formularioCompra) throws ValidacionException {
        //Validaciones
        var errores = formularioCompra.validar();

        var usuario = usuarioRepo.obtenerPorId(formularioCompra.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(formularioCompra.getJuegoId());

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (usuario.get().getEstado() != EstadoCuentaEmun.ACTIVA) {
            errores.add(new ErrorDto("cuenta", ErrorType.USUARIO_SIN_PERMISO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.get().getEstado() == EstadoJuegoEnum.NO_DISPONIBLE) {
            errores.add(new ErrorDto("estado", ErrorType.JUEGO_NO_COMPRABLE));
        }

        if (bibliotecaRepo.buscarJuegoUsuario(formularioCompra.getJuegoId(), formularioCompra.getUsuarioId()).isPresent()) {
            errores.add(new ErrorDto("duplicado", ErrorType.DUPLICADO));

        }

        if (formularioCompra.getMetodoPago() == MetodoPagoEnum.CARTERA_STEAM &&
                usuario.get().getCreditoSteam() < juego.get().getDescuentoActual()) {
            errores.add(new ErrorDto("saldo", ErrorType.SALDO_INSUFICIENTE));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        //Ejecución
        var compra = compraRepo.crear(new CompraForm(formularioCompra.getUsuarioId(), formularioCompra.getJuegoId(), formularioCompra.getFechaCompra(),
                formularioCompra.getMetodoPago(), formularioCompra.getPrecioSinDescuento(), formularioCompra.getDescuento(),
                formularioCompra.getPrecioFinal(), formularioCompra.getEstado()));

        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));


        return Optional.ofNullable(Mapper.mapCompraEntidadADto(compra.orElse(null), usuarioDto, juegoDto));
    }

    @Override
    public Optional<CompraDto> procesarPago(CompraForm formularioCompra, long id) throws ValidacionException {
        var errores = formularioCompra.validar();
        var compra = compraRepo.obtenerPorId(id);

        if (compra.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.COMRPA_INEXISTENTE));
        }

        if (formularioCompra.getEstado() == EstadoCompraEnum.COMPLETADO) {
            errores.add(new ErrorDto("completado", ErrorType.COMPRA_COMPLETADA));
        }
        if (formularioCompra.getEstado() == EstadoCompraEnum.REEMBOLSADO) {
            errores.add(new ErrorDto("reembolsado", ErrorType.COMPRA_REEMBOLSADA));
        }

        if (!Arrays.stream(MetodoPagoEnum.values()).equals(formularioCompra.getMetodoPago())) {
            errores.add(new ErrorDto("inválido", ErrorType.COMPRA_INVALIDA));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var usuario = usuarioRepo.obtenerPorId(formularioCompra.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(formularioCompra.getJuegoId());
        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));


        return Optional.ofNullable(Mapper.mapCompraEntidadADto(compra.orElse(null), usuarioDto, juegoDto));
    }

    @Override
    public List<CompraDto> consultarHistorialCompras(CompraForm formularioCompra, BusquedaCompraForm opcionesBusqueda,
                                                     long id) throws ValidacionException {

        var compra = compraRepo.obtenerPorId(id);
        var erroresCompra = formularioCompra.validar();
        var erroresBusqueda = opcionesBusqueda.validar();
        List<ErrorDto> errores = Stream.concat(erroresBusqueda.stream(), erroresCompra.stream()).toList();

        if (compra.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.COMRPA_INEXISTENTE));
        }

        //Todo comprobaciones de cómo mostrar la lista de las compras entre un rango y otro

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        return List.of();
    }

    @Override
    public Optional<CompraDto> consultarDetallesCompra(long idCompra, CompraForm formularioCompra) throws ValidacionException {
        var errores = formularioCompra.validar();
        var compra = compraRepo.obtenerPorId(idCompra);

        if (compra.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.COMRPA_INEXISTENTE));
        }

        if (compra.get().getIdUsuario() != formularioCompra.getUsuarioId()) {
            errores.add(new ErrorDto("id", ErrorType.COMPRA_NO_COINCIDENTE));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }
        var usuario = usuarioRepo.obtenerPorId(formularioCompra.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(formularioCompra.getJuegoId());
        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));


        return Optional.ofNullable(Mapper.mapCompraEntidadADto(compra.orElse(null), usuarioDto, juegoDto));


    }

    @Override
    public boolean solicitarReembolso(long idCompra, OpcionesReembolsoEnum opcionesReembolso) throws ValidacionException {
        //Todo
        return false;
    }

    @Override
    public String generarFactura(long idCompra) throws ValidacionException {
        //Todo
        return "";
    }

    public static void main(String[] args) {
        IBibliotecaRepo iBibliotecaRepo = new BibliotecaRepoInMemory();
        IUsuarioRepo iUsuarioRepo = new UsuarioRepoInMemory();
        IJuegoRepo iJuegoRepo = new JuegoRepoInMemory();
        ICompraRepo iCompraRepo = new CompraRepoInMemory();

        CompraControlador compraControlador = new CompraControlador(iCompraRepo, iUsuarioRepo, iBibliotecaRepo, iJuegoRepo);

        iUsuarioRepo.crear(new UsuarioForm("kaisquest", "email@email.com", "1234abcd!", "Iván",
                "Spain", LocalDate.of(1998, 03, 05), LocalDate.of(2026, 04, 21),
                "Avatar", 50.0f, EstadoCuentaEmun.ACTIVA));
        iJuegoRepo.crear(new JuegoForm("Marvel Rivals", "Heroe shooter en tercera persona en el que controlas" +
                "a los personajes del universo marvel", "NetEast", LocalDate.of(2025, 01, 01),
                5.0f, 0, "Heroe Shooter", PegiEnum.PEGI_12, "Español, Inglés", EstadoJuegoEnum.DISPONIBLE));

        compraControlador.realizarCompra(new CompraForm(iUsuarioRepo.obtenerPorNombre("kaisquest").get().getId(), iJuegoRepo.obtenerTitulo("Marvel Rivals").get().getId(),
                LocalDateTime.now(), MetodoPagoEnum.CARTERA_STEAM, iJuegoRepo.obtenerTitulo("Marvel Rivals").get().getPrecioBase(),
                iJuegoRepo.obtenerTitulo("Marvel Rivals").get().getDescuentoActual(),
                Double.valueOf(iJuegoRepo.obtenerTitulo("Marvel Rivals").get().getPrecioBase()) - ((iJuegoRepo.obtenerTitulo("Marvel Rivals").get().getPrecioBase()
                        * (iJuegoRepo.obtenerTitulo("Marvel Rivals").get().getDescuentoActual() * 100))), EstadoCompraEnum.COMPLETADO));


    }
}
