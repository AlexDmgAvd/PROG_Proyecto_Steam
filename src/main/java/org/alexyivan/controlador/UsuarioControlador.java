package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.UsuarioDTO;
import org.alexyivan.modelo.enums.EstadoCuentaENUM;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.UsuarioForm;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.alexyivan.repositorio.inmemory.UsuarioRepoInMemory.listaPaises;

public class UsuarioControlador implements IUsuarioControlador {

    public static final int CONST_CERO = 0;
    public static final double SALDO_MINIMO = 5.00;
    public static final double SALDO_MAXIMO = 500.00;
    private final IUsuarioRepo usuarioRepo;


    public UsuarioControlador(IUsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }


    @Override
    public Optional<UsuarioDTO> registrarUsuario(UsuarioForm usuarioForm) throws ValidacionException {

        //Validar formulario
        var errores = usuarioForm.validar();

        //Validación de email en repositorio
        var usuario = usuarioRepo.obtenerPorEmail(usuarioForm.getEmail());
        if (usuario.isPresent())
            errores.add(new ErrorDto("email", ErrorType.EMAIL_INVALIDO));

        //Validación de que el nombre no existe en el repositorio
        usuario = usuarioRepo.obtenerPorNombre(usuarioForm.getNombreUsuario());
        if (usuario.isPresent()) {
            errores.add(new ErrorDto("nombre", ErrorType.NOMBRE_EXISTENTE));
        }

        //Validación de que el país está permitido en el repositorio
        if (!listaPaises.contains(usuarioForm.getPais())) {
            errores.add(new ErrorDto("pais", ErrorType.PAIS_INEXISTENTE));

        }

        //Si la lista de errores no está vacía manda los errores
        if (!errores.isEmpty())
            throw new ValidacionException(errores);


        //Ejecuta la función
        var usuarioCreado = usuarioRepo.crear(usuarioForm);


        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuarioCreado.orElse(null)));
    }

    @Override
    public Optional<UsuarioDTO> consultarUsuarioNombreUsuario(String nombreUsuario) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (nombreUsuario.isEmpty()) {
            errores.add(new ErrorDto("vacio", ErrorType.BUSQUEDA_INVALIDA));

        }
        var usuario = usuarioRepo.obtenerPorNombre(nombreUsuario);

        //TODO Hace falta DTO de estadísticas de juego

        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuario.orElse(null)));
    }

    @Override
    public Optional<UsuarioDTO> consultarUsuarioId(Long id) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (id == null) {
            errores.add(new ErrorDto("vacio", ErrorType.BUSQUEDA_INVALIDA));

        }

        //TODO Hace falta DTO de estadísticas de juego
        var usuario = usuarioRepo.obtenerPorId(id);

        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuario.orElse(null)));
    }


    @Override
    public void anhadirSaldo(long id, float cantidad) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        //Validaciones


        //Comprobar que el saldo cumple las validaciones
        if (cantidad < CONST_CERO) {

            errores.add(new ErrorDto("cantidad", ErrorType.FORMATO_INVALIDO));
        }

        if (cantidad < SALDO_MINIMO || cantidad > SALDO_MAXIMO) {
            errores.add(new ErrorDto("cantidad", ErrorType.SALDO_INVALIDO));
        }

        //Comprobar que el usuario existe en el repositorio
        var usuario = usuarioRepo.obtenerPorId(id);
        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (usuario.get().estado() != EstadoCuentaENUM.ACTIVA) {
            errores.add(new ErrorDto("cuenta", ErrorType.ESTADO_CUENTA));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        usuarioRepo.actualizar(id, new UsuarioForm(usuario.get().nombreUsuario(), usuario.get().email(),
                usuario.get().contrasenha(), usuario.get().nombreReal(), usuario.get().pais(), usuario.get().cumpleanhos(), usuario.get().fechaRegistro(),
                usuario.get().avatar(), cantidad, usuario.get().estado()));


    }


    @Override
    public Optional<UsuarioDTO> consultarSaldo(long id) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(id);

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuario.orElse(null)));
    }
}
