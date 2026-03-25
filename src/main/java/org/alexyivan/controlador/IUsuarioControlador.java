package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.modelo.form.UsuarioForm;

import java.util.Optional;

public interface IUsuarioControlador {

    Optional<UsuarioDto> registrarUsuario(UsuarioForm usuarioForm) throws ValidacionException;

    Optional<UsuarioDto> consultarUsuario(Long id, String nombreUsuario) throws ValidacionException;

    Optional<UsuarioDto> consultarUsuarioNombreUsuario(String nombreUsuario) throws ValidacionException;

    Optional<UsuarioDto> consultarUsuarioId(Long id) throws ValidacionException;

    void anhadirSaldo(long id, float cantidad) throws ValidacionException;

    Optional<UsuarioDto> consultarSaldo(long id) throws ValidacionException;


}
