package org.alexyivan.modelo.form;

import java.util.ArrayList;
import java.util.List;

public class BusquedaJuegosForm {

    private String titulo;
    private String genero;
    private Float precio;
    private String pegi;
    private String estado;


    public BusquedaJuegosForm(String titulo, String genero, Float precio, String pegi, String estado) {
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

    public String getEstado() {
        return estado;
    }

    /**
     * Valida los datos del formulario de Busqueda de juegos.
     *
     * @return Lista de errores encontrados durante la validación. Vacía si todos los campos son válidos.
     */
    public List<ErrorDto> validar() {

        List<ErrorDto> errores = new ArrayList<>();

        if (titulo.isEmpty() & genero.isEmpty() & precio == null & pegi.isEmpty() & estado.isEmpty()) {
            errores.add(new ErrorDto("busqueda", ErrorType.BUSQUEDA_INVALIDA));

        }

        String precioStr = String.valueOf(precio);
        if (precioStr.contains(".") && precioStr.split("\\.")[1].length() > 2) {
            errores.add(new ErrorDto("precio", ErrorType.FORMATO_INVALIDO));

        }

        return errores;
    }
}
