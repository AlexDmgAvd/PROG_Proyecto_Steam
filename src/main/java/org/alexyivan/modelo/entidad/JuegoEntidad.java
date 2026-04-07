package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.PegiEnum;

import java.time.LocalDate;

@Table(name = "juegos")
@Entity
public class JuegoEntidad {

    @Id

    private long id;
    private String titulo;
    private String descripcion;
    private String desarrolladora;
    private LocalDate fechaPublicacion;
    private float precioBase;
    private int descuentoActual;
    private String genero;
    private PegiEnum rangoEdad;
    private String idiomasDisponibles;
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
}
