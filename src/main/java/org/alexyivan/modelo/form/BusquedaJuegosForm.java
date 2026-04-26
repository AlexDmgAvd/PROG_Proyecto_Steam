package org.alexyivan.modelo.form;

import org.alexyivan.modelo.enums.EstadoJuegoEnum;

import java.util.ArrayList;
import java.util.List;

public class BusquedaJuegosForm {

    public static final int CONSTANTE_DOS = 2;
    private String titulo;
    private String genero;
    private Float precio;
    private String pegi;
    private EstadoJuegoEnum estado;


    public BusquedaJuegosForm(String titulo, String genero, Float precio, String pegi, EstadoJuegoEnum estado) {
        this.titulo = titulo;
        this.genero = genero;
        this.precio = precio;
        this.pegi = pegi;
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public Float getPrecio() {
        return precio;
    }

    public String getPegi() {
        return pegi;
    }

    public EstadoJuegoEnum getEstado() {
        return estado;
    }

    /**
     * Valida los datos del formulario de Busqueda de juegos.
     *
     * @return Lista de errores encontrados durante la validación. Vacía si todos los campos son válidos.
     */
    public List<ErrorDto> validar() {

        List<ErrorDto> errores = new ArrayList<>();

        if ((titulo.isEmpty()) & genero.isEmpty() & precio == null & pegi.isEmpty() & estado == null) {
            errores.add(new ErrorDto("busqueda", ErrorType.BUSQUEDA_INVALIDA));

        }

        String precioStr = String.valueOf(precio);
        if (precioStr.contains(".") && precioStr.split("\\.")[1].length() > CONSTANTE_DOS) {
            errores.add(new ErrorDto("precio", ErrorType.FORMATO_INVALIDO));

        }

        return errores;
    }
}
