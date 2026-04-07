package org.alexyivan.repositorio.interfaces;


import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.form.BibliotecaForm;

import java.util.Optional;

public interface IBibliotecaRepo extends ICrud<BibliotecaEntidad, BibliotecaForm, Long> {

    Optional<BibliotecaEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario);


}
