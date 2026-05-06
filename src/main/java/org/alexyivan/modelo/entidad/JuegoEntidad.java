package org.alexyivan.modelo.entidad;

import org.alexyivan.bdconfig.HibernateUtil;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.PegiEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "juegos")
@Entity
public class JuegoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "desarrolladora")
    private String desarrolladora;
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;
    @Column(name = "precio_base")
    private float precioBase;
    @Column(name = "descuento_actual")
    private int descuentoActual;
    @Column(name = "genero")
    private String genero;
    @Column(name = "rango_edad")
    private PegiEnum rangoEdad;
    @Column(name = "idiomas_disponibles")
    private String idiomasDisponibles;
    @Column(name = "estado")
    private EstadoJuegoEnum estado;

    // Constructor
    public JuegoEntidad(long id, String titulo, String descripcion, String desarrolladora,
                        LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                        String genero, PegiEnum rangoEdad, String idiomasDisponibles, EstadoJuegoEnum estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrolladora = desarrolladora;
        this.fechaPublicacion = fechaPublicacion;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.genero = genero;
        this.rangoEdad = rangoEdad;
        this.idiomasDisponibles = idiomasDisponibles;
        this.estado = estado;
    }

    // Constructor vacio
    public JuegoEntidad(){

    }

    // Constructor sin ID
    public JuegoEntidad(String titulo, String descripcion, String desarrolladora,
                        LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                        String genero, PegiEnum rangoEdad, String idiomasDisponibles, EstadoJuegoEnum estado) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrolladora = desarrolladora;
        this.fechaPublicacion = fechaPublicacion;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.genero = genero;
        this.rangoEdad = rangoEdad;
        this.idiomasDisponibles = idiomasDisponibles;
        this.estado = estado;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public float getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(float precioBase) {
        this.precioBase = precioBase;
    }

    public int getDescuentoActual() {
        return descuentoActual;
    }

    public void setDescuentoActual(int descuentoActual) {
        this.descuentoActual = descuentoActual;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public PegiEnum getRangoEdad() {
        return rangoEdad;
    }

    public void setRangoEdad(PegiEnum rangoEdad) {
        this.rangoEdad = rangoEdad;
    }

    public String getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public void setIdiomasDisponibles(String idiomasDisponibles) {
        this.idiomasDisponibles = idiomasDisponibles;
    }

    public EstadoJuegoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoJuegoEnum estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "JuegoEntidad [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", desarrolladora="
                + desarrolladora + ", fechaPublicacion=" + fechaPublicacion + ", precioBase=" + precioBase
                + ", descuentoActual=" + descuentoActual + ", genero=" + genero + ", rangoEdad=" + rangoEdad
                + ", idiomasDisponibles=" + idiomasDisponibles + ", estado=" + estado + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JuegoEntidad that = (JuegoEntidad) o;
        return id == that.id && Float.compare(precioBase, that.precioBase) == 0
                && descuentoActual == that.descuentoActual
                && Objects.equals(titulo, that.titulo)
                && Objects.equals(descripcion, that.descripcion)
                && Objects.equals(desarrolladora, that.desarrolladora)
                && Objects.equals(fechaPublicacion, that.fechaPublicacion)
                && Objects.equals(genero, that.genero)
                && rangoEdad == that.rangoEdad
                && Objects.equals(idiomasDisponibles, that.idiomasDisponibles) && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descripcion, desarrolladora, fechaPublicacion, precioBase, descuentoActual,
                genero, rangoEdad, idiomasDisponibles, estado);
    }
}
