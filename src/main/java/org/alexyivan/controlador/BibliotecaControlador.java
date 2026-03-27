package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.enums.EstadoInstalacionEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.modelo.form.CompraForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BibliotecaControlador implements IBibliotecaControlador {

    private final IBibliotecaRepo bibliotecaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;

    public BibliotecaControlador(IBibliotecaRepo bibliotecaRepo, IUsuarioRepo usuarioRepo, IJuegoRepo juegoRepo) {
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
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

        bibliotecaUsuario = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.idUsuario() == id).toList();

        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.ALFABETICO)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(Mapper::mapBibliotecaEntidadADto).toList();
            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getJuego().getTitulo()));

            return bibliotecaFiltrada;
        }

        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.ULTIMA_SESION)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(Mapper::mapBibliotecaEntidadADto).toList();
            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getUltimaFechaDeJuego()));

            return bibliotecaFiltrada;
        }

        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.TIEMPO_JUEGO)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(Mapper::mapBibliotecaEntidadADto).toList();
            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getHorasJugadasTotal()));

            return bibliotecaFiltrada;
        }
        if (busquedaBiblioteca.equals(OrdenBusquedaBibliotecaEnum.FECHA_ADQUISICION)) {
            List<BibliotecaDto> bibliotecaFiltrada = bibliotecaRepo.obtenerTodos().
                    stream().map(Mapper::mapBibliotecaEntidadADto).toList();
            bibliotecaFiltrada.sort(Comparator.comparing(b -> b.getFechaAdquisicion()));

            return bibliotecaFiltrada;
        }


        return bibliotecaUsuario.stream().map(Mapper::mapBibliotecaEntidadADto).toList();


    }

    @Override
    public Optional<BibliotecaDto> anhadirJuego(CompraForm compra) {

        var errores = compra.validar();
        var usuario = usuarioRepo.obtenerPorId(compra.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(compra.getJuegoId());
        var biblioteca = bibliotecaRepo.obtenerTodos();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!biblioteca.stream().filter(b -> b.idJuego() == compra.getJuegoId()).findFirst().isEmpty()
                && biblioteca.stream().filter(b -> b.idUsuario() == compra.getUsuarioId()).findFirst().isPresent()) {

            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));

        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var b = bibliotecaRepo.crear(new BibliotecaForm(compra.getUsuarioId(), compra.getJuegoId(),
                compra.getFechaCompra().toLocalDate(), 0.0f, null,
                EstadoInstalacionEnum.NO_INSTALADO));


        return Optional.empty();


    }

    @Override
    public  Optional<BibliotecaDto> eliminarJuego(long usuarioId, long juegoId) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(usuarioId);
        var juego = juegoRepo.obtenerPorId(juegoId);
        var biblioteca = bibliotecaRepo.obtenerTodos();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (biblioteca.stream().filter(b -> b.idUsuario() == usuarioId).findFirst().isEmpty()) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
        }

        if (biblioteca.stream().filter(b -> b.idJuego() == juegoId).findFirst().isEmpty()
                && biblioteca.stream().filter(b -> b.idUsuario() == usuarioId).findFirst().isPresent()) {

            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));

        }

        List<BibliotecaEntidad> bibliotecaBuscada;
        bibliotecaBuscada = bibliotecaRepo.obtenerTodos();
        var br = bibliotecaBuscada;

        //Crear una función en el repositorio para buscar una biblioteca por id de juego y id de usuario
        br.stream().filter(b -> b.idUsuario() == usuarioId);
        br.stream().filter(b -> b.idJuego() == juegoId);



        long id = bibliotecaBuscada.getFirst().id();
        bibliotecaRepo.eliminar(id);

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto();


    }

    @Override
    public float actualizarTempoJuego(long idUsuario, long idJuego, float horas) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(idUsuario);
        var juego = juegoRepo.obtenerPorId(idJuego);


        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (horas < 0) {
            errores.add(new ErrorDto("horas", ErrorType.HORAS_INSUFICIENTES));

        }
        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        var biblioteca = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.idUsuario() == idUsuario
                && b.idJuego() == idJuego).findFirst();


        bibliotecaRepo.actualizar(biblioteca.get().id(), new BibliotecaForm(biblioteca.get().idUsuario(), biblioteca.get().idJuego(),
                biblioteca.get().fechaAdquisicion(), (biblioteca.get().horasJugadasTotal() + horas), biblioteca.get().ultimaFechaDeJuego(),
                biblioteca.get().estadoInstalacion()));

        return biblioteca.get().horasJugadasTotal() + horas;

    }

    @Override
    public String consultarUltimaSesion(long idUsuario, long idJuego) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(idUsuario);
        var juego = juegoRepo.obtenerPorId(idJuego);


        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        var biblioteca = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.idUsuario() == idUsuario
                && b.idJuego() == idJuego).findFirst();

        var ultimaSesion = biblioteca.get().ultimaFechaDeJuego();

        long dias = ChronoUnit.DAYS.between(ultimaSesion, LocalDate.now());

        String salida = "Última sesión" + dias + ultimaSesion;
        return salida;

    }


}
