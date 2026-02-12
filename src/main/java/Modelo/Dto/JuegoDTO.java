package Modelo.Dto;

import Modelo.Enum.EstadoENUM;
import Modelo.Enum.PegiENUM;

import java.time.LocalDate;
import java.util.List;

public class JuegoDTO {
    private long id;
    private String titulo;
    private String descripcion;
    private String desarrolladora;
    private LocalDate fechaPublicacion;
    private float precioBase;
    private int descuentoActual;
    private String genero;
    private PegiENUM rangoEdad;
    private EstadoENUM estado;

    public JuegoDTO(long id, String titulo, String descripcion, String desarrolladora,
                    LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                    String genero, PegiENUM rangoEdad, EstadoENUM estado) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrolladora = desarrolladora;
        this.fechaPublicacion = fechaPublicacion;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.genero = genero;
        this.rangoEdad = rangoEdad;
        this.estado = estado;
    }

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

    public EstadoENUM getEstado() {
        return estado;
    }

    public void setEstado(EstadoENUM estado) {
        this.estado = estado;
    }

    public PegiENUM getRangoEdad() {
        return rangoEdad;
    }

    public void setRangoEdad(PegiENUM rangoEdad) {
        this.rangoEdad = rangoEdad;
    }
}
