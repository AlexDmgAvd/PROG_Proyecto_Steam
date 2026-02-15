package org.alexyivan.Modelo.Form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioForm {
    public static final int Longuitud_min_nombre = 3;
    public static final int Longitud_max_nombre = 20;
    String nombreUsuario;
    String email;
    String contrasena;
    String nombreReal;
    String pais;
    LocalDate fechaNacimiento;
    LocalDate fechaRegistro;
    String avatar;
    Float saldo;

    public UsuarioForm(String nombreUsuario, String email, String contrasena, String nombreReal, String pais,
                       LocalDate fechaNacimiento,LocalDate fechaRegistro, String avatar, Float saldo) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro=fechaRegistro;
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

        // Nombre usuario
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.add(new ErrorDto("nombreUsuario", ErrorType.REQUERIDO));
        }

        if (nombreUsuario.length() < Longuitud_min_nombre || nombreUsuario.length() > Longitud_max_nombre){
            errores.add(new ErrorDto("nombreUsuario", ErrorType.LONGITUD_INVALIDA));
        }

        if (!nombreUsuario.matches("^[a-zA-Z][a-zA-Z0-9_-]*$")) {
            errores.add(new ErrorDto("nombreUsuario", ErrorType.FORMATO_INVALIDO));
        }

        if (nombreUsuario.startsWith("^[^0-9].*")) {
            errores.add((new ErrorDto("nombreUsuario", ErrorType.FORMATO_INVALIDO)));
        }


        //Email
        if (email == null || email.isBlank()) {
            errores.add(new ErrorDto("email", ErrorType.REQUERIDO));
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$")) {
            errores.add(new ErrorDto("email", ErrorType.FORMATO_INVALIDO));
        }

        //Contrasenha







        return errores;


    }
}