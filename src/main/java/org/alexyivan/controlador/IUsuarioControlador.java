package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.UsuarioDTO;
import org.alexyivan.modelo.form.UsuarioForm;

import java.time.LocalDate;
import java.util.Optional;

public interface IUsuarioControlador {

    Optional<UsuarioDTO> registrarUsuario (UsuarioForm usuarioForm) throws ValidacionException;

    Optional<UsuarioDTO> consultarUsuarioNombreUsuario(String nombreUsuario) throws ValidacionException;

    Optional<UsuarioDTO> consultarUsuarioId(Long id) throws ValidacionException;

    void anhadirSaldo (long id, float cantidad) throws ValidacionException;

    Optional<UsuarioDTO> consultarSaldo (long id) throws ValidacionException;




}
