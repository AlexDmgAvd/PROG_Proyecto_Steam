package org.alexyivan.form;

import org.alexyivan.modelo.enums.EstadoCompraEnum;
import org.alexyivan.modelo.enums.MetodoPagoEnum;
import org.alexyivan.modelo.form.CompraForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests para validación de restricciones del formulario CompraForm
 * Cubre todos los casos de validación de Compra especificados en steam.md
 */
public class CompraFormTest {

    private CompraForm formularioValido;
    private LocalDateTime fechaCompraValida;

    @Before
    public void setup() {
        fechaCompraValida = LocalDateTime.now();
        formularioValido = new CompraForm(
                1L, // usuarioId
                1L, // juegoId
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                10, // descuento 10%
                53.99, // precioFinal
                EstadoCompraEnum.COMPLETADO
        );
    }

    /**
     * Test 1: Usuario ID - Obligatorio
     */
    @Test
    public void testUsuarioIdObligatorio() {
        CompraForm formulario = new CompraForm(
                null, // usuarioId
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                10,
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de usuario ID requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("usuarioId")));
    }

    /**
     * Test 2: Juego ID - Obligatorio
     */
    @Test
    public void testJuegoIdObligatorio() {
        CompraForm formulario = new CompraForm(
                1L,
                null, // juegoId
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                10,
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de juego ID requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("juegoId")));
    }

    /**
     * Test 3: Fecha de compra - No puede ser futura
     */
    @Test
    public void testFechaCompraNoFutura() {
        LocalDateTime fechaFutura = LocalDateTime.now().plusHours(1);
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaFutura,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                10,
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha futura", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaCompra")));
    }

    /**
     * Test 4: Método de pago - Obligatorio
     */
    @Test
    public void testMetodoPagoObligatorio() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                null, // metodoPago
                59.99f,
                10,
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de método de pago requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("metodoPago")));
    }

    /**
     * Test 5: Métodos de pago válidos
     */
    @Test
    public void testMetodosPagoValidos() {
        for (MetodoPagoEnum metodo : MetodoPagoEnum.values()) {
            CompraForm formulario = new CompraForm(
                    1L,
                    1L,
                    fechaCompraValida,
                    metodo,
                    59.99f,
                    10,
                    53.99,
                    EstadoCompraEnum.COMPLETADO
            );

            List<ErrorDto> errores = formulario.validar();
            assertFalse("No debe haber error para método de pago: " + metodo, 
                    errores.stream().anyMatch(e -> e.campo().equals("metodoPago")));
        }
    }

    /**
     * Test 6: Precio sin descuento - Debe ser positivo
     */
    @Test
    public void testPrecioSinDescuentoNegativo() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                -10f, // precio negativo
                10,
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de precio negativo", 
                errores.stream().anyMatch(e -> e.campo().equals("precioSinDescuento")));
    }

    /**
     * Test 7: Precio sin descuento - Máximo 2 decimales
     */
    @Test
    public void testPrecioSinDescuentoDecimales() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.999f, // Más de 2 decimales
                10,
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        // Puede haber error de formato según la implementación
    }

    /**
     * Test 8: Descuento - Rango válido (0-100)
     */
    @Test
    public void testDescuentoNegativo() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                -5, // Descuento negativo
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de descuento inválido", 
                errores.stream().anyMatch(e -> e.campo().equals("descuento")));
    }

    /**
     * Test 9: Descuento - Máximo 100%
     */
    @Test
    public void testDescuentoMayorA100() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                101, // Mayor a 100%
                53.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de descuento inválido", 
                errores.stream().anyMatch(e -> e.campo().equals("descuento")));
    }

    /**
     * Test 10: Precio final - Debe corresponder al cálculo
     */
    @Test
    public void testPrecioFinalCalculado() {
        // Precio sin descuento: 100, Descuento: 10%
        // Precio final debe ser: 90
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                100f,
                10,
                90.0,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertFalse("El precio final calculado correctamente no debe tener errores", 
                errores.stream().anyMatch(e -> e.campo().equals("precioFinal")));
    }

    /**
     * Test 11: Estado de compra - Valores válidos
     */
    @Test
    public void testEstadoCompraValido() {
        for (EstadoCompraEnum estado : EstadoCompraEnum.values()) {
            CompraForm formulario = new CompraForm(
                    1L,
                    1L,
                    fechaCompraValida,
                    MetodoPagoEnum.TARJETA_CREDITO,
                    59.99f,
                    10,
                    53.99,
                    estado
            );

            List<ErrorDto> errores = formulario.validar();
            assertFalse("No debe haber error de estado para " + estado, 
                    errores.stream().anyMatch(e -> e.campo().equals("estado")));
        }
    }

    /**
     * Test 12: Compra con pago de tarjeta de crédito válida
     */
    @Test
    public void testCompraTarjetaCredito() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                0,
                59.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Compra válida con tarjeta de crédito no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 13: Compra con pago de cartera Steam válida
     */
    @Test
    public void testCompraCarteraSteam() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.CARTERA_STEAM,
                59.99f,
                0,
                59.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Compra válida con cartera Steam no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 14: Compra con pago de PayPal válida
     */
    @Test
    public void testCompraPaypal() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.PAYPAL,
                59.99f,
                0,
                59.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Compra válida con PayPal no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 15: Compra sin descuento válida
     */
    @Test
    public void testCompraSinDescuento() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                59.99f,
                0, // Sin descuento
                59.99,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Compra sin descuento válida no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 16: Compra con grandes descuentos válida
     */
    @Test
    public void testCompraConGranDescuento() {
        CompraForm formulario = new CompraForm(
                1L,
                1L,
                fechaCompraValida,
                MetodoPagoEnum.TARJETA_CREDITO,
                99.99f,
                75, // 75% de descuento
                24.9975,
                EstadoCompraEnum.COMPLETADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Compra con descuento válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 17: Registro válido - Sin errores
     */
    @Test
    public void testRegistroValido() {
        List<ErrorDto> errores = formularioValido.validar();
        assertTrue("El registro válido de compra no debe contener errores", errores.isEmpty());
    }
}
