package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.form.UsuarioForm;

import java.util.Optional;

public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {


    public Optional<UsuarioEntidad> obtenerPorEmail (String email);


}
