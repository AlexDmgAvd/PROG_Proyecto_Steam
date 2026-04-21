package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.dto.EstadisticasBibliotecaDto;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.enums.EstadoCuentaEmun;
import org.alexyivan.modelo.enums.EstadoInstalacionEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.enums.OrdenResenhasEnum;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.UsuarioForm;
import org.alexyivan.repositorio.inmemory.BibliotecaRepoInMemory;
import org.alexyivan.repositorio.inmemory.CompraRepoInMemory;
import org.alexyivan.repositorio.inmemory.JuegoRepoInMemory;
import org.alexyivan.repositorio.inmemory.UsuarioRepoInMemory;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.ICompraRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BibliotecaControlador implements IBibliotecaControlador {

    private final IBibliotecaRepo bibliotecaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;
    private final ICompraRepo compraRepo;
    private int estadisticasId = 1;

    public BibliotecaControlador(IBibliotecaRepo bibliotecaRepo, IUsuarioRepo usuarioRepo, IJuegoRepo juegoRepo, ICompraRepo compraRepo) {
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
        this.compraRepo = compraRepo;
    }

    @Override
    public List<BibliotecaDto> verBibliotecaPersonal(long id, OrdenBusquedaBibliotecaEnum busquedaBiblioteca) {

        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(id);

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        List<BibliotecaEntidad> bibliotecaUsuario;

        bibliotecaUsuario = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.getIdUsuario() == id).toList();

        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.ALFABETICO)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(b -> {
                        Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(b.getIdJuego());
                        Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(b.getIdUsuario());

                        Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
                        Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

                        return Mapper.mapBibliotecaEntidadADto(b,usuarioDto.orElse(null),juegoDto.orElse(null));

                    }).toList();

            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getJuego().getTitulo()));

            return bibliotecaFiltrada;
        }

        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.ULTIMA_SESION)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(b -> {
                        Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(b.getIdJuego());
                        Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(b.getIdUsuario());

                        Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
                        Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

                        return Mapper.mapBibliotecaEntidadADto(b,usuarioDto.orElse(null),juegoDto.orElse(null));

                    }).toList();

            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getUltimaFechaDeJuego()));

            return bibliotecaFiltrada;
        }

        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.TIEMPO_JUEGO)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(b -> {
                        Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(b.getIdJuego());
                        Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(b.getIdUsuario());

                        Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
                        Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

                        return Mapper.mapBibliotecaEntidadADto(b,usuarioDto.orElse(null),juegoDto.orElse(null));

                    }).toList();

            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getHorasJugadasTotal()));

            return bibliotecaFiltrada;
        }
        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.FECHA_ADQUISICION)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(b -> {
                        Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(b.getIdJuego());
                        Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(b.getIdUsuario());

                        Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
                        Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

                        return Mapper.mapBibliotecaEntidadADto(b,usuarioDto.orElse(null),juegoDto.orElse(null));

                    }).toList();

            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getFechaAdquisicion()));

            return bibliotecaFiltrada;
        }


        return bibliotecaUsuario.stream().map(b -> {
            Optional<JuegoEntidad> j = juegoRepo.obtenerPorId(b.getIdJuego());
            Optional<UsuarioEntidad> u = usuarioRepo.obtenerPorId(b.getIdUsuario());

            Optional<JuegoDto> juegoDto = j.map(Mapper::mapJuegoEntidadADto);
            Optional<UsuarioDto> usuarioDto = u.map(Mapper::mapUsuarioEntidadADto);

            return Mapper.mapBibliotecaEntidadADto(b,usuarioDto.orElse(null),juegoDto.orElse(null));

        }).toList();


    }

    @Override
    public Optional<BibliotecaDto> anhadirJuego(BibliotecaForm bibliotecaForm) {

        var errores = bibliotecaForm.validar();
        var usuario = usuarioRepo.obtenerPorId(bibliotecaForm.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(bibliotecaForm.getJuegoId());
        var biblioteca = bibliotecaRepo.obtenerTodos();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!biblioteca.stream().filter(b -> b.getIdJuego() == bibliotecaForm.getJuegoId()).findFirst().isEmpty()
                && biblioteca.stream().filter(b -> b.getIdJuego() == bibliotecaForm.getUsuarioId()).findFirst().isPresent()) {

            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));

        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var b = bibliotecaRepo.crear(new BibliotecaForm(bibliotecaForm.getUsuarioId(), bibliotecaForm.getJuegoId(),
                bibliotecaForm.getFechaAdquisicion(), 0.0f, null,
                EstadoInstalacionEnum.NO_INSTALADO));

        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));


        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(b.orElse(null),usuarioDto,juegoDto));


    }

    @Override
    public Optional<BibliotecaDto> eliminarJuego(BibliotecaForm bibliotecaForm) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(bibliotecaForm.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(bibliotecaForm.getJuegoId());
        var biblioteca = bibliotecaRepo.obtenerTodos();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (biblioteca.stream().filter(b -> b.getIdUsuario() == usuario.get().getId()).findFirst().isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (biblioteca.stream().filter(b -> b.getIdJuego() == juego.get().getId()).findFirst().isEmpty()
                && biblioteca.stream().filter(b -> b.getIdUsuario() == usuario.get().getId()).findFirst().isPresent()) {

            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));

        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var bibliotecaBuscada = bibliotecaRepo.buscarJuegoUsuario(juego.get().getId(), usuario.get().getId());

        bibliotecaRepo.eliminar(bibliotecaBuscada.get().getId());

        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(bibliotecaBuscada.orElse(null),usuarioDto,juegoDto));


    }

    @Override
    public Optional<BibliotecaDto> actualizarTempoJuego(BibliotecaForm bibliotecaForm, float horas) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(bibliotecaForm.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(bibliotecaForm.getJuegoId());


        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (horas < 0) {
            errores.add(new ErrorDto("horas", ErrorType.HORAS_INSUFICIENTES));

        }
        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var biblioteca = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.getIdUsuario() == usuario.get().getId()
                && b.getIdJuego() == juego.get().getId()).findFirst();


        var bibliotecaActualizada = bibliotecaRepo.actualizar(biblioteca.get().getId(), new BibliotecaForm(biblioteca.get().getIdUsuario(), biblioteca.get().getIdJuego(),
                biblioteca.get().getFechaAdquisicion(), (biblioteca.get().getHorasJugadasTotal() + horas), biblioteca.get().getUltimaFechaDeJuego(),
                biblioteca.get().getEstadoInstalacion()));

        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(bibliotecaActualizada.orElse(null),usuarioDto,juegoDto));

    }

    @Override
    public Optional<BibliotecaDto> consultarUltimaSesion(BibliotecaForm bibliotecaForm) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(bibliotecaForm.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(bibliotecaForm.getJuegoId());


        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var biblioteca = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.getIdUsuario() == usuario.get().getId()
                && b.getIdJuego() == juego.get().getId()).findFirst();

        var usuarioDto = Mapper.mapUsuarioEntidadADto(usuario.orElse(null));
        var juegoDto = Mapper.mapJuegoEntidadADto(juego.orElse(null));

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(biblioteca.orElse(null),usuarioDto,juegoDto));

    }

    @Override
    public Optional<EstadisticasBibliotecaDto> estadisticasBiblioteca(BibliotecaForm bibliotecaForm) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(bibliotecaForm.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(bibliotecaForm.getJuegoId());

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var bibliotecaUsuario = bibliotecaRepo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuario.get().getId()).toList();

        var comprasUsuario = compraRepo.obtenerTodos().stream()
                .filter(c -> c.getIdUsuario() == usuario.get().getId());


        // Calcular estadísticas

        // Total de juegos
        int totalJuegos = bibliotecaUsuario.size();

        // Horas totales
        float totalHoras = bibliotecaUsuario.stream()
                .map(BibliotecaEntidad::getHorasJugadasTotal)
                .reduce(0f, (acc, n) -> acc + n);

        // Juegos instalados
        int juegosInstaladosTotales = bibliotecaUsuario.stream()
                .filter(b -> b.getEstadoInstalacion() == EstadoInstalacionEnum.INSTALADO)
                .toList().size();

        // Juego mas jugado
        Optional<BibliotecaEntidad> juegoMasJugado = bibliotecaUsuario.stream()
                .max(Comparator.comparingDouble(BibliotecaEntidad::getHorasJugadasTotal));  //todo

        // Valor total de la biblioteca
        float valorTotalBiblioteca = comprasUsuario
                .map(c -> c.getPrecioSinDescuento() - c.getPrecioSinDescuento() * (c.getDescuentoAplicado() / 100))
                .reduce(0f, (a, b) -> a + b);

        // juegos nunca jugados
        int juegosNuncaJugados = bibliotecaUsuario.stream()
                .filter(b -> b.getHorasJugadasTotal() == 0f)
                .toList().size();


        var estadisticas = new EstadisticasBibliotecaDto(
                estadisticasId++,
                bibliotecaForm.getJuegoId(),
                null,
                bibliotecaForm.getUsuarioId(),
                null,
                totalJuegos,
                totalHoras,
                juegosInstaladosTotales,
                juegoMasJugado.get().getIdJuego(),
                null,
                valorTotalBiblioteca,
                juegosNuncaJugados);


        return Optional.ofNullable(estadisticas);
    }


//    public static void main(String[] args) {
//        IBibliotecaRepo iBibliotecaRepo = new BibliotecaRepoInMemory();
//        IUsuarioRepo iUsuarioRepo = new UsuarioRepoInMemory();
//        IJuegoRepo iJuegoRepo = new JuegoRepoInMemory();
//        ICompraRepo iCompraRepo = new CompraRepoInMemory();
//
//
//        BibliotecaControlador biblioteca = new BibliotecaControlador(iBibliotecaRepo, iUsuarioRepo, iJuegoRepo, iCompraRepo);
//
//        iUsuarioRepo.crear(new UsuarioForm("kaisquest", "email@email.com", "1234abcd!", "Iván",
//                "Spain", LocalDate.of(1998,03,05),LocalDate.of(2026,04,21),
//                "Avatar",50.0f, EstadoCuentaEmun.ACTIVA));
//
//        biblioteca.verBibliotecaPersonal(1, OrdenBusquedaBibliotecaEnum.ALFABETICO);
//
//
//    }


}
