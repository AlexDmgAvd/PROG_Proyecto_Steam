package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.modelo.enums.*;
import org.alexyivan.modelo.form.*;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.ICompraRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;
import org.alexyivan.transaction.ITransactionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CompraControlador implements ICompraControlador {

    public static final int DIAS_MAXIMOS = 30;
    public static final float MAX_HORAS_JUGADAS = 2.0f;
    public static final int DESCUENTO = 100;
    public static final float HORAS_COMPRA = 0f;
    private static long idCounterFactura = 1;
    private static String separador = "_";
    private final ICompraRepo compraRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IBibliotecaRepo bibliotecaRepo;
    private final IJuegoRepo juegoRepo;
    private ITransactionManager tm;

    public CompraControlador(ICompraRepo compraRepo, IUsuarioRepo usuarioRepo, IBibliotecaRepo bibliotecaRepo, IJuegoRepo juegoRepo, ITransactionManager tm) {
        this.compraRepo = compraRepo;
        this.usuarioRepo = usuarioRepo;
        this.bibliotecaRepo = bibliotecaRepo;
        this.juegoRepo = juegoRepo;
        this.tm = tm;
    }

    @Override
    public Optional<CompraDto> realizarCompra(CompraForm formularioCompra) throws ValidacionException {
        var errores = formularioCompra.validar();
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var compraRealizada = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var usuario = usuarioRepo.obtenerPorId(formularioCompra.getUsuarioId());
            var juego = juegoRepo.obtenerPorId(formularioCompra.getJuegoId());

            if (usuario.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }

            if (juego.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            if (usuario.get().getEstado() != EstadoCuentaEmun.ACTIVA) {
                erroresTx.add(new ErrorDto("cuenta", ErrorType.USUARIO_SIN_PERMISO));
            }

            if (formularioCompra.getMetodoPago() == MetodoPagoEnum.CARTERA_STEAM &&
                    usuario.get().getCreditoSteam() < juego.get().getDescuentoActual()) {
                erroresTx.add(new ErrorDto("saldo", ErrorType.SALDO_INSUFICIENTE));
            }

            if (formularioCompra.getFechaCompra() == null) {
                formularioCompra.setFechaCompra(LocalDateTime.now());
            }

            if (juego.get().getEstado() == EstadoJuegoEnum.NO_DISPONIBLE) {
                erroresTx.add(new ErrorDto("estado", ErrorType.JUEGO_NO_COMPRABLE));
            }

            if (bibliotecaRepo.buscarJuegoUsuario(formularioCompra.getJuegoId(), formularioCompra.getUsuarioId()).isPresent()) {
                erroresTx.add(new ErrorDto("duplicado", ErrorType.DUPLICADO));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            var compra = compraRepo.crear(new CompraForm(formularioCompra.getUsuarioId(), formularioCompra.getJuegoId(), formularioCompra.getFechaCompra(),
                    formularioCompra.getMetodoPago(), formularioCompra.getPrecioSinDescuento(), formularioCompra.getDescuento(),
                    formularioCompra.getPrecioFinal(), EstadoCompraEnum.PENDIENTE));

            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            return Optional.ofNullable(Mapper.mapCompraEntidadADto(compra.orElse(null), usuarioDto, juegoDto));
        });

        return compraRealizada;
    }

    @Override
    public Optional<CompraDto> procesarPago(long id) throws ValidacionException {
        var compraProcesada = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var compra = compraRepo.obtenerPorId(id);

            if (compra.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.COMRPA_INEXISTENTE));
            } else {
                if (compra.get().getEstado() == EstadoCompraEnum.COMPLETADO) {
                    erroresTx.add(new ErrorDto("completado", ErrorType.COMPRA_COMPLETADA));
                }
                if (compra.get().getEstado() == EstadoCompraEnum.REEMBOLSADA) {
                    erroresTx.add(new ErrorDto("reembolsado", ErrorType.COMPRA_REEMBOLSADA));
                }
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            var usuario = usuarioRepo.obtenerPorId(compra.get().getIdUsuario())
                    .orElseThrow(() -> new ValidacionException(List.of(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO))));
            var juego = juegoRepo.obtenerPorId(compra.get().getIdJuego())
                    .orElseThrow(() -> new ValidacionException(List.of(new ErrorDto("juego", ErrorType.NO_ENCONTRADO))));
            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario);
            var juegoDto = Mapper.mapJuegoEntidadADto(juego);

            bibliotecaRepo.crear(new BibliotecaForm(usuario.getId(), juego.getId(), compra.get().getFechaCompra().toLocalDate(),
                    HORAS_COMPRA, null, EstadoInstalacionEnum.NO_INSTALADO));

            var compraActualizada = compraRepo.actualizar(compra.get().getId(), new CompraForm(compra.get().getIdUsuario(), compra.get().getIdJuego(),
                    compra.get().getFechaCompra(), compra.get().getMetodoDePago(), compra.get().getPrecioSinDescuento(), compra.get().getDescuentoAplicado(),
                    (double) (compra.get().getPrecioSinDescuento() - compra.get().getPrecioSinDescuento() *
                            ((float) compra.get().getDescuentoAplicado() / DESCUENTO)), EstadoCompraEnum.COMPLETADO
            ));

            return Optional.ofNullable(Mapper.mapCompraEntidadADto(compraActualizada.orElse(null), usuarioDto, juegoDto));
        });

        return compraProcesada;
    }

    @Override
    public List<CompraDto> consultarHistorialCompras(CompraForm formularioCompra, BusquedaCompraForm opcionesBusqueda,
                                                     long id) throws ValidacionException {
        var erroresCompra = formularioCompra.validar();
        var erroresBusqueda = opcionesBusqueda.validar();
        List<ErrorDto> errores = Stream.concat(erroresBusqueda.stream(), erroresCompra.stream()).toList();

        var historialCompras = tm.inTransaction(() -> {
            var compra = compraRepo.obtenerPorId(id);
            if (compra.isEmpty()) {
                errores.add(new ErrorDto("id", ErrorType.COMRPA_INEXISTENTE));
            }

            if (!errores.isEmpty()) {
                throw new ValidacionException(errores);
            }

            return List.<CompraDto>of();
        });

        return historialCompras;
    }

    @Override
    public Optional<CompraDto> consultarDetallesCompra(long idCompra, CompraForm formularioCompra) throws ValidacionException {
        var errores = formularioCompra.validar();

        var detallesCompra = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var compra = compraRepo.obtenerPorId(idCompra);

            if (compra.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.COMRPA_INEXISTENTE));
            } else if (compra.get().getIdUsuario() != formularioCompra.getUsuarioId()) {
                erroresTx.add(new ErrorDto("id", ErrorType.COMPRA_NO_COINCIDENTE));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            var usuario = usuarioRepo.obtenerPorId(formularioCompra.getUsuarioId());
            var juego = juegoRepo.obtenerPorId(formularioCompra.getJuegoId());
            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            return Optional.ofNullable(Mapper.mapCompraEntidadADto(compra.orElse(null), usuarioDto, juegoDto));
        });

        return detallesCompra;
    }

    @Override
    public Optional<CompraDto> solicitarReembolso(long idCompra, OpcionesReembolsoEnum opcionesReembolso) throws ValidacionException {
        var reembolsoSolicitado = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var compra = compraRepo.obtenerPorId(idCompra);

            if (compra.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.COMPRA_NO_EXISTENTE));
                throw new ValidacionException(erroresTx);
            }

            var usuario = usuarioRepo.obtenerPorId(compra.get().getIdUsuario());
            var juego = juegoRepo.obtenerPorId(compra.get().getIdJuego());
            var bibloteca = bibliotecaRepo.buscarJuegoUsuario(compra.get().getIdUsuario(), compra.get().getIdJuego());

            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaCompra = compra.get().getFechaCompra().toLocalDate();

            if (usuario.isEmpty()) {
                erroresTx.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            }
            if (juego.isEmpty()) {
                erroresTx.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            }
            if (bibloteca.isEmpty()) {
                erroresTx.add(new ErrorDto("biblioteca", ErrorType.NO_ENCONTRADO));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            if (compra.get().getEstado() != EstadoCompraEnum.COMPLETADO) {
                erroresTx.add(new ErrorDto("estado", ErrorType.COMPRA_NO_COMPLETADA));
            }
            if (fechaActual.isAfter(fechaCompra.plusDays(DIAS_MAXIMOS))) {
                erroresTx.add(new ErrorDto("plazo", ErrorType.PLAZO_EXPIRADO));
            }
            if (usuario.get().getId() != compra.get().getIdUsuario()) {
                erroresTx.add(new ErrorDto("usuario", ErrorType.USUARIO_NO_COINCIDENTE));
            }
            if (juego.get().getId() != compra.get().getIdJuego()) {
                erroresTx.add(new ErrorDto("id", ErrorType.JUEGO_NO_COINCIDENTE));
            }
            if (bibloteca.get().getIdUsuario() != compra.get().getIdUsuario()) {
                erroresTx.add(new ErrorDto("id", ErrorType.BIBLIOTECA_NO_COINCIDENTE));
            }
            if (bibloteca.get().getHorasJugadasTotal() > MAX_HORAS_JUGADAS) {
                erroresTx.add(new ErrorDto("compra", ErrorType.COMPRA_NO_REEMBOLSABLE));
            }
            if (opcionesReembolso == null) {
                erroresTx.add(new ErrorDto("opciones", ErrorType.OPCIONES_VACIAS));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            usuarioRepo.actualizar(usuario.get().getId(), new UsuarioForm(usuario.get().getNombreUsuario(), usuario.get().getEmail(),
                    usuario.get().getContrasenha(), usuario.get().getNombreReal(), usuario.get().getPais(), usuario.get().getCumpleanhos(),
                    usuario.get().getFechaRegistro(), usuario.get().getAvatar(),
                    (usuario.get().getCreditoSteam()) + (compra.get().getPrecioSinDescuento() - compra.get().getPrecioSinDescuento() *
                            (compra.get().getDescuentoAplicado() / DESCUENTO)), usuario.get().getEstado()));

            bibliotecaRepo.eliminar(bibloteca.get().getId());

            var compraActualizada = compraRepo.actualizar(compra.get().getId(), new CompraForm(compra.get().getIdUsuario(), compra.get().getIdJuego(),
                    compra.get().getFechaCompra(), compra.get().getMetodoDePago(), compra.get().getPrecioSinDescuento(),
                    compra.get().getDescuentoAplicado(), (double) (compra.get().getPrecioSinDescuento() - compra.get().getPrecioSinDescuento() *
                    ((double) compra.get().getDescuentoAplicado() / DESCUENTO)), EstadoCompraEnum.REEMBOLSADA));

            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            return Optional.ofNullable(Mapper.mapCompraEntidadADto(compraActualizada.orElse(null), usuarioDto, juegoDto));
        });

        return reembolsoSolicitado;
    }

    @Override
    public String generarFactura(long idCompra) throws ValidacionException, IOException {
        DatosFactura datos = obtenerDatosFactura(idCompra);
        return escribirFicheroFactura(datos);
    }

    private DatosFactura obtenerDatosFactura(long idCompra) throws ValidacionException {

        var datosObtenidos = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var compra = compraRepo.obtenerPorId(idCompra);

            if (compra.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.COMPRA_NO_EXISTENTE));
                throw new ValidacionException(erroresTx);
            }

            var usuario = usuarioRepo.obtenerPorId(compra.get().getIdUsuario());
            var juego = juegoRepo.obtenerPorId(compra.get().getIdJuego());

            if (compra.get().getEstado() != EstadoCompraEnum.COMPLETADO) {
                erroresTx.add(new ErrorDto("estado", ErrorType.COMPRA_NO_COMPLETADA));
            }

            if (usuario.isEmpty() || usuario.get().getId() != compra.get().getIdUsuario()) {
                erroresTx.add(new ErrorDto("usuario", ErrorType.USUARIO_NO_COINCIDENTE));
            }

            if (juego.isEmpty() || juego.get().getId() != compra.get().getIdJuego()) {
                erroresTx.add(new ErrorDto("id", ErrorType.JUEGO_NO_COINCIDENTE));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            return new DatosFactura(
                    compra.get().getId(),
                    compra.get().getFechaCompra(),
                    usuario.get().getNombreUsuario(),
                    usuario.get().getEmail(),
                    juego.get().getTitulo(),
                    compra.get().getPrecioSinDescuento(),
                    compra.get().getDescuentoAplicado(),
                    compra.get().getMetodoDePago()
            );
        });

        return datosObtenidos;
    }

    private String escribirFicheroFactura(DatosFactura datos) throws IOException {

        double total = datos.precioSinDescuento() -
                datos.precioSinDescuento() * (datos.descuentoAplicado() / (double) DESCUENTO);

        String nombreFactura = "factura" + separador + datos.nombreUsuario() + separador
                + idCounterFactura + separador + LocalDate.now() + ".txt";

        Path path = Path.of("resources/", nombreFactura);

        Files.createDirectories(path.getParent());

        Files.write(path, List.of(
                "=================================================",
                "==========            Steam©          ===========",
                "==========    Factura simplificada    ===========",
                "=================================================",
                "",
                "Id de la compra: " + datos.idCompra(),
                "Fecha de la compra: " + datos.fechaCompra(),
                "",
                "Nombre de usuario: " + datos.nombreUsuario(),
                "Correo electrónico: " + datos.email(),
                "",
                "Título del videojuego : " + datos.tituloJuego(),
                "Precio: " + datos.precioSinDescuento(),
                "Descuento: " + datos.descuentoAplicado(),
                "Total: " + String.format("%.2f", total),
                "Método de pago: " + datos.metodoPago().toString(),
                "",
                "=================================================",
                "==========            Steam©          ===========",
                "==========             " + LocalDate.now().getYear() + "           ===========",
                "================================================="
        ));

        idCounterFactura++;
        return nombreFactura;
    }

    private record DatosFactura(
            long idCompra,
            LocalDateTime fechaCompra,
            String nombreUsuario,
            String email,
            String tituloJuego,
            float precioSinDescuento,
            int descuentoAplicado,
            MetodoPagoEnum metodoPago
    ) {}
}
