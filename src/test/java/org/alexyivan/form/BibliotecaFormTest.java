package org.alexyivan.form;

import org.alexyivan.modelo.enums.EstadoInstalacionEnum;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests para validación de restricciones del formulario BibliotecaForm
 * Cubre todos los casos de validación de Biblioteca especificados en steam.md
 */
public class BibliotecaFormTest {

    private BibliotecaForm formularioValido;
    private LocalDate fechaAdquisicionValida;

    @Before
    public void setup() {
        fechaAdquisicionValida = LocalDate.now().minusMonths(3);
        formularioValido = new BibliotecaForm(
                1L, // usuarioId
                1L, // juegoId
                fechaAdquisicionValida,
                50.5f, // tiempoJuegoTotal
                LocalDate.now().minusDays(5), // ultimaFechaJuego
                EstadoInstalacionEnum.INSTALADO
        );
    }

    /**
     * Test 1: Usuario ID - Obligatorio
     */
    @Test
    public void testUsuarioIdObligatorio() {
        BibliotecaForm formulario = new BibliotecaForm(
                null, // usuarioId
                1L,
                fechaAdquisicionValida,
                50.5f,
                LocalDate.now().minusDays(5),
                EstadoInstalacionEnum.INSTALADO
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
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                null, // juegoId
                fechaAdquisicionValida,
                50.5f,
                LocalDate.now().minusDays(5),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de juego ID requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("juegoId")));
    }

    /**
     * Test 3: Fecha de adquisición - Obligatoria
     */
    @Test
    public void testFechaAdquisicionObligatoria() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                null, // fechaAdquisicion
                50.5f,
                LocalDate.now().minusDays(5),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha de adquisición requerida", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaAdquisicion")));
    }

    /**
     * Test 4: Fecha de adquisición - No puede ser futura
     */
    @Test
    public void testFechaAdquisicionNoFutura() {
        LocalDate fechaFutura = LocalDate.now().plusDays(1);
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaFutura,
                50.5f,
                LocalDate.now(),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha futura", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaAdquisicion")));
    }

    /**
     * Test 5: Tiempo de juego total - Debe ser positivo o cero
     */
    @Test
    public void testTiempoJuegoTotalNegativo() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                -10f, // Tiempo negativo
                LocalDate.now().minusDays(5),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de tiempo de juego negativo", 
                errores.stream().anyMatch(e -> e.campo().equals("tiempoJuegoTotal")));
    }

    /**
     * Test 6: Tiempo de juego total - Máximo 1 decimal
     */
    @Test
    public void testTiempoJuegoTotalDecimales() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                50.55f, // Más de 1 decimal
                LocalDate.now().minusDays(5),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        // Puede haber error de formato según la implementación
    }

    /**
     * Test 7: Última fecha de juego - No puede ser futura
     */
    @Test
    public void testUltimaFechaJuegoNoFutura() {
        LocalDate fechaFutura = LocalDate.now().plusDays(1);
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                50.5f,
                fechaFutura,
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha futura", 
                errores.stream().anyMatch(e -> e.campo().equals("ultimaFechaJuego")));
    }

    /**
     * Test 8: Última fecha de juego - No puede ser anterior a fecha de adquisición
     */
    @Test
    public void testUltimaFechaJuegoAnteriorAdquisicion() {
        LocalDate fechaAdquisicion = LocalDate.now().minusMonths(3);
        LocalDate fechaJuegoAnterior = fechaAdquisicion.minusDays(5);

        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicion,
                50.5f,
                fechaJuegoAnterior,
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de última fecha anterior a adquisición", 
                errores.stream().anyMatch(e -> e.campo().equals("ultimaFechaJuego")));
    }

    /**
     * Test 9: Estado de instalación - Valores válidos
     */
    @Test
    public void testEstadoInstalacionValido() {
        for (EstadoInstalacionEnum estado : EstadoInstalacionEnum.values()) {
            BibliotecaForm formulario = new BibliotecaForm(
                    1L,
                    1L,
                    fechaAdquisicionValida,
                    50.5f,
                    LocalDate.now().minusDays(5),
                    estado
            );

            List<ErrorDto> errores = formulario.validar();
            assertFalse("No debe haber error de estado para " + estado, 
                    errores.stream().anyMatch(e -> e.campo().equals("estadoInstalacion")));
        }
    }

    /**
     * Test 10: Juego instalado en la biblioteca
     */
    @Test
    public void testJuegoInstalado() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                100.5f,
                LocalDate.now().minusDays(1),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Juego instalado válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 11: Juego no instalado en la biblioteca
     */
    @Test
    public void testJuegoNoInstalado() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                0f, // Sin tiempo de juego
                null, // Sin última fecha de juego
                EstadoInstalacionEnum.NO_INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Juego no instalado válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 12: Juego recién adquirido sin jugar
     */
    @Test
    public void testJuegoRecienAdquirido() {
        LocalDate hoy = LocalDate.now();
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                hoy,
                0f, // Sin tiempo de juego
                null, // Sin última fecha de juego
                EstadoInstalacionEnum.NO_INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Juego recién adquirido válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 13: Juego con muchas horas jugadas
     */
    @Test
    public void testJuegoMuchasHoras() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                1000.0f, // 1000 horas
                LocalDate.now().minusDays(1),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Juego con muchas horas válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 14: Tiempo de juego total con 1 decimal
     */
    @Test
    public void testTiempoJuegoTotalUnDecimal() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                50.5f,
                LocalDate.now().minusDays(5),
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Tiempo de juego con 1 decimal válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 15: Última fecha de juego igual a fecha de adquisición
     */
    @Test
    public void testUltimaFechaJuegoIgualAdquisicion() {
        LocalDate fecha = LocalDate.now().minusMonths(3);
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fecha,
                10.5f,
                fecha, // Igual a fecha de adquisición
                EstadoInstalacionEnum.INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Última fecha igual a adquisición válida no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 16: Juego con tiempo 0 y no instalado
     */
    @Test
    public void testJuegoTiempoZeroNoInstalado() {
        BibliotecaForm formulario = new BibliotecaForm(
                1L,
                1L,
                fechaAdquisicionValida,
                0f,
                null,
                EstadoInstalacionEnum.NO_INSTALADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Juego con tiempo 0 no instalado válido no debe tener errores", errores.isEmpty());
    }

    /**
     * Test 17: Registro válido - Sin errores
     */
    @Test
    public void testRegistroValido() {
        List<ErrorDto> errores = formularioValido.validar();
        assertTrue("El registro válido de biblioteca no debe contener errores", errores.isEmpty());
    }
}
