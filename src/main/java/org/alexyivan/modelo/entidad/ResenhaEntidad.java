package org.alexyivan.modelo.entidad;

import jakarta.persistence.*;
import org.alexyivan.modelo.enums.EstadoResenhaEnum;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "Resenhas")
@Entity
public class ResenhaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_usuario")
    private long idUsuario;
    @Column(name = "id_juego")
    private long idJuego;
    @Column(name = "recomendado")
    private boolean recomendado;
    @Column(name = "texto_analisis")
    private String textoAnalisis;
    @Column(name = "horas_jugadas")
    private float horasJugadas;
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;
    @Column(name = "ultima_fecha_edicion")
    private LocalDate ultimaFechaEdicion;
    @Column(name = "estado")
    private EstadoResenhaEnum estado;

    // Constructor
    public ResenhaEntidad(long id, long idUsuario, long idJuego, boolean recomendado,
                          String textoAnalisis, float horasJugadas, LocalDate fechaPublicacion,
                          LocalDate ultimaFechaEdicion, EstadoResenhaEnum estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.textoAnalisis = textoAnalisis;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.ultimaFechaEdicion = ultimaFechaEdicion;
        this.estado = estado;
    }

    // Constructor
    public ResenhaEntidad() {
    }

    // Constructor sin ID
    public ResenhaEntidad(long idUsuario, long idJuego, boolean recomendado,
                          String textoAnalisis, float horasJugadas, LocalDate fechaPublicacion,
                          LocalDate ultimaFechaEdicion, EstadoResenhaEnum estado) {
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.textoAnalisis = textoAnalisis;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.ultimaFechaEdicion = ultimaFechaEdicion;
        this.estado = estado;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EstadoResenhaEnum getEstado() {
        return estado;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTextoAnalisis() {
        return textoAnalisis;
    }

    public void setTextoAnalisis(String textoAnalisis) {
        this.textoAnalisis = textoAnalisis;
    }

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(float horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getUltimaFechaEdicion() {
        return ultimaFechaEdicion;
    }

    public void setUltimaFechaEdicion(LocalDate ultimaFechaEdicion) {
        this.ultimaFechaEdicion = ultimaFechaEdicion;
    }

    @Override
    public String toString() {
        return "ResenhaEntidad{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", recomendado=" + recomendado +
                ", textoAnalisis='" + textoAnalisis + '\'' +
                ", horasJugadas=" + horasJugadas +
                ", fechaPublicacion=" + fechaPublicacion +
                ", ultimaFechaEdicion=" + ultimaFechaEdicion +
                ", estado=" + estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResenhaEntidad that = (ResenhaEntidad) o;
        return id == that.id && idUsuario == that.idUsuario && idJuego == that.idJuego && recomendado ==
                that.recomendado && Float.compare(horasJugadas, that.horasJugadas) == 0 &&
                Objects.equals(textoAnalisis, that.textoAnalisis)
                && Objects.equals(fechaPublicacion, that.fechaPublicacion) && Objects.equals(ultimaFechaEdicion,
                that.ultimaFechaEdicion)
                && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idJuego, recomendado, textoAnalisis, horasJugadas, fechaPublicacion,
                ultimaFechaEdicion, estado);
    }
}