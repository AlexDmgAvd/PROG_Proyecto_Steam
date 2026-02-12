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

        if (nombreUsuario.length() < 3 || nombreUsuario.length() > 20){
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