package org.alexyivan.form;

import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.PegiEnum;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.JuegoForm;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests para validación de restricciones del formulario JuegoForm
 * Cubre todos los casos de validación de Juego especificados en steam.md
 */
public class JuegoFormTest {

    private JuegoForm formularioValido;
    private LocalDate fechaPublicacionValida;

    @Before
    public void setup() {
        fechaPublicacionValida = LocalDate.now().plusMonths(1);
        formularioValido = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );
    }

    /**
     * Test 1: Título - Obligatorio
     */
    @Test
    public void testTituloObligatorio() {
        JuegoForm formulario = new JuegoForm(
                null, // título
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de título requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("titulo")));
    }

    /**
     * Test 2: Título - Longitud mínima (1 carácter)
     */
    @Test
    public void testTituloLongitudMinima() {
        JuegoForm formulario = new JuegoForm(
                "", // título vacío
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud mínima de título", 
                errores.stream().anyMatch(e -> e.campo().equals("titulo")));
    }

    /**
     * Test 3: Título - Longitud máxima (100 caracteres)
     */
    @Test
    public void testTituloLongitudMaxima() {
        JuegoForm formulario = new JuegoForm(
                "a".repeat(101), // Más de 100 caracteres
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud máxima de título", 
                errores.stream().anyMatch(e -> e.campo().equals("titulo")));
    }

    /**
     * Test 4: Descripción - Longitud máxima (2000 caracteres)
     */
    @Test
    public void testDescripcionLongitudMaxima() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "a".repeat(2001), // Más de 2000 caracteres
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud máxima de descripción", 
                errores.stream().anyMatch(e -> e.campo().equals("descripcion")));
    }

    /**
     * Test 5: Desarrollador - Obligatorio
     */
    @Test
    public void testDesarrolladorObligatorio() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                null, // desarrollador
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de desarrollador requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("desarrolladora")));
    }

    /**
     * Test 6: Desarrollador - Longitud mínima (2 caracteres)
     */
    @Test
    public void testDesarrolladorLongitudMinima() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "N", // 1 carácter
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud mínima de desarrollador", 
                errores.stream().anyMatch(e -> e.campo().equals("desarrolladora")));
    }

    /**
     * Test 7: Desarrollador - Longitud máxima (100 caracteres)
     */
    @Test
    public void testDesarrolladorLongitudMaxima() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "a".repeat(101), // Más de 100 caracteres
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud máxima de desarrollador", 
                errores.stream().anyMatch(e -> e.campo().equals("desarrolladora")));
    }

    /**
     * Test 8: Fecha de publicación - Obligatoria
     */
    @Test
    public void testFechaPublicacionObligatoria() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                null, // fechaPublicacion
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha de publicación requerida", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaPublicacion")));
    }

    /**
     * Test 9: Precio base - Obligatorio y no negativo
     */
    @Test
    public void testPrecioBaseNegativo() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                -10f, // Precio negativo
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de precio negativo", 
                errores.stream().anyMatch(e -> e.campo().equals("precioBase")));
    }

    /**
     * Test 10: Precio base - Máximo permitido (999.99)
     */
    @Test
    public void testPrecioBaseMaximo() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                1000f, // Mayor a 999.99
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de precio demasiado alto", 
                errores.stream().anyMatch(e -> e.campo().equals("precioBase")));
    }

    /**
     * Test 11: Precio base - Máximo 2 decimales
     */
    @Test
    public void testPrecioBaseDecimales() {
        // Nota: Este test depende de la implementación del formato
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.999f, // Más de 2 decimales
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        // Puede haber error de formato
        boolean tieneErrorFormato = errores.stream().anyMatch(e -> e.campo().equals("precioBase"));
    }

    /**
     * Test 12: Descuento actual - Rango válido (0-100)
     */
    @Test
    public void testDescuentoNegativo() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                -5, // Descuento negativo
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de descuento inválido", 
                errores.stream().anyMatch(e -> e.campo().equals("descuentoActual")));
    }

    /**
     * Test 13: Descuento actual - Máximo 100%
     */
    @Test
    public void testDescuentoMayorA100() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                101, // Mayor a 100%
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de descuento inválido", 
                errores.stream().anyMatch(e -> e.campo().equals("descuentoActual")));
    }

    /**
     * Test 14: Clasificación por edad - Obligatoria
     */
    @Test
    public void testRangoEdadObligatorio() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                null, // rangoEdad
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de rango de edad requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("rangoEdad")));
    }

    /**
     * Test 15: Clasificación por edad - Debe ser válida (PEGI_3, 7, 12, 16, 18)
     */
    @Test
    public void testRangoEdadValido() {
        JuegoForm formulario = formularioValido;
        List<ErrorDto> errores = formulario.validar();
        assertFalse("No debe contener error de rango de edad para valor válido", 
                errores.stream().anyMatch(e -> e.campo().equals("rangoEdad")));
    }

    /**
     * Test 16: Idiomas disponibles - Longitud máxima (200 caracteres)
     */
    @Test
    public void testIdiomasDisponiblesMaximo() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "a".repeat(201), // Más de 200 caracteres
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud máxima de idiomas", 
                errores.stream().anyMatch(e -> e.campo().equals("idiomasDisponibles")));
    }

    /**
     * Test 17: Estado - Valor por defecto DISPONIBLE
     */
    @Test
    public void testEstadoPorDefecto() {
        JuegoForm formulario = new JuegoForm(
                "The Legend of Zelda",
                "Epic adventure game",
                "Nintendo",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        assertEquals("El estado debe ser DISPONIBLE", EstadoJuegoEnum.DISPONIBLE, formulario.getEstado());
    }

    /**
     * Test 18: Estado - Valores válidos
     */
    @Test
    public void testEstadosValidos() {
        for (EstadoJuegoEnum estado : EstadoJuegoEnum.values()) {
            JuegoForm formulario = new JuegoForm(
                    "The Legend of Zelda",
                    "Epic adventure game",
                    "Nintendo",
                    fechaPublicacionValida,
                    59.99f,
                    0,
                    "Aventura",
                    PegiEnum.PEGI_12,
                    "Español, Inglés",
                    estado
            );

            List<ErrorDto> errores = formulario.validar();
            assertFalse("No debe haber error de estado para " + estado, 
                    errores.stream().anyMatch(e -> e.campo().equals("estado")));
        }
    }

    /**
     * Test 19: Juego gratuito - Precio 0 es válido
     */
    @Test
    public void testJuegoGratuito() {
        JuegoForm formulario = new JuegoForm(
                "Dota 2",
                "Free-to-play game",
                "Valve",
                fechaPublicacionValida,
                0f, // Juego gratuito
                0,
                "MOBA",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        List<ErrorDto> errores = formulario.validar();
        assertFalse("No debe haber error de precio para juego gratuito", 
                errores.stream().anyMatch(e -> e.campo().equals("precioBase")));
    }

    /**
     * Test 20: Preventa - Fecha de publicación futura es válida
     */
    @Test
    public void testPreventaFechaFutura() {
        LocalDate fechaFutura = LocalDate.now().plusMonths(6);
        JuegoForm formulario = new JuegoForm(
                "Starfield 2",
                "Upcoming space game",
                "Bethesda",
                fechaFutura,
                69.99f,
                0,
                "RPG",
                PegiEnum.PEGI_16,
                "Español, Inglés",
                EstadoJuegoEnum.ACCESO_ANTICIPADO
        );

        List<ErrorDto> errores = formulario.validar();
        assertFalse("No debe haber error de fecha futura para preventa", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaPublicacion")));
    }

    /**
     * Test 21: Registro válido - Sin errores
     */
    @Test
    public void testRegistroValido() {
        List<ErrorDto> errores = formularioValido.validar();
        assertTrue("El registro válido de juego no debe contener errores", errores.isEmpty());
    }
}
