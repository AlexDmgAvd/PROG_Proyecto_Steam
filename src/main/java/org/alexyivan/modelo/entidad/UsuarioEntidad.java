package org.alexyivan.modelo.entidad;

import jakarta.persistence.*;
import org.alexyivan.modelo.enums.EstadoCuentaEmun;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "usuarios")
@Entity
public class UsuarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column(name = "email")
    private String email;
    @Column(name = "contrasenha")
    private String contrasenha;
    @Column(name = "nombre_real")
    private String nombreReal;
    @Column(name = "pais")
    private String pais;
    @Column(name = "cumpleanhos")
    private LocalDate cumpleanhos;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "credito_steam")
    private float creditoSteam;
    @Column(name = "estado")
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
        this(id, nombreUsuario, email, "", nombreReal, pais, cumpleanhos, fechaRegistro,
                avatar, creditoSteam, estado);
    }

    // Constructor vacio
    public UsuarioEntidad(){
    }

    // Constructor sin ID
    public UsuarioEntidad(String nombreUsuario, String email, String contrasenha,
                          String nombreReal, String pais, LocalDate cumpleanhos, LocalDate fechaRegistro,
                          String avatar, EstadoCuentaEmun estado, float creditoSteam) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenha = contrasenha;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.cumpleanhos = cumpleanhos;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.estado = estado;
        this.creditoSteam = creditoSteam;
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

    @Override
    public String toString() {
        return "UsuarioEntidad{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", contrasenha='" + contrasenha + '\'' +
                ", nombreReal='" + nombreReal + '\'' +
                ", pais='" + pais + '\'' +
                ", cumpleanhos=" + cumpleanhos +
                ", fechaRegistro=" + fechaRegistro +
                ", avatar='" + avatar + '\'' +
                ", creditoSteam=" + creditoSteam +
                ", estado=" + estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntidad that = (UsuarioEntidad) o;
        return id == that.id && Float.compare(creditoSteam, that.creditoSteam) == 0 &&
                Objects.equals(nombreUsuario, that.nombreUsuario)
                && Objects.equals(email, that.email) && Objects.equals(contrasenha, that.contrasenha)
                && Objects.equals(nombreReal, that.nombreReal)
                && Objects.equals(pais, that.pais) && Objects.equals(cumpleanhos, that.cumpleanhos)
                && Objects.equals(fechaRegistro, that.fechaRegistro)
                && Objects.equals(avatar, that.avatar) && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreUsuario, email, contrasenha, nombreReal, pais, cumpleanhos,
                fechaRegistro, avatar, creditoSteam, estado);
    }
}
