package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaJuegoEnum;
import org.alexyivan.modelo.form.BusquedaJuegosForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

import java.util.*;

public class JuegoControlador implements IJuegoControlador {

    private final IJuegoRepo juegoRepo;
    private ItransactionManager tm;
    private static final int DESCUENTO_MIN = 0;
    private static final int DESCUENTO_MAX = 100;

    public JuegoControlador(IJuegoRepo juegoRepo, ITransactionManager tm) {
        this.juegoRepo = juegoRepo;
        this.tm = tm;
    }

    @Override
    public Optional<JuegoDto> anhadirJuegoCatalogo(JuegoForm formulario) throws ValidacionException {

        //Validaciones
        var errores = formulario.validar();

        Optional<JuegoEntidad> juegoCreado = tm.inTransaction(() -> {
            var juego = juegoRepo.obtenerTitulo(formulario.getTitulo());

            if (juego.isPresent()) {

                errores.add(new ErrorDto("existe", ErrorType.JUEGO_EXISTENTE));
                throw new IllegalStateException();
            }
            return juegoRepo.crear(formulario);

        });

        //Si la lista de errores no está vacía manda los errores
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoCreado.orElse(null)));
    }

    @Override
    public List<JuegoDto> buscarJuegos(BusquedaJuegosForm busquedaJuegos) throws ValidacionException {


        var errores = busquedaJuegos.validar();

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        List<JuegoEntidad> juegosFiltrados;
        juegosFiltrados = juegoRepo.obtenerTodos();
        var jf = juegosFiltrados.stream();


        if (!busquedaJuegos.getTitulo().isEmpty()) {
            jf.filter(j -> j.getTitulo().contains(busquedaJuegos.getTitulo()));
        }

        if (!busquedaJuegos.getGenero().isEmpty()) {
            jf.filter(j -> j.getGenero().equals(busquedaJuegos.getGenero()));

        }
        if (busquedaJuegos.getPrecio() != null) {
            jf.filter(j -> j.getPrecioBase() <= busquedaJuegos.getPrecio());

        }
        if (!busquedaJuegos.getPegi().isEmpty()) {
            jf.filter(j -> j.getRangoEdad().toString().equals(busquedaJuegos.getPegi()));
        }

        if (!busquedaJuegos.getEstado().isEmpty()) {
            jf.filter(j -> j.getEstado().toString().equals(busquedaJuegos.getEstado()));
        }


        return jf.map(Mapper::mapJuegoEntidadADto).toList();
    }

    @Override
    public List<JuegoDto> listarTodosJuegos(OrdenBusquedaJuegoEnum orden) throws ValidacionException {

        List<JuegoEntidad> juegosOriginales;




        if (orden.equals(OrdenBusquedaJuegoEnum.ALFABETICO)) {
            List<JuegoDto> juegosFiltrados = juegoRepo.obtenerTodos().
                    stream().map(Mapper::mapJuegoEntidadADto).toList();
            juegosFiltrados.sort(Comparator.comparing(JuegoDto::getTitulo));

            return juegosFiltrados;
        }


        if (orden.equals(OrdenBusquedaJuegoEnum.PRECIO)) {
            List<JuegoDto> juegosFiltrados = juegoRepo.obtenerTodos().
                    stream().map(Mapper::mapJuegoEntidadADto).toList();
            juegosFiltrados.sort(Comparator.comparing(JuegoDto::getPrecioBase));

            return juegosFiltrados;

        }

        if (orden.equals(OrdenBusquedaJuegoEnum.FECHA)) {
            List<JuegoDto> juegosFiltrados = juegoRepo.obtenerTodos().
                    stream().map(Mapper::mapJuegoEntidadADto).toList();
            juegosFiltrados.sort(Comparator.comparing(JuegoDto::getFechaPublicacion));

            return juegosFiltrados;

        }

        juegosOriginales = juegoRepo.obtenerTodos();
        return juegosOriginales.stream().map(Mapper::mapJuegoEntidadADto).toList();

    }

    @Override
    public Optional<JuegoDto> consultarJuego(long id) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        var juego = juegoRepo.obtenerPorId(id);
        if (juego.isEmpty()) {

            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juego.orElse(null)));
    }

    @Override
    public Optional<JuegoDto> actualizarDescuento(long id, int descuento) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (descuento < DESCUENTO_MIN || descuento > DESCUENTO_MAX) {
            errores.add(new ErrorDto("descuento", ErrorType.DESCUENTO_INVALIDO));

        }

        var juego = juegoRepo.obtenerPorId(id);

        if (juego.isEmpty()) {

            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        var juegoActualizado =  juegoRepo.actualizar(id, new JuegoForm(juego.get().getTitulo(), juego.get().getDescripcion(), juego.get().getDesarrolladora(),
                juego.get().getFechaPublicacion(), juego.get().getPrecioBase(), descuento, juego.get().getGenero(),
                juego.get().getRangoEdad(), juego.get().getIdiomasDisponibles(), juego.get().getEstado()));

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoActualizado.orElse(null)));


    }

    @Override
    public Optional<JuegoDto> cambiarEstado(long id, EstadoJuegoEnum estado) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (estado == null) {
            errores.add(new ErrorDto("estado", ErrorType.ESTADO_INVALIDO));

        }

        var juego = juegoRepo.obtenerPorId(id);

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));

        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);

        }


        var juegoActualizado =  juegoRepo.actualizar(id, new JuegoForm(juego.get().getTitulo(), juego.get().getDescripcion(), juego.get().getDesarrolladora(),
                juego.get().getFechaPublicacion(), juego.get().getPrecioBase(), juego.get().getDescuentoActual(), juego.get().getGenero(),
                juego.get().getRangoEdad(), juego.get().getIdiomasDisponibles(), estado));
        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoActualizado.orElse(null)));
    }
}
