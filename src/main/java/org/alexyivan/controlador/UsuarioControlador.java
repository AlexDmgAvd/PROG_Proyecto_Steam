package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.UsuarioDTO;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.UsuarioForm;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.time.LocalDate;
import java.util.Optional;

public class UsuarioControlador implements IUsuarioControlador {

    private final IUsuarioRepo usuarioRepo;


    public UsuarioControlador(IUsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }


    @Override
    public Optional<UsuarioDTO> registrarUsuario(UsuarioForm usuarioForm) throws ValidacionException {
        //Validar formulario
        var errores = usuarioForm.validar();
        var usuario = usuarioRepo.obtenerPorEmail(usuarioForm.getEmail());
        if (usuario.isPresent())
            errores.add(new ErrorDto("email", ErrorType.EMAIL_INVALIDO));

        if(!errores.isEmpty())
            throw new ValidacionException(errores);
        //Ejecutar
        var usuarioCreado = usuarioRepo.crear(usuarioForm);


        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuarioCreado.orElse(null)));
    }

    @Override
    public Optional<UsuarioDTO> consultarUsuario(Optional<Integer> id, Optional<String> nombreUsuario) throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public void anhadirSaldo(int id, float cantidad) throws ValidacionException {

    }

    @Override
    public Optional<UsuarioDTO> consultarSaldo(int id) throws ValidacionException {
        return Optional.empty();
    }
}
