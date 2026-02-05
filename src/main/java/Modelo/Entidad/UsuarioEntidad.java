package Modelo.Entidad;

import java.time.LocalDate;

public record UsuarioEntidad (long id, String nombreUsuario, String email, String contrasenha,
                              String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                              String avatar, float creditoSteam) {
}
