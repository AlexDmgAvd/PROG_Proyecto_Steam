package org.alexyivan.controlador;


import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.dto.ResenhaDto;
import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.entidad.ResenhaEntidad;
import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.enums.*;
import org.alexyivan.modelo.form.*;
import org.alexyivan.repositorio.inmemory.*;
import org.alexyivan.repositorio.interfaces.*;
import org.alexyivan.transaction.ITransactionManager;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class ResenhaControlador implements IResenhaControlador {

    public static final int CONS_CERO = 0;
    private final IResenhaRepo resenhaRepo;
    private final IJuegoRepo juegoRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IBibliotecaRepo bibliotecaRepo;
    private ITransactionManager tm;

    public ResenhaControlador(ITransactionManager tm, IBibliotecaRepo bibliotecaRepo, IUsuarioRepo usuarioRepo,
                              IJuegoRepo juegoRepo, IResenhaRepo resenhaRepo) {
        this.tm = tm;
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
        this.resenhaRepo = resenhaRepo;
    }

    @Override
    public Optional<ResenhaDto> escribirResenha(ResenhaForm formularioResenha) throws ValidacionException {
        var errores = formularioResenha.validar();
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var resenhaCreada = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var usuario = usuarioRepo.obtenerPorId(formularioResenha.getIdUsuario());
            var juego = juegoRepo.obtenerPorId(formularioResenha.getIdJuego());
            var bibliotecaUsuario = bibliotecaRepo.buscarJuegoUsuario(formularioResenha.getIdJuego(),
                    formularioResenha.getIdUsuario());
            var resenhas = resenhaRepo.obtenerTodos().stream()
                    .filter(r -> r.getIdUsuario() == formularioResenha.getIdUsuario())
                    .filter(r -> r.getIdJuego() == formularioResenha.getIdJuego())
                    .filter(r -> r.getTextoAnalisis()
                            .equalsIgnoreCase(formularioResenha.getTextoAnalisis())).findFirst();


            if (usuario.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }
            if (juego.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }
            if (bibliotecaUsuario.isEmpty()) {
                erroresTx.add(new ErrorDto("juego", ErrorType.JUEGO_NO_EN_BIBLIOTECA));
            }
            if (resenhas.isPresent()) {
                erroresTx.add(new ErrorDto("reseña", ErrorType.RESENHA_DUPLICADA));
            }
            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            var resenha = resenhaRepo.crear(new ResenhaForm(formularioResenha.getIdUsuario(),
                    formularioResenha.getUsuario(), formularioResenha.getIdJuego(), formularioResenha.getJuego(),
                    formularioResenha.isRecomendado(), formularioResenha.getTextoAnalisis(),
                    formularioResenha.getHorasJugadas(), formularioResenha.getFechaPublicacion(),
                    formularioResenha.getUltimaFechaEdicion(), EstadoResenhaEnum.PUBLICADA));

            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            return Optional.ofNullable(Mapper.mapResenhaEntidadADto(resenha.orElse(null), usuarioDto, juegoDto));
        });

        return resenhaCreada;
    }

    @Override
    public Optional<ResenhaDto> eliminarResenha(long idResenha, long idUsuario) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (idResenha <= CONS_CERO) {
            errores.add(new ErrorDto("id", ErrorType.BUSQUEDA_INVALIDA));
        }

        if (idUsuario <= CONS_CERO) {
            errores.add(new ErrorDto("id", ErrorType.BUSQUEDA_INVALIDA));
        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var resenhaEliminada = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var resenha = resenhaRepo.obtenerPorId(idResenha);

            if (resenha.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }
            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }
            if (resenha.get().getIdUsuario() != idUsuario) {
                erroresTx.add(new ErrorDto("id", ErrorType.RESENHA_NO_PERTENECE));
            }
            if (resenha.get().getEstado() == EstadoResenhaEnum.BORRADA) {
                erroresTx.add(new ErrorDto("borrada", ErrorType.RESENHA_YA_BORRADA));
            }
            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }


            var usuario = usuarioRepo.obtenerPorId(idUsuario);
            var juego = juegoRepo.obtenerPorId(resenha.get().getIdJuego());
            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            var resenhaActualizada = resenhaRepo.actualizar(resenha.get().getId(),
                    new ResenhaForm(resenha.get().getIdUsuario(), usuarioDto,
                            resenha.get().getIdJuego(), juegoDto, resenha.get().isRecomendado(),
                            resenha.get().getTextoAnalisis(), resenha.get().getHorasJugadas(),
                            resenha.get().getFechaPublicacion(),
                            resenha.get().getUltimaFechaEdicion(), EstadoResenhaEnum.BORRADA));

            return Optional.ofNullable
                    (Mapper.mapResenhaEntidadADto(resenhaActualizada.orElse(null), usuarioDto, juegoDto));
        });

        return resenhaEliminada;
    }


    @Override
    public List<ResenhaDto> verResenhasJuego(long id, Optional<BusquedaResenhasValoracionEnum> valoracion,
                                             Optional<OrdenResenhasEnum> orden) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (id <= CONS_CERO) {
            errores.add(new ErrorDto("id", ErrorType.BUSQUEDA_INVALIDA));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var resenhasObtenidas = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var juego = juegoRepo.obtenerPorId(id);
            if (juego.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }
            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            var resenhas = resenhaRepo.obtenerTodos().stream().filter(r ->
                    r.getIdJuego() == id && r.getEstado() == EstadoResenhaEnum.PUBLICADA

            );

            if (valoracion.isPresent()) {
                if (valoracion.get().equals(BusquedaResenhasValoracionEnum.POSITIVA)) {
                    resenhas = resenhas.filter(r -> r.isRecomendado());
                }
                if (valoracion.get().equals(BusquedaResenhasValoracionEnum.NEGATIVA)) {
                    resenhas = resenhas.filter(r -> !r.isRecomendado());
                }
            }

            if (orden.isPresent()) {
                if (orden.get().equals(OrdenResenhasEnum.RECIENTES)) {
                    resenhas = resenhas.sorted((a, b) ->
                            a.getFechaPublicacion().compareTo(b.getFechaPublicacion()));
                }
                if (orden.get().equals(OrdenResenhasEnum.UTILES)) {
                    resenhas = resenhasOrdenadasUtilidad(resenhas);
                }
            }

            return resenhas.map(r -> {
                Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(r.getIdJuego());
                Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(r.getIdUsuario());

                Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
                Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

                return Mapper.mapResenhaEntidadADto(r, usuarioDto.orElse(null), juegoDto.orElse(null));
            }).toList();
        });

        return resenhasObtenidas;
    }

    @Override
    public Optional<ResenhaDto> ocultarResenha(long idResenha, long idUsuario) throws
            ValidacionException {

        List<ErrorDto> errores = new ArrayList<>();

        var resenha = resenhaRepo.obtenerPorId(idResenha);

        if (idResenha <= CONS_CERO) {
            errores.add(new ErrorDto("id", ErrorType.BUSQUEDA_INVALIDA));
        }

        if (idUsuario <= CONS_CERO) {
            errores.add(new ErrorDto("id", ErrorType.BUSQUEDA_INVALIDA));
        }


        if (resenha.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        var usuario = usuarioRepo.obtenerPorId(idUsuario);
        var juego = juegoRepo.obtenerPorId(resenha.get().getIdJuego());


        var resenhaOcultada = tm.inTransaction(() -> {

            List<ErrorDto> erroresTx = new ArrayList<>();

            if (juego.isEmpty()){
                erroresTx.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            }


            if (usuario.isEmpty()) {
                erroresTx.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            }

            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            if (resenha.get().getIdUsuario() != usuario.get().getId()) {
                erroresTx.add(new ErrorDto("id", ErrorType.RESENHA_NO_PERTENECE));

            }

            if (resenha.get().getIdJuego() != juego.get().getId()){
                erroresTx.add(new ErrorDto("juego", ErrorType.JUEGO_NO_COINCIDENTE));
            }
            if (resenha.get().getIdUsuario() != idUsuario) {
                erroresTx.add(new ErrorDto("id", ErrorType.RESENHA_NO_PERTENECE));
            }
            if (resenha.get().getEstado() == EstadoResenhaEnum.BORRADA) {
                erroresTx.add(new ErrorDto("borrada", ErrorType.RESENHA_YA_BORRADA));
            }
            if (resenha.get().getEstado() == EstadoResenhaEnum.OCULTA) {
                erroresTx.add(new ErrorDto("oculta", ErrorType.RESENHA_YA_OCULTA));
            }
            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }


            var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
            var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

            var resenhaActualizada = resenhaRepo.actualizar(resenha.get().getId(),
                    new ResenhaForm(resenha.get().getIdUsuario(), usuarioDto,
                            resenha.get().getIdJuego(), juegoDto, resenha.get().isRecomendado(),
                            resenha.get().getTextoAnalisis(), resenha.get().getHorasJugadas(),
                            resenha.get().getFechaPublicacion(),
                            resenha.get().getUltimaFechaEdicion(), EstadoResenhaEnum.OCULTA));
            return Optional.ofNullable
                    (Mapper.mapResenhaEntidadADto(resenhaActualizada.orElse(null), usuarioDto, juegoDto));
        });

        return resenhaOcultada;
    }


    @Override
    public List<ResenhaDto> verResenhaUsuario(ResenhaForm
                                                      formularioResenha, Optional<EstadoResenhaEnum> estado) {

        var errores = formularioResenha.validar();
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var resenhasUsuario = tm.inTransaction(() -> {
            List<ErrorDto> erroresTx = new ArrayList<>();
            var usuario = usuarioRepo.obtenerPorId(formularioResenha.getIdUsuario());
            if (usuario.isEmpty()) {
                erroresTx.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }
            if (!erroresTx.isEmpty()) {
                throw new ValidacionException(erroresTx);
            }

            return resenhaRepo.obtenerTodos().stream()
                    .filter(r -> r.getIdUsuario() == usuario.get().getId())
                    .filter(r -> estado.map(e ->
                            r.getEstado() == e).orElse(true))
                    .map(r -> {
                        Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(r.getIdJuego());
                        Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(r.getIdUsuario());

                        Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
                        Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

                        return Mapper.mapResenhaEntidadADto(r, usuarioDto.orElse(null),
                                juegoDto.orElse(null));
                    }).toList();
        });

        return resenhasUsuario;
    }


    private Stream<ResenhaEntidad> resenhasOrdenadasUtilidad(Stream<ResenhaEntidad> lista) {

        return lista
                .sorted((a, b) -> Float.compare(a.getHorasJugadas(), b.getHorasJugadas()))
                .filter(r -> r.isRecomendado());
    }
}


//    static void main() {
//        IResenhaRepo iResenhaRepo = new ResenhaRepoInMemory();
//        IJuegoRepo iJuegoRepo = new JuegoRepoInMemory();
//        IUsuarioRepo iUsuarioRepo = new UsuarioRepoInMemory();
//        IBibliotecaRepo iBibliotecaRepo = new BibliotecaRepoInMemory();
//        ICompraRepo iCompraRepo = new CompraRepoInMemory();
//
//
//        ITransactionManager tm = new NoOpTransactionManager();
//        JuegoControlador juegoControlador = new JuegoControlador(iJuegoRepo, tm);
//        UsuarioControlador usuarioControlador = new UsuarioControlador(iUsuarioRepo);
//        BibliotecaControlador bibliotecaControlador = new BibliotecaControlador(iBibliotecaRepo, iUsuarioRepo, iJuegoRepo, iCompraRepo);
//        ResenhaControlador resenhaControlador = new ResenhaControlador(iResenhaRepo, iJuegoRepo, iUsuarioRepo, iBibliotecaRepo);
//
//        iUsuarioRepo.crear(new UsuarioForm("kaisquest", "email@email.com", "1234abcd!", "Iván",
//                "España", LocalDate.of(1998, 03, 05), LocalDate.of(2026, 04, 21),
//                "Avatar", 50.0f, EstadoCuentaEmun.ACTIVA));
//
//        iJuegoRepo.crear(new JuegoForm("Clair Obscure: Expedition 33", "Guía a la expedición 33 en su viaje " +
//                "para destruir a la Peintresse para que no pinte la muerte. Explora un mundo inspirado por la Francia de la Belle Époque y " +
//                "combate enemigos únicos" + " en este juego de rol por turnos con mecánicas en tiempo real.", "Sandfall Interactive",
//                LocalDate.of(2025, 04, 24), 44.99f, 20, "RPG, TBS", PegiEnum.PEGI_18,
//                "Español, Francés, Inglés", EstadoJuegoEnum.DISPONIBLE));
//
//        var usuario = usuarioControlador.consultarUsuarioId(1l);
//        var juego = juegoControlador.consultarJuego(1);
//        var biblioteca = bibliotecaControlador.anhadirJuego(new BibliotecaForm(usuario.get().getId(), juego.get().getId(), LocalDate.of(2025, 12, 25),
//                20.4f, LocalDate.of(2026, 01, 9), EstadoInstalacionEnum.INSTALADO));
//
//
//        System.out.println(usuario.get().getNombreUsuario());
//        System.out.println(juego.get().getTitulo());
//
//        resenhaControlador.escribirResenha(new ResenhaForm(1, usuario.orElse(null), 1, juego.orElse(null), true,
//                "Texto de ejemplo del análisisis para que no pite por ser demasiado corto",
//                biblioteca.get().getHorasJugadasTotal(), LocalDate.of(2026, 04, 10), LocalDate.of(2026, 04, 16),
//                EstadoResenhaEnum.PUBLICADA));
//
//        var resenha = resenhaControlador.verResenhasJuego(1l, null, null);
//        System.out.println(resenha.getFirst().getId()+"   " + resenha.getFirst().getTextoAnalisis());
//