package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.UsuarioDTO;
import org.alexyivan.modelo.form.UsuarioForm;

import java.time.LocalDate;
import java.util.Optional;

public interface IUsuarioControlador {

    Optional<UsuarioDTO> registrarUsuario (UsuarioForm usuarioForm) throws ValidacionException;

    Optional<UsuarioDTO> consultarUsuario(Optional<Integer> id, Optional<String> nombreUsuario) throws ValidacionException;

    void anhadirSaldo (int id, float cantidad) throws ValidacionException;

    Optional<UsuarioDTO> consultarSaldo (int id) throws ValidacionException;


}
