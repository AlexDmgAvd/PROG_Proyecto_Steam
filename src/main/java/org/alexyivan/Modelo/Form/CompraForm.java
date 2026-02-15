package org.alexyivan.Modelo.Form;

import org.alexyivan.Modelo.Enum.EstadoCompraENUM;
import org.alexyivan.Modelo.Enum.MetodoPagoENUM;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompraForm {
    public static final int MAX_DESC = 100;
    private Long usuarioId;
    private Long juegoId;
    private LocalDateTime fechaCompra;
    private MetodoPagoENUM metodoPago;
    private Float precioSinDescuento;
    private Integer descuento;
    private Double precioFinal;
    private EstadoCompraENUM estado;


    // Constructor

    public CompraForm(Long usuarioId, Long juegoId, LocalDateTime fechaCompra, MetodoPagoENUM metodoPago, Float precioSinDescuento, Integer descuento, Double precioFinal, EstadoCompraENUM estado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.descuento = descuento;
        this.precioFinal = precioFinal;
        this.estado = estado;
    }


    // Getters

    public Integer getDescuento() {
        return descuento;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getJuegoId() {
        return juegoId;
    }

    public MetodoPagoENUM getMetodoPago() {
        return metodoPago;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public Float getPrecioSinDescuento() {
        return precioSinDescuento;
    }

    public EstadoCompraENUM getEstado() {
        return estado;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }


    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();

        // Usuario ID
        if (usuarioId == null) {
            errores.add(new ErrorDto("usuarioId", ErrorType.REQUERIDO));
        }

        // Juego ID
        if (juegoId == null) {
            errores.add(new ErrorDto("juegoId", ErrorType.REQUERIDO));
        }

        // Fecha de compra
        if (fechaCompra != null) {
            errores.add(new ErrorDto("fechaCompra", ErrorType.REQUERIDO));
        }

        if (fechaCompra != null && fechaCompra.isAfter(LocalDateTime.now())) {
            errores.add(new ErrorDto("fechaCompra", ErrorType.FECHA_FUTURA));
        }

        // Método de pago
        if (metodoPago == null) {
            errores.add(new ErrorDto("metodoPago", ErrorType.REQUERIDO));
        }

        // Precio sin descuento
        if (precioSinDescuento == null) {
            errores.add(new ErrorDto("precioSinDescuento", ErrorType.REQUERIDO));
        }

        if (precioSinDescuento <= 0) {
            errores.add(new ErrorDto("precioSinDescuento", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        // Validar máximo 2 decimales
        String precioStr = String.valueOf(precioSinDescuento);
        if (precioStr.contains(".") && precioStr.split("\\.")[1].length() > 2) {
            errores.add(new ErrorDto("precioSinDescuento", ErrorType.FORMATO_INVALIDO));
        }

        // Descuento aplicado
        if (descuento != null) {
            if (descuento < 0 || descuento > MAX_DESC) {
                errores.add(new ErrorDto("descuentoAplicado", ErrorType.DESCUENTO_INVALIDO));
            }
        }
        return errores;
    }




//    // calcular el precio final
//    public Double calcularPrecioFinal() {
//        if (precioSinDescuento == null) {
//            return null;
//        }
//
//        double precio = precioSinDescuento;
//        if (precioFinal != null && precioFinal > 0) {
//            double precioFinal = precio * descuento / 100.0;
//            precio -= precioFinal;
//        }
//
//        // Redondear a 2 decimales
//        return Math.round(precio * 100.0) / 100.0;
//    }


}