package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.enums.EstadoJuegoENUM;
import org.alexyivan.modelo.enums.OrdenENUM;
import org.alexyivan.modelo.enums.PegiENUM;
import org.alexyivan.modelo.form.BusquedaJuegosForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

import java.util.*;

public class JuegoControlador implements IJuegoControlador {

    private final IJuegoRepo juegoRepo;
    private final int DESCUENTO_MIN = 0;
    private final int DESCUENTO_MAX = 100;

    public JuegoControlador(IJuegoRepo juegoRepo) {
        this.juegoRepo = juegoRepo;
    }

    @Override
    public Optional<JuegoDTO> anhadirJuegoCatalogo(JuegoForm formulario) throws ValidacionException {

        //Validaciones
        var errores = formulario.validar();

        var juego = juegoRepo.obtenerTitulo(formulario.getTitulo());

        if (juego.isPresent()) {

            errores.add(new ErrorDto("existe", ErrorType.JUEGO_EXISTENTE));
        }

        //Si la lista de errores no está vacía manda los errores
        if (!errores.isEmpty())
            throw new ValidacionException(errores);


        //Ejecuta la función
        var juegoCreado = juegoRepo.crear(formulario);


        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoCreado.orElse(null)));
    }

    @Override
    public List<JuegoDTO> buscarJuegos(BusquedaJuegosForm busquedaJuegos) throws ValidacionException {


        var errores = busquedaJuegos.validar();

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        List<JuegoEntidad> juegosFiltrados = new ArrayList<>();
        juegosFiltrados = juegoRepo.obtenerTodos();
        var jf = juegosFiltrados.stream();


        if (!busquedaJuegos.getTitulo().isEmpty()) {
            jf.filter(j -> j.titulo().contains(busquedaJuegos.getTitulo()));
        }

        if (!busquedaJuegos.getGenero().isEmpty()) {
            jf.filter(j -> j.genero().equals(busquedaJuegos.getGenero()));

        }
        if (busquedaJuegos.getPrecio() != null) {
            jf.filter(j -> j.precioBase() <= busquedaJuegos.getPrecio());

        }
        if (!busquedaJuegos.getPegi().isEmpty()) {
            jf.filter(j -> j.rangoEdad().toString().equals(busquedaJuegos.getPegi()));
        }

        if (!busquedaJuegos.getEstado().isEmpty()) {
            jf.filter(j -> j.estado().toString().equals(busquedaJuegos.getEstado()));
        }


        return jf.map(Mapper::mapJuegoEntidadADto).toList();
    }

    @Override
    public List<JuegoDTO> listarTodosJuegos(OrdenENUM orden) throws ValidacionException {

        List<JuegoEntidad> juegosOriginales = new ArrayList<>();
        List<ErrorDto> errores = new ArrayList<>();


        if (orden == null) {
            juegosOriginales = juegoRepo.obtenerTodos();
            return juegosOriginales.stream().map(Mapper::mapJuegoEntidadADto).toList();
        }

        if (orden.equals(OrdenENUM.ALFABETICO)) {
            // List<JuegoDTO> juegosFiltrados = new ArrayList<>();
            // juegosOriginales = juegoRepo.obtenerTodos();
            // juegosOriginales.stream().map(Mapper::mapJuegoEntidadADto).toList();
            // return juegosFiltrados.sort(juegosOriginales);
        }

        if (orden.equals(OrdenENUM.PRECIO)) {

        }

        if (orden.equals(OrdenENUM.FECHA)) {


        }


    }

    @Override
    public Optional<JuegoDTO> consultarJuego(long id) throws ValidacionException {
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
    public Optional<JuegoDTO> actualizarDescuento(long id, int descuento) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (descuento < DESCUENTO_MIN || descuento > DESCUENTO_MAX){
            errores.add(new ErrorDto("descuento", ErrorType.DESCUENTO_INVALIDO));

        }

        var juego = juegoRepo.obtenerPorId(id);

        if (juego.isEmpty()) {

            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        juegoRepo.actualizar(id,new JuegoForm(juego.get().titulo(), juego.get().descripcion(), juego.get().desarrolladora(),
                juego.get().fechaPublicacion(),juego.get().precioBase(),descuento,juego.get().genero(),
                juego.get().rangoEdad(),juego.get().idiomasDisponibles(), juego.get().estado()));

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juego.orElse(null)));



    }

    @Override
    public boolean cambiarEstado(long id, EstadoJuegoENUM estado) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (estado == null || estado.toString().equals(EstadoJuegoENUM.)){
            errores.add(new ErrorDto("estado", ErrorType.ESTADO_INVALIDO));

        }

        var juego = juegoRepo.obtenerPorId(id);

        if (juego.isEmpty()) {

            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        juegoRepo.actualizar(id,new JuegoForm(juego.get().titulo(), juego.get().descripcion(), juego.get().desarrolladora(),
                juego.get().fechaPublicacion(),juego.get().precioBase(),descuento,juego.get().genero(),
                juego.get().rangoEdad(),juego.get().idiomasDisponibles(), juego.get().estado()));
    }
}
