package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.UsuarioDTO;
import org.alexyivan.modelo.form.UsuarioForm;

import java.time.LocalDate;
import java.util.Optional;

public class UsuarioControlador implements IUsuarioControlador {


    @Override
    public Optional<UsuarioForm> registrarUsuario(String nombreUsuario, String email, String contrasenha,
                                                  String nombreReal, String pais, LocalDate fechaNacimiento) {


        return Optional.empty();
    }

    @Override
    public Optional<UsuarioDTO> consultarUsuario(Optional<Integer> id, Optional<String> nombreUsuario) {
        return Optional.empty();
    }

    @Override
    public void anhadirSaldo(int id, float cantidad) {

    }

    @Override
    public Optional<UsuarioDTO> consultarSaldo(int id) {
        return Optional.empty();
    }
}
