package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.CompraEntidad;
import org.alexyivan.modelo.form.CompraForm;

import java.util.Optional;

public interface ICompraRepo extends ICrud<CompraEntidad, CompraForm, Long> {

    Optional<CompraEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario);

}
