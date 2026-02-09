package Modelo.Form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompraForm {
    private Long usuarioId;
    private Long juegoId;
    private LocalDateTime fechaCompra;
    private String metodoPago;
    private Double precioSinDescuento;
    private Integer descuentoAplicado;
    private String estado;

    // Métodos de pago válidos
    private static final List<String> METODOS_PAGO_VALIDOS = Arrays.asList(
            "TARJETA_CREDITO", "PAYPAL", "CARTERA_STEAM", "TRANSFERENCIA", "OTROS"
    );

    // Estados válidos
    private static final List<String> ESTADOS_VALIDOS = Arrays.asList(
            "PENDIENTE", "COMPLETADA", "CANCELADA", "REEMBOLSADA"
    );

    // Constructor
    public CompraForm(Long usuarioId, Long juegoId, LocalDateTime fechaCompra, String metodoPago, Double precioSinDescuento, Integer descuentoAplicado, String estado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = estado;
    }

    // Getters
    public Long getUsuarioId() { return usuarioId; }
    public Long getJuegoId() { return juegoId; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public String getMetodoPago() { return metodoPago; }
    public Double getPrecioSinDescuento() { return precioSinDescuento; }
    public Integer getDescuentoAplicado() { return descuentoAplicado; }
    public String getEstado() { return estado; }


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

        // Fecha de compra (opcional, se genera automáticamente)
        if (fechaCompra != null && fechaCompra.isAfter(LocalDateTime.now())) {
            errores.add(new ErrorDto("fechaCompra", ErrorType.FECHA_FUTURA));
        }

        // Método de pago
        if (metodoPago == null || metodoPago.isBlank()) {
            errores.add(new ErrorDto("metodoPago", ErrorType.REQUERIDO));
        } else if (!METODOS_PAGO_VALIDOS.contains(metodoPago)) {
            errores.add(new ErrorDto("metodoPago", ErrorType.METODO_PAGO_INVALIDO));
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

        // Descuento aplicado (opcional)
        if (descuentoAplicado != null) {
            if (descuentoAplicado < 0 || descuentoAplicado > 100) {
                errores.add(new ErrorDto("descuentoAplicado", ErrorType.DESCUENTO_INVALIDO));
            }
        }

        // Estado (opcional)
        if (estado != null && !ESTADOS_VALIDOS.contains(estado)) {
            errores.add(new ErrorDto("estado", ErrorType.ESTADO_INVALIDO));
        }

        return errores;
    }

    // calcular el precio final
    public Double calcularPrecioFinal() {
        if (precioSinDescuento == null) return null;

        double precio = precioSinDescuento;
        if (descuentoAplicado != null && descuentoAplicado > 0) {
            double descuento = precio * descuentoAplicado / 100.0;
            precio -= descuento;
        }

        // Redondear a 2 decimales
        return Math.round(precio * 100.0) / 100.0;
    }
}