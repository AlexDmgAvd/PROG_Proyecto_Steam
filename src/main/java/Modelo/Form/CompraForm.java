package Modelo.Form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompraForm {
    private Long usuarioId;
    private Long juegoId;
    private LocalDateTime fechaCompra;
    private String metodoPago;
    private Double precioSinDescuento;
    private Integer precioFinal;
    private String estado;


    // Constructor
    public CompraForm(Long usuarioId, Long juegoId, LocalDateTime fechaCompra, String metodoPago, Double precioSinDescuento, Integer descuentoAplicado, String estado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.precioFinal = descuentoAplicado;
        this.estado = estado;
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
        if (fechaCompra != null && fechaCompra.isAfter(LocalDateTime.now())) {
            errores.add(new ErrorDto("fechaCompra", ErrorType.FECHA_FUTURA));
        }

        // Método de pago
        if (metodoPago == null || metodoPago.isBlank()) {
            errores.add(new ErrorDto("metodoPago", ErrorType.REQUERIDO));
        }

        // Precio sin descuento
        if (precioSinDescuento == null) {
            errores.add(new ErrorDto("precioSinDescuento", ErrorType.REQUERIDO));
        } else {
            if (precioSinDescuento <= 0) {
                errores.add(new ErrorDto("precioSinDescuento", ErrorType.VALOR_DEMASIADO_BAJO));
            }
            // Validar máximo 2 decimales
            String precioStr = String.valueOf(precioSinDescuento);
            if (precioStr.contains(".") && precioStr.split("\\.")[1].length() > 2) {
                errores.add(new ErrorDto("precioSinDescuento", ErrorType.FORMATO_INVALIDO));
            }
        }

        // Descuento aplicado
        if (precioFinal != null) {
            if (precioFinal < 0 || precioFinal > 100) {
                errores.add(new ErrorDto("descuentoAplicado", ErrorType.DESCUENTO_INVALIDO));
            }
        }


        return errores;
    }

    // calcular el precio final
    public Double calcularPrecioFinal() {
        if (precioSinDescuento == null) {
            return null;
        }

        double precio = precioSinDescuento;
        if (precioFinal != null && precioFinal > 0) {
            double descuento = precio * precioFinal / 100.0;
            precio -= descuento;
        }

        // Redondear a 2 decimales
        return Math.round(precio * 100.0) / 100.0;
    }
}