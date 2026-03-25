package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoCuentaEmun;

import java.time.LocalDate;

public class UsuarioEntidad {
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
    private EstadoCuentaEmun estado;

    // Constructor completo
    public UsuarioEntidad(long id, String nombreUsuario, String email, String contrasenha,
                          String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                          String avatar, float creditoSteam, EstadoCuentaEmun estado) {
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
        this.estado = estado;
    }

    // Constructor sin contraseña
    public UsuarioEntidad(long id, String nombreUsuario, String email,
                          String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                          String avatar, float creditoSteam, EstadoCuentaEmun estado) {
        this(id, nombreUsuario, email, "", nombreReal, pais, cumpleanhos, fechaRegistro, avatar, creditoSteam, estado);
    }

    // Getters y Setters
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

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
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

    public EstadoCuentaEmun getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaEmun estado) {
        this.estado = estado;
    }
}
