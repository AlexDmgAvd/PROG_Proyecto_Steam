package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.dto.EstadisticasBibliotecaDto;
import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.enums.EstadoInstalacionEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BibliotecaControlador implements IBibliotecaControlador {

    private final IBibliotecaRepo bibliotecaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;
    private int estadisticasId = 1;

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

        bibliotecaUsuario = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.getIdUsuario() == id).toList();

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


        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(b.orElse(null)));


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

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(bibliotecaBuscada.orElse(null)));


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

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(bibliotecaActualizada.orElse(null)));

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

        return Optional.ofNullable(Mapper.mapBibliotecaEntidadADto(biblioteca.orElse(null)));

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

        var bibliotecaUsuario = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.getIdUsuario() == usuario.get().getId()).toList();

        var estadisticas = new EstadisticasBibliotecaDto(estadisticasId++, bibliotecaForm.getJuegoId(), null, bibliotecaForm.getUsuarioId(), null,
                bibliotecaUsuario.size(),
                //Sumar todas las horas de todos los juegos
                ,//Sumar todos los juegos que tengan el estado de Instalado
                ,//Comprobar cuál es el juego que tiene más horas jugadas
                ,//Comprobar cuál es el valor total de toda la biblioteca
                ,//Comprobar todos los juegos que nunca hayan tenido el estado de Instalado


                )


        //Todo cambiar nombre a EstadisticasBiblioteca y hacer la función
        return Optional.ofNullable(estadisticas);
    }


}
