package Modelo.Dto;

import java.time.LocalDate;

public class UsuarioDTO {
    private long id;
    private String nombreUsuario;
    private String email;
    private String contrasnha;
    private String nombreReal;
    private String pais;
    private LocalDate cumpleanhos;
    private LocalDate fechaRegistro;
    private String avatar;
    private float creditoSteam;
    private enum estadoCuenta {ACTIVA, SUSPENDIDA, BANEADA}


}
