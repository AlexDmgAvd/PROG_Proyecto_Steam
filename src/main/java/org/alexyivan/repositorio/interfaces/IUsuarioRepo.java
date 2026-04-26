package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.form.UsuarioForm;

import java.util.Optional;

public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {


    Optional<UsuarioEntidad> obtenerPorEmail(String email);

    Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombre);


}
