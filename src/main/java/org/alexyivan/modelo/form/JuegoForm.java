package org.alexyivan.modelo.form;

import org.alexyivan.modelo.enums.EstadoENUM;
import org.alexyivan.modelo.enums.PegiENUM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JuegoForm {

    public static final int LONGITUD_MAX_CARACTERES_TITULO = 100;
    public static final int LONGITUD_MIN_CARACTERES_TITULO = 1;
    public static final int LONGITUD_MAX_CARACTERES_DESCRIP = 2000;
    public static final int LONGITUD_MAX_CARACTERES_DESARROLL = 100;
    public static final int LONGITUD_MIN_CARACTERES_DESARROLL = 2;
    public static final int CONSTANTE_CERO = 0;
    public static final double RANGO_MAX_PRECIOBASE = 999.99;
    public static final int CONSTANTE_CIEN = 100;
    public static final int CONSTANTE_DOSCIENTOS = 200;
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

        if (titulo.length() < LONGITUD_MIN_CARACTERES_TITULO || titulo.length() > LONGITUD_MAX_CARACTERES_TITULO) {
            errores.add(new ErrorDto("titulo", ErrorType.LONGITUD_INVALIDA));
        }


        // Descripcion
        if (descripcion.length() > LONGITUD_MAX_CARACTERES_DESCRIP) {
            errores.add(new ErrorDto("descripcion", ErrorType.LONGITUD_INVALIDA));
        }


        // Desarrolladora
        if (desarrolladora == null || desarrolladora.isBlank()) {
            errores.add(new ErrorDto("desarrolladora", ErrorType.REQUERIDO));
        }

        if (desarrolladora.length() < LONGITUD_MIN_CARACTERES_DESARROLL || desarrolladora.length() > LONGITUD_MAX_CARACTERES_DESARROLL) {
            errores.add(new ErrorDto("desarrolladora", ErrorType.LONGITUD_INVALIDA));
        }


        // fecha publicacion
        if (fechaPublicacion == null) {
            errores.add(new ErrorDto("fechaPublicacion", ErrorType.REQUERIDO));
        }


        // precio base
        if (precioBase < CONSTANTE_CERO) {
            errores.add(new ErrorDto("precioBase", ErrorType.VALOR_NEGATIVO));
        }
        if (precioBase > RANGO_MAX_PRECIOBASE) {
            errores.add(new ErrorDto("precioBase", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        if (Math.round(precioBase * CONSTANTE_CIEN) != precioBase * CONSTANTE_CIEN) {
            errores.add(new ErrorDto("precioBase", ErrorType.FORMATO_INVALIDO));
        }



        // descuento actual
        if (descuentoActual < CONSTANTE_CERO || descuentoActual > CONSTANTE_CIEN) {
            errores.add(new ErrorDto("descuentoActual", ErrorType.DESCUENTO_INVALIDO));
        }



        // rango de Edad
        if (rangoEdad == null) {
            errores.add(new ErrorDto("rangoEdad", ErrorType.REQUERIDO));
        }



        // idiomas
        if (idiomasDisponibles != null) {
            if (idiomasDisponibles.length() > CONSTANTE_DOSCIENTOS) {
                errores.add(new ErrorDto("idiomasDisponibles", ErrorType.VALOR_DEMASIADO_ALTO));
            }
            if (idiomasDisponibles.isBlank()) {
                errores.add(new ErrorDto("idiomasDisponibles", ErrorType.FORMATO_INVALIDO));
            }
        }

        return errores;
    }
}
