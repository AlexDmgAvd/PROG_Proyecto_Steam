package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.UsuarioDTO;
import org.alexyivan.modelo.form.UsuarioForm;

import java.time.LocalDate;
import java.util.Optional;

public interface IUsuarioControlador {

    Optional<UsuarioForm> registrarUsuario (String nombreUsuario, String email, String contrasenha, String nombreReal,
                           String pais, LocalDate fechaNacimiento);

    Optional<UsuarioDTO> consultarUsuario(Optional<Integer> id, Optional<String> nombreUsuario);

    void anhadirSaldo (int id, float cantidad);

    Optional<UsuarioDTO> consultarSaldo (int id);


}
