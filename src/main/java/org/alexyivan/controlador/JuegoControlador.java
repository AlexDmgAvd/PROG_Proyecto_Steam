package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.enums.EstadoJuegoENUM;
import org.alexyivan.modelo.enums.OrdenENUM;
import org.alexyivan.modelo.enums.PegiENUM;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

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


        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoCreado.orElse(null));
    }

    @Override
    public List<Optional<JuegoDTO>> buscarJuegos(String titulo, String genero, float precio,
                                                 PegiENUM pegi, EstadoJuegoENUM estado) throws ValidacionException {




        return List.of();
    }

    @Override
    public List<Optional<JuegoDTO>> listarTodosJuegos(OrdenENUM orden) throws ValidacionException {




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
