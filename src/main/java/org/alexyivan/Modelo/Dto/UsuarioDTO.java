package org.alexyivan.Modelo.Dto;

import org.alexyivan.Modelo.Enum.EstadoCuentaENUM;

import java.time.LocalDate;

public class UsuarioDTO {
    private long id;
    private String nombreUsuario;
    private String email;
    private String nombreReal;
    private String pais;
    private LocalDate cumpleanhos;
    private LocalDate fechaRegistro;
    private String avatar;
    private float creditoSteam;
    private EstadoCuentaENUM estadoCuenta;

    public UsuarioDTO(long id, String nombreUsuario, String email,
                      String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                      String avatar, float creditoSteam) {

        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.cumpleanhos = cumpleanhos;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.creditoSteam = creditoSteam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getCumpleanhos() {
        return cumpleanhos;
    }

    public void setCumpleanhos(LocalDate cumpleanhos) {
        this.cumpleanhos = cumpleanhos;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getCreditoSteam() {
        return creditoSteam;
    }

    public void setCreditoSteam(float creditoSteam) {
        this.creditoSteam = creditoSteam;
    }
}
