package org.alexyivan.modelo.form;

import org.alexyivan.modelo.enums.EstadoCompraEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BusquedaCompraForm {

    private LocalDate fechaMinima;
    private LocalDate fechaMaxima;
    private EstadoCompraEnum estadoCompra;


    public BusquedaCompraForm(LocalDate fechaMinima, LocalDate fechaMaxima, EstadoCompraEnum estadoCompra) {
        this.fechaMinima = fechaMinima;
        this.fechaMaxima = fechaMaxima;
        this.estadoCompra = estadoCompra;
    }

    public LocalDate getFechaMinima() {
        return fechaMinima;
    }

    public LocalDate getFechaMaxima() {
        return fechaMaxima;
    }

    public EstadoCompraEnum getEstadoCompra() {
        return estadoCompra;
    }

    /**
     * Valida los datos del formulario de Busqueda de compras.
     *
     * @return Lista de errores encontrados durante la validación. Vacía si todos los campos son válidos.
     */

    public List<ErrorDto> validar() {

        List<ErrorDto> errores = new ArrayList<>();

        if (fechaMinima.isAfter(fechaMaxima)) {
            errores.add(new ErrorDto("busqueda", ErrorType.FECHA_ANTERIOR_MAYOR));

        }

        return errores;
    }


}
