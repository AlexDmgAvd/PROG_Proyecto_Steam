package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

public interface IJuegoRepo extends ICrud<JuegoEntidad, JuegoForm, Long> {

    Optional<JuegoEntidad> obtenerTitulo (String titulo);


}
