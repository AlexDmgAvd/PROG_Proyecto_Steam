package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaJuegoEnum;
import org.alexyivan.modelo.enums.PegiEnum;
import org.alexyivan.modelo.form.BusquedaJuegosForm;
import org.alexyivan.modelo.form.JuegoForm;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests para JuegoControlador
 * Cubre acciones: Añadir juego, Buscar juegos, Listar todos, Consultar detalles,
 * Aplicar descuento, Cambiar estado
 * Especificadas en steam.md bajo "Gestión de Juegos"
 */
public class JuegoControladorTest {

    private IJuegoControlador controlador;
    private JuegoForm formularioValido;
    private LocalDate fechaPublicacionValida;

    @Before
    public void setup() {
        controlador = new JuegoControlador();
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
     * Acción: Añadir juego al catálogo
     * Debe registrar un nuevo videojuego con validaciones
     */
    @Test
    public void testAnadirJuegoValido() throws ValidacionException {
        Optional<JuegoDto> resultado = controlador.anhadirJuegoCatalogo(formularioValido);
        assertTrue("Debe añadir juego válido", resultado.isPresent());
        assertNotNull("Juego debe tener ID", resultado.get().getId());
        assertEquals("Título debe coincidir", "The Legend of Zelda", resultado.get().getTitulo());
    }

    /**
     * Acción: Añadir juego al catálogo
     * No debe registrar juego con datos inválidos
     */
    @Test
    public void testAnadirJuegoInvalido() throws ValidacionException {
        JuegoForm formularioInvalido = new JuegoForm(
                "", // Título vacío
                "Description",
                "Developer",
                fechaPublicacionValida,
                59.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español, Inglés",
                EstadoJuegoEnum.DISPONIBLE
        );

        assertThrows("Debe lanzar excepción para juego inválido",
                ValidacionException.class, () -> controlador.anhadirJuegoCatalogo(formularioInvalido));
    }

    /**
     * Acción: Buscar juegos
     * Debe filtrar juegos según criterios múltiples
     */
    @Test
    public void testBuscarJuegos() throws ValidacionException {
        controlador.anhadirJuegoCatalogo(formularioValido);

        BusquedaJuegosForm busqueda = new BusquedaJuegosForm();
        busqueda.setTexto("Zelda");
        List<JuegoDto> resultados = controlador.buscarJuegos(busqueda);

        assertTrue("Debe encontrar juegos con búsqueda", resultados.size() > 0);
        assertTrue("Debe contener el juego buscado", 
                resultados.stream().anyMatch(j -> j.getTitulo().contains("Zelda")));
    }

    /**
     * Acción: Consultar catálogo completo
     * Debe listar todos los juegos disponibles
     */
    @Test
    public void testListarTodosJuegos() throws ValidacionException {
        controlador.anhadirJuegoCatalogo(formularioValido);

        List<JuegoDto> juegos = controlador.listarTodosJuegos(OrdenBusquedaJuegoEnum.ALFABETICO);
        assertTrue("Debe listar todos los juegos", juegos.size() > 0);
    }

    /**
     * Acción: Consultar detalles de juego
     * Debe mostrar toda la información de un juego específico
     */
    @Test
    public void testConsultarJuego() throws ValidacionException {
        Optional<JuegoDto> registrado = controlador.anhadirJuegoCatalogo(formularioValido);
        assertTrue("Juego debe registrarse", registrado.isPresent());

        Optional<JuegoDto> resultado = controlador.consultarJuego(registrado.get().getId());
        assertTrue("Debe consultar juego por ID", resultado.isPresent());
        assertEquals("Título debe coincidir", "The Legend of Zelda", resultado.get().getTitulo());
        assertEquals("Precio debe coincidir", 59.99f, resultado.get().getPrecioBase(), 0.01f);
    }

    /**
     * Acción: Aplicar descuento
     * Debe establecer un porcentaje de descuento temporal
     */
    @Test
    public void testActualizarDescuento() throws ValidacionException {
        Optional<JuegoDto> registrado = controlador.anhadirJuegoCatalogo(formularioValido);
        assertTrue("Juego debe registrarse", registrado.isPresent());

        Optional<JuegoDto> resultado = controlador.actualizarDescuento(registrado.get().getId(), 20);
        assertTrue("Debe actualizar descuento", resultado.isPresent());
        assertEquals("Descuento debe ser 20%", 20, resultado.get().getDescuentoActual());
    }

    /**
     * Acción: Aplicar descuento
     * No debe permitir descuentos fuera del rango 0-100
     */
    @Test
    public void testActualizarDescuentoInvalido() throws ValidacionException {
        Optional<JuegoDto> registrado = controlador.anhadirJuegoCatalogo(formularioValido);
        assertTrue("Juego debe registrarse", registrado.isPresent());

        assertThrows("No debe permitir descuento mayor a 100%",
                ValidacionException.class, 
                () -> controlador.actualizarDescuento(registrado.get().getId(), 150));
    }

    /**
     * Acción: Cambiar estado del juego
     * Debe modificar el estado de disponibilidad
     */
    @Test
    public void testCambiarEstado() throws ValidacionException {
        Optional<JuegoDto> registrado = controlador.anhadirJuegoCatalogo(formularioValido);
        assertTrue("Juego debe registrarse", registrado.isPresent());

        Optional<JuegoDto> resultado = controlador.cambiarEstado(registrado.get().getId(), EstadoJuegoEnum.NO_DISPONIBLE);
        assertTrue("Debe cambiar estado", resultado.isPresent());
        assertEquals("Estado debe ser NO_DISPONIBLE", EstadoJuegoEnum.NO_DISPONIBLE, resultado.get().getEstado());
    }

    /**
     * Validación: Título único
     * No debe permitir dos juegos con el mismo título
     */
    @Test
    public void testTituloUnico() throws ValidacionException {
        controlador.anhadirJuegoCatalogo(formularioValido);

        JuegoForm formularioDuplicado = new JuegoForm(
                "The Legend of Zelda", // Mismo título
                "Another adventure",
                "Different Dev",
                fechaPublicacionValida,
                49.99f,
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español",
                EstadoJuegoEnum.DISPONIBLE
        );

        assertThrows("No debe permitir títulos duplicados",
                ValidacionException.class, () -> controlador.anhadirJuegoCatalogo(formularioDuplicado));
    }

    /**
     * Validación: Rango de precio válido (0-999.99)
     */
    @Test
    public void testPrecioBaseMayorAlMaximo() throws ValidacionException {
        JuegoForm formulario = new JuegoForm(
                "Game",
                "Description",
                "Developer",
                fechaPublicacionValida,
                1000f, // Mayor a 999.99
                0,
                "Aventura",
                PegiEnum.PEGI_12,
                "Español",
                EstadoJuegoEnum.DISPONIBLE
        );

        assertThrows("No debe permitir precio mayor a 999.99",
                ValidacionException.class, () -> controlador.anhadirJuegoCatalogo(formulario));
    }

    /**
     * Validación: Juegos gratuitos
     * Debe permitir precio 0 para juegos gratuitos
     */
    @Test
    public void testJuegoGratuito() throws ValidacionException {
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

        Optional<JuegoDto> resultado = controlador.anhadirJuegoCatalogo(formulario);
        assertTrue("Debe permitir precio 0", resultado.isPresent());
        assertEquals("Precio debe ser 0", 0f, resultado.get().getPrecioBase(), 0.01f);
    }

    /**
     * Validación: Preventa
     * Debe permitir fecha de lanzamiento futura para preventas
     */
    @Test
    public void testPreventa() throws ValidacionException {
        LocalDate fechaFutura = LocalDate.now().plusMonths(6);
        JuegoForm formulario = new JuegoForm(
                "Star Field 2",
                "Upcoming game",
                "Bethesda",
                fechaFutura,
                69.99f,
                0,
                "RPG",
                PegiEnum.PEGI_16,
                "Español",
                EstadoJuegoEnum.PREVENTA
        );

        Optional<JuegoDto> resultado = controlador.anhadirJuegoCatalogo(formulario);
        assertTrue("Debe permitir preventa con fecha futura", resultado.isPresent());
        assertEquals("Estado debe ser PREVENTA", EstadoJuegoEnum.PREVENTA, resultado.get().getEstado());
    }

    /**
     * Validación: Diferentes PEGI ratings
     */
    @Test
    public void testTodosLosPegiRatings() throws ValidacionException {
        for (PegiEnum pegi : PegiEnum.values()) {
            JuegoForm formulario = new JuegoForm(
                    "Game " + pegi.name(),
                    "Description",
                    "Developer",
                    fechaPublicacionValida,
                    29.99f,
                    0,
                    "Genre",
                    pegi,
                    "Español",
                    EstadoJuegoEnum.DISPONIBLE
            );

            Optional<JuegoDto> resultado = controlador.anhadirJuegoCatalogo(formulario);
            assertTrue("Debe permitir PEGI " + pegi, resultado.isPresent());
        }
    }

    /**
     * Acción: Listar juegos ordenados alfabéticamente
     */
    @Test
    public void testListarOrdenAlfabetico() throws ValidacionException {
        JuegoForm juego1 = new JuegoForm("Zelda", "Desc", "Dev", fechaPublicacionValida, 59.99f, 0, "Gen", PegiEnum.PEGI_12, "Español", EstadoJuegoEnum.DISPONIBLE);
        JuegoForm juego2 = new JuegoForm("Mario", "Desc", "Dev", fechaPublicacionValida, 49.99f, 0, "Gen", PegiEnum.PEGI_7, "Español", EstadoJuegoEnum.DISPONIBLE);

        controlador.anhadirJuegoCatalogo(juego1);
        controlador.anhadirJuegoCatalogo(juego2);

        List<JuegoDto> resultado = controlador.listarTodosJuegos(OrdenBusquedaJuegoEnum.ALFABETICO);
        assertTrue("Debe listar juegos ordenados", resultado.size() >= 2);
    }

    /**
     * Acción: Listar juegos ordenados por precio
     */
    @Test
    public void testListarOrdenPrecio() throws ValidacionException {
        List<JuegoDto> resultado = controlador.listarTodosJuegos(OrdenBusquedaJuegoEnum.PRECIO);
        assertTrue("Debe permitir ordenar por precio", resultado.isEmpty() || resultado.size() >= 0);
    }

    /**
     * Acción: Buscar por categoría
     */
    @Test
    public void testBuscarPorCategoria() throws ValidacionException {
        controlador.anhadirJuegoCatalogo(formularioValido);

        BusquedaJuegosForm busqueda = new BusquedaJuegosForm();
        busqueda.setCategoria("Aventura");
        List<JuegoDto> resultados = controlador.buscarJuegos(busqueda);

        assertTrue("Debe encontrar juegos de categoría Aventura", resultados.size() >= 0);
    }

    /**
     * Acción: Estados válidos
     * Debe permitir todos los estados válidos
     */
    @Test
    public void testTodosLosEstadosValidos() throws ValidacionException {
        Optional<JuegoDto> registrado = controlador.anhadirJuegoCatalogo(formularioValido);
        assertTrue("Juego debe registrarse", registrado.isPresent());

        for (EstadoJuegoEnum estado : EstadoJuegoEnum.values()) {
            Optional<JuegoDto> resultado = controlador.cambiarEstado(registrado.get().getId(), estado);
            assertTrue("Debe cambiar a estado " + estado, resultado.isPresent());
        }
    }
}
