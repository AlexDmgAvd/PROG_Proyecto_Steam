package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;


import org.alexyivan.modelo.enums.EstadoCuentaEmun;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.UsuarioForm;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.transaction.ITransactionManager;

import static org.alexyivan.repositorio.inmemory.UsuarioRepoInMemory.listaPaises;

public class UsuarioControlador implements IUsuarioControlador {

    public static final int CONST_CERO = 0;
    public static final double SALDO_MINIMO = 5.00;
    public static final double SALDO_MAXIMO = 500.00;
    private final IUsuarioRepo usuarioRepo;
    private ITransactionManager tm;


    public UsuarioControlador(IUsuarioRepo usuarioRepo, ITransactionManager tm) {
        this.usuarioRepo = usuarioRepo;
        this.tm = tm;
    }


    @Override
    public Optional<UsuarioDto> registrarUsuario(UsuarioForm usuarioForm) throws ValidacionException {

        var errores = usuarioForm.validar();
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var usuarioCreado = tm.inTransaction(() -> {
            var usuario = usuarioRepo.obtenerPorEmail(usuarioForm.getEmail());
            if (usuario.isPresent()) {
                errores.add(new ErrorDto("email", ErrorType.EMAIL_INVALIDO));
            }

            usuario = usuarioRepo.obtenerPorNombreUsuario(usuarioForm.getNombreUsuario());
            if (usuario.isPresent()) {
                errores.add(new ErrorDto("nombre", ErrorType.NOMBRE_EXISTENTE));
            }

            if (!listaPaises.contains(usuarioForm.getPais())) {
                errores.add(new ErrorDto("pais", ErrorType.PAIS_INEXISTENTE));
            }

            if (!errores.isEmpty()) {
                throw new ValidacionException(errores);
            }

            return usuarioRepo.crear(usuarioForm);
        });

        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuarioCreado.orElse(null)));
    }

    @Override
    public Optional<UsuarioDto> consultarUsuarioNombreUsuario(String nombreUsuario) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (nombreUsuario.isEmpty()) {
            errores.add(new ErrorDto("vacio", ErrorType.BUSQUEDA_INVALIDA));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var usuarioConsultado = tm.inTransaction(() -> {
            var usuario = usuarioRepo.obtenerPorNombreUsuario(nombreUsuario);
            return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuario.orElse(null)));
        });

        return usuarioConsultado;
    }

    @Override
    public Optional<UsuarioDto> consultarUsuarioId(Long id) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (id == null) {
            errores.add(new ErrorDto("vacio", ErrorType.BUSQUEDA_INVALIDA));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var usuarioConsultado = tm.inTransaction(() -> {
            var usuario = usuarioRepo.obtenerPorId(id);
            return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuario.orElse(null)));
        });

        return usuarioConsultado;
    }

    @Override
    public Optional<UsuarioDto> anhadirSaldo(long id, float cantidad) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (cantidad < CONST_CERO) {
            errores.add(new ErrorDto("cantidad", ErrorType.FORMATO_INVALIDO));
        }

        if (cantidad < SALDO_MINIMO || cantidad > SALDO_MAXIMO) {
            errores.add(new ErrorDto("cantidad", ErrorType.SALDO_INVALIDO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var usuarioNuevoSaldo = tm.inTransaction(() -> {
            var usuario = usuarioRepo.obtenerPorId(id);
            if (usuario.isEmpty()) {
                errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            }

            if (!errores.isEmpty()) {
                throw new ValidacionException(errores);
            }

            if (usuario.get().getEstado() != EstadoCuentaEmun.ACTIVA) {
                errores.add(new ErrorDto("cuenta", ErrorType.ESTADO_CUENTA));
            }

            if (!errores.isEmpty()) {
                throw new ValidacionException(errores);
            }

            return usuarioRepo.actualizar(id, new UsuarioForm(usuario.get().getNombreUsuario(),
                    usuario.get().getEmail(), usuario.get().getContrasenha(), usuario.get().getNombreReal(),
                    usuario.get().getPais(), usuario.get().getCumpleanhos(), usuario.get().getFechaRegistro(),
                    usuario.get().getAvatar(), (cantidad + usuario.get().getCreditoSteam()),
                    usuario.get().getEstado()));
        });

        return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuarioNuevoSaldo.orElse(null)));
    }

    @Override
    public Optional<UsuarioDto> consultarSaldo(long id) throws ValidacionException {
        var usuarioConsultado = tm.inTransaction(() -> {
            var usuario = usuarioRepo.obtenerPorId(id);
            if (usuario.isEmpty()) {
                throw new ValidacionException(List.of(new ErrorDto("id", ErrorType.NO_ENCONTRADO)));
            }
            return Optional.ofNullable(Mapper.mapUsuarioEntidadADto(usuario.orElse(null)));
        });

        return usuarioConsultado;
    }


//    public static void main(String[] args) {
//        IUsuarioRepo iUsuarioRepo = new UsuarioRepoInMemory();
//        UsuarioControlador controlador = new UsuarioControlador();
//
//
//        var usuario2 = controlador.registrarUsuario(new UsuarioForm("kaisquest", "email@email.com",
//        "1234ABcd!", "Iván",
//                "España", LocalDate.of(1998, 03, 05), LocalDate.of(2026, 04, 21),
//                "Avatar", 50.0f, EstadoCuentaEmun.ACTIVA));
//
//        try {
//            var usuario = controlador.registrarUsuario(new UsuarioForm("kaisquest", "email@email.com",
//            "1234abcd!", "Iván",
//                    "España", LocalDate.of(1998, 03, 05), LocalDate.of(2026, 04, 21),
//                    "Avatar", 50.0f, EstadoCuentaEmun.ACTIVA));
//        } catch (ValidacionException e) {
//            System.err.println("El usuario ya existe en el sistema");
//
//        }
//
//
//        var usuarioNombre = controlador.consultarUsuarioNombreUsuario("kaisquest");
//
//        System.out.println(usuarioNombre.get().getNombreUsuario());
//
//        var usuarioId = controlador.consultarUsuarioId(1l);
//
//        System.out.println(usuarioId.get().getNombreUsuario());
//
//        var usuarioSaldo = controlador.consultarSaldo(1L);
//        System.out.println(usuarioSaldo.get().getNombreUsuario() + " " + usuarioSaldo.get().getCreditoSteam());
//
//        //TODO - A la hora de añadir el saldo no se está actualizando, siempre sale el saldo antiguo
//        usuarioSaldo = controlador.anhadirSaldo(1L, 20.0f);
//
//        System.out.println(usuarioSaldo.get().getNombreUsuario() + " " + usuarioSaldo.get().getCreditoSteam());
//        System.out.println(usuarioNombre.get().getEstadoCuenta().toString());
//
//        try {
//            usuarioSaldo = controlador.anhadirSaldo(1L, 3f);
//        } catch (ValidacionException e) {
//            System.err.println("Saldo a introducir demasiado bajo");
//        }
//
//
//    }
}
