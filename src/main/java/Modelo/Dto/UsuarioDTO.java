package Modelo.Dto;

import Modelo.Enum.EstadoCuentaENUM;

import java.time.LocalDate;

public class UsuarioDTO {
    private long id;
    private String nombreUsuario;
    private String email;
    private String contrasenha;
    private String nombreReal;
    private String pais;
    private LocalDate cumpleanhos;
    private LocalDate fechaRegistro;
    private String avatar;
    private float creditoSteam;
    private EstadoCuentaENUM estadoCuenta;

    public UsuarioDTO(long id, String nombreUsuario, String email, String contrasenha,
                      String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                      String avatar, float creditoSteam) {

        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenha = contrasenha;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.cumpleanhos = cumpleanhos;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.creditoSteam = creditoSteam;
    }
}
