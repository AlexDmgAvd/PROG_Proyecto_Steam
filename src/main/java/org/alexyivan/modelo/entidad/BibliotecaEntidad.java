package org.alexyivan.modelo.entidad;

import jakarta.persistence.*;
import org.alexyivan.modelo.enums.EstadoInstalacionEnum;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "bibliotecas")
@Entity
public class BibliotecaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_usuario")
    private long idUsuario;
    @Column(name = "id_juego")
    private long idJuego;
    @Column(name = "horas_jugadas_total")
    private float horasJugadasTotal;
    @Column(name = "ultima_fecha_de_juego")
    private LocalDate ultimaFechaDeJuego;
    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;
    @Column(name = "estado_instalacion")
    private EstadoInstalacionEnum estadoInstalacion;

    // Constructor
    public BibliotecaEntidad(long id, long idUsuario, long idJuego, float horasJugadasTotal,
                             LocalDate ultimaFechaDeJuego, LocalDate fechaAdquisicion,
                             EstadoInstalacionEnum estadoInstalacion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.horasJugadasTotal = horasJugadasTotal;
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estadoInstalacion = estadoInstalacion;
    }

    // Constructor vacio
    public BibliotecaEntidad(){
    }

    // Constructor sin ID
    public BibliotecaEntidad(long idUsuario, float horasJugadasTotal, long idJuego, LocalDate ultimaFechaDeJuego,
                             LocalDate fechaAdquisicion, EstadoInstalacionEnum estadoInstalacion) {
        this.idUsuario = idUsuario;
        this.horasJugadasTotal = horasJugadasTotal;
        this.idJuego = idJuego;
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estadoInstalacion = estadoInstalacion;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public float getHorasJugadasTotal() {
        return horasJugadasTotal;
    }

    public void setHorasJugadasTotal(float horasJugadasTotal) {
        this.horasJugadasTotal = horasJugadasTotal;
    }

    public LocalDate getUltimaFechaDeJuego() {
        return ultimaFechaDeJuego;
    }

    public void setUltimaFechaDeJuego(LocalDate ultimaFechaDeJuego) {
        this.ultimaFechaDeJuego = ultimaFechaDeJuego;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public EstadoInstalacionEnum getEstadoInstalacion() {
        return estadoInstalacion;
    }

    public void setEstadoInstalacion(EstadoInstalacionEnum estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }

    @Override
    public String toString() {
        return "BibliotecaEntidad{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", horasJugadasTotal=" + horasJugadasTotal +
                ", ultimaFechaDeJuego=" + ultimaFechaDeJuego +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", estadoInstalacion=" + estadoInstalacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BibliotecaEntidad that = (BibliotecaEntidad) o;
        return id == that.id && idUsuario == that.idUsuario
                && idJuego == that.idJuego
                && Float.compare(horasJugadasTotal, that.horasJugadasTotal) == 0
                && Objects.equals(ultimaFechaDeJuego, that.ultimaFechaDeJuego)
                && Objects.equals(fechaAdquisicion, that.fechaAdquisicion)
                && estadoInstalacion == that.estadoInstalacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idJuego, horasJugadasTotal, ultimaFechaDeJuego,
                fechaAdquisicion, estadoInstalacion);
    }
}
