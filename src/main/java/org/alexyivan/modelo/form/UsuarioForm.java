package org.alexyivan.modelo.form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioForm {

    public static final int LONGITUD_MIN_NOMBRE = 3;
    public static final int LONGITUD_MAX_NOMBRE = 20;
    public static final int LONGITUD_MIN_REAL = 2;
    public static final int LONGITUD_MAX_REAL = 50;
    public static final int LONGITUD_MIN_CONTRASENA = 8;
    public static final int CONSTANTE_CERO = 0;
    public static final int CONSTANTE_CIEN = 100;

    String nombreUsuario;
    String email;
    String contrasena;
    String nombreReal;
    String pais;
    LocalDate fechaNacimiento;
    LocalDate fechaRegistro;
    String avatar;
    Float saldo;

    //Constructor
    public UsuarioForm(String nombreUsuario, String email, String contrasena, String nombreReal, String pais,
                       LocalDate fechaNacimiento, LocalDate fechaRegistro, String avatar, Float saldo) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.saldo = saldo;
    }

    //Getters
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getAvatar() {
        return avatar;
    }

    public Float getSaldo() {
        return saldo;
    }

    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();


        // nombre usuario
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.add(new ErrorDto("nombreUsuario", ErrorType.REQUERIDO));
        }

        if (nombreUsuario != null) {

            if (nombreUsuario.length() < LONGITUD_MIN_NOMBRE || nombreUsuario.length() > LONGITUD_MAX_NOMBRE) {
                errores.add(new ErrorDto("nombreUsuario", ErrorType.LONGITUD_INVALIDA));
            }
            // Solo alfanumericos, guiones y guiones bajos
            if (!nombreUsuario.matches("^[a-zA-Z0-9_-]+$")) {
                errores.add(new ErrorDto("nombreUsuario", ErrorType.FORMATO_INVALIDO));
            }
            // No puede empezar con número
            if (nombreUsuario.matches("^[0-9].*")) {
                errores.add(new ErrorDto("nombreUsuario", ErrorType.FORMATO_INVALIDO));
            }
        }


        // email
        if (email == null || email.isBlank()) {
            errores.add(new ErrorDto("email", ErrorType.REQUERIDO));
        }

        if (email != null) {

            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                errores.add(new ErrorDto("email", ErrorType.EMAIL_INVALIDO));
            }
        }


        // contrasenha
        if (contrasena == null || contrasena.isBlank()) {
            errores.add(new ErrorDto("contrasena", ErrorType.REQUERIDO));
        }

        if (contrasena != null) {
            if (contrasena.length() < LONGITUD_MIN_CONTRASENA) {
                errores.add(new ErrorDto("contrasena", ErrorType.VALOR_DEMASIADO_BAJO));
            }
        }

        if (contrasena != null) {

            if (!contrasena.matches(".*[A-Z].*")) {
                errores.add(new ErrorDto("contrasena", ErrorType.CONTRASENA_DEBIL));
            }
            if (!contrasena.matches(".*[a-z].*")) {
                errores.add(new ErrorDto("contrasena", ErrorType.CONTRASENA_DEBIL));
            }
            if (!contrasena.matches(".*\\d.*")) {
                errores.add(new ErrorDto("contrasena", ErrorType.CONTRASENA_DEBIL));
            }
        }


        // nombre real
        if (nombreReal == null || nombreReal.isBlank()) {
            errores.add(new ErrorDto("nombreReal", ErrorType.REQUERIDO));
        }

        if (nombreReal != null) {

            if (nombreReal.length() < LONGITUD_MIN_REAL ||
                    nombreReal.length() > LONGITUD_MAX_REAL) {
                errores.add(new ErrorDto("nombreReal", ErrorType.LONGITUD_INVALIDA));
            }
        }



        // pais
        if (pais == null || pais.isBlank()) {
            errores.add(new ErrorDto("pais", ErrorType.REQUERIDO));
        }

        // fecha nacimiento
        if (fechaNacimiento == null) {
            errores.add(new ErrorDto("fechaNacimiento", ErrorType.REQUERIDO));
        }

        if (fechaNacimiento != null){

            LocalDate hoy = LocalDate.now();
            if (fechaNacimiento.isAfter(hoy)) {
                errores.add(new ErrorDto("fechaNacimiento", ErrorType.FECHA_FUTURA));
            }
            LocalDate hace13Anios = hoy.minusYears(13);
            if (fechaNacimiento.isAfter(hace13Anios)) {
                errores.add(new ErrorDto("fechaNacimiento", ErrorType.EDAD_INSUFICIENTE));
            }
        }

        // avatar
        if (avatar != null && !avatar.isBlank() && avatar.length() > CONSTANTE_CIEN) {
            errores.add(new ErrorDto("avatar", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        // saldo
        if (saldo != null) {
            if (saldo < CONSTANTE_CERO) {
                errores.add(new ErrorDto("saldo", ErrorType.VALOR_NEGATIVO));
            }

            if (Math.round(saldo * CONSTANTE_CIEN) != saldo * CONSTANTE_CIEN) {
                errores.add(new ErrorDto("saldo", ErrorType.FORMATO_INVALIDO));
            }
        }

        return errores;
    }
}