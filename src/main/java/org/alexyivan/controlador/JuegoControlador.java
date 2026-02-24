package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

public class JuegoControlador implements IJuegoControlador {

    private final IJuegoRepo juegoRepo ;

    public JuegoControlador(IJuegoRepo juegoRepo) {
        this.juegoRepo = juegoRepo;
    }
}
