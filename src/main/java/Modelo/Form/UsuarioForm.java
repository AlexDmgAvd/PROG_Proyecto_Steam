package Modelo.Form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioForm {
    String nombreUsuario;
    String email;
    String contrasena;
    String nombreReal;
    String pais;
    LocalDate fechaNacimiento;
    String avatar;
    Double saldo;

    public UsuarioForm(String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNacimiento, String avatar, Double saldo) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.avatar = avatar;
        this.saldo = saldo;
    }

    public List<ErrorDto> validar() {


        List<ErrorDto> errores = new ArrayList<>();

        // Nombre usuario
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.add(new ErrorDto("nombreUsuario", ErrorType.REQUERIDO));
        }

        if (!nombreUsuario.contains("^[a-zA-Z][a-zA-Z0-9_-]*$")) {
            errores.add(new ErrorDto("nombreUsuario", ErrorType.FORMATO_INVALIDO));
        }

        if (!nombreUsuario.startsWith("^[a-zA-Z].*")) {
            errores.add((new ErrorDto("nombreUsuario", ErrorType.FORMATO_INVALIDO)));
        }


        //Email
        if (email == null || email.isBlank()) {
            errores.add(new ErrorDto("email", ErrorType.REQUERIDO));
        }

        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errores.add(new ErrorDto("email", ErrorType.FORMATO_INVALIDO));
        }



        return errores;


    }
}