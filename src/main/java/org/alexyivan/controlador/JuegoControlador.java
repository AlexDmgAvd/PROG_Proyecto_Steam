package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.enums.EstadoJuegoENUM;
import org.alexyivan.modelo.enums.OrdenENUM;
import org.alexyivan.modelo.enums.PegiENUM;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoControlador implements IJuegoControlador {

    private final IJuegoRepo juegoRepo;

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
    public List<JuegoDTO> buscarJuegos(String titulo, String genero, float precio,
                                       String pegi, String estado) throws ValidacionException {


        List<JuegoEntidad> juegosFiltrados = new ArrayList<>();
        List<ErrorDto> errores = new ArrayList<>();
        juegosFiltrados = juegoRepo.obtenerTodos();
        var jf = juegosFiltrados.stream();
        if (titulo.isEmpty() & genero.isEmpty() & precio == 0 & pegi.isEmpty() & estado.isEmpty()) {
            errores.add(new ErrorDto("busqueda", ErrorType.BUSQUEDA_INVALIDA));

        } else {

            if (!titulo.isEmpty()) {
                jf.filter(j -> j.titulo().contains(titulo));
            }

            if (!genero.isEmpty()) {
                jf.filter(j -> j.genero().equals(genero));

            }
//            if (precio != 0) {
//                juegosFiltrados = juegoRepo.buscarRangoPrecio(precio).stream()
//                        .map(Mapper::mapJuegoEntidadADto).toList();
//            }
//
//            if (!pegi.isEmpty()) {
//                juegosFiltrados = juegoRepo.buscarEstado(pegi).stream()
//                        .map(Mapper::mapJuegoEntidadADto).toList();
//            }
//
//            if (!estado.isEmpty()) {
//                juegosFiltrados = juegoRepo.buscarEstado(estado).stream()
//                        .map(Mapper::mapJuegoEntidadADto).toList();
//            }
        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        return jf.map(Mapper::mapJuegoEntidadADto).toList();
    }

    @Override
    public List<JuegoDTO> listarTodosJuegos(OrdenENUM orden) throws ValidacionException {

        List<JuegoDTO> juegosFiltrados = new ArrayList<>();
        List<ErrorDto> errores = new ArrayList<>();

        if (orden.equals(OrdenENUM.ALFABETICO)) {

            //return juegosFiltrados = juegoRepo.
        }

        return List.of();
    }

    @Override
    public Optional<JuegoDTO> consultarJuego(long id) throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public Optional<JuegoDTO> actualizarDescuento(long id, int descuento) throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public boolean cambiarEstado(long id, EstadoJuegoENUM estado) throws ValidacionException {
        return false;
    }
}
