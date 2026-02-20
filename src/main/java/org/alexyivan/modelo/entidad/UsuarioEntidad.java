package org.alexyivan.modelo.entidad;

import java.time.LocalDate;

public record UsuarioEntidad (long id, String nombreUsuario, String email, String contrasenha,
                              String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                              String avatar, float creditoSteam) {

    public UsuarioEntidad(long id, String nombreUsuario, String email,
                          String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                          String avatar, float creditoSteam){
        this(id, nombreUsuario, email, "", nombreReal, pais, cumpleanhos, fechaRegistro, avatar, creditoSteam);
    }
}
