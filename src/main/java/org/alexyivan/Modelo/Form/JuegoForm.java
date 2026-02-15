package org.alexyivan.Modelo.Form;

import org.alexyivan.Modelo.Enum.EstadoENUM;
import org.alexyivan.Modelo.Enum.PegiENUM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JuegoForm {

    private String titulo;
    private String descripcion;
    private String desarrolladora;
    private LocalDate fechaPublicacion;
    private float precioBase;
    private int descuentoActual;
    private String genero;
    private PegiENUM rangoEdad;
    private String idiomasDisponibles;
    private EstadoENUM estado;

    public JuegoForm(String titulo, String descripcion, String desarrolladora, LocalDate fechaPublicacion,
                     float precioBase, int descuentoActual, String genero, PegiENUM rangoEdad, String idiomasDisponibles,
                     EstadoENUM estado) {
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

    //Getters


    public String getGenero() {
        return genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public float getPrecioBase() {
        return precioBase;
    }

    public int getDescuentoActual() {
        return descuentoActual;
    }

    public PegiENUM getRangoEdad() {
        return rangoEdad;
    }

    public String getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public EstadoENUM getEstado() {
        return estado;
    }

    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();

        // Titulo
        if (titulo == null || titulo.isBlank()) {
            errores.add(new ErrorDto("titulo", ErrorType.REQUERIDO));
        }

        if (titulo.length() < 1 || titulo.length() > 100) {
            errores.add(new ErrorDto("titulo", ErrorType.LONGITUD_INVALIDA));
        }


        // Descripcion
        if (descripcion.length() > 2000) {
            errores.add(new ErrorDto("descripcion", ErrorType.LONGITUD_INVALIDA));
        }

        // Desarrolladora
        if (desarrolladora == null || desarrolladora.isBlank()) {
            errores.add(new ErrorDto("desarrolladora", ErrorType.REQUERIDO));
        }

        if (desarrolladora.length() < 2 || desarrolladora.length() > 100) {
            errores.add(new ErrorDto("desarrolladora", ErrorType.LONGITUD_INVALIDA));
        }

        // FechaLanzamineto


        return errores;
    }
}
