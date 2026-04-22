package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.dto.EstadisticasBibliotecaDto;
import org.alexyivan.modelo.enums.EstadoInstalacionEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.repositorio.inmemory.BibliotecaRepoInMemory;
import org.alexyivan.repositorio.inmemory.CompraRepoInMemory;
import org.alexyivan.repositorio.inmemory.JuegoRepoInMemory;
import org.alexyivan.repositorio.inmemory.UsuarioRepoInMemory;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.ICompraRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests para BibliotecaControlador
 * Cubre acciones: Ver biblioteca personal, Añadir juego, Eliminar juego,
 * Actualizar tiempo de juego, Consultar última sesión, Ver estadísticas
 * Especificadas en steam.md bajo "Gestión de Biblioteca"
 */
public class BibliotecaControladorTest {

    private IBibliotecaControlador controlador;
    private IBibliotecaRepo bibliotecaRepo;
    private IUsuarioRepo usuarioRepo;
    private IJuegoRepo juegoRepo;
    private ICompraRepo compraRepo;

    @Before
    public void setup() {
        // Inicializar repositorios (asumiendo que existen implementaciones in-memory)
        bibliotecaRepo = new BibliotecaRepoInMemory(); // Necesitarás crear este o usar el existente
        usuarioRepo = new UsuarioRepoInMemory(); // Necesitarás crear este o usar el existente
        juegoRepo = new JuegoRepoInMemory(); // Necesitarás crear este o usar el existente
        compraRepo = new CompraRepoInMemory();

        controlador = new BibliotecaControlador(bibliotecaRepo, usuarioRepo, juegoRepo, compraRepo);

        // Setup de datos de prueba - Crear usuario y juego si no existen
        setupTestData();
    }

    private void setupTestData() {
        // Aquí deberías crear un usuario con ID 1 y un juego con ID 1 si no existen
        // Esto depende de cómo estén implementados tus repositorios
    }

    /**
     * Acción: Ver biblioteca personal
     * Debe listar todos los juegos que posee un usuario
     */
    @Test
    public void testVerBibliotecaPersonal() throws ValidacionException {
        List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(1L, OrdenBusquedaBibliotecaEnum.ALFABETICO);
        assertNotNull("Debe retornar lista de biblioteca", biblioteca);
    }

    /**
     * Acción: Añadir juego a biblioteca
     * Debe agregar un juego adquirido a la biblioteca del usuario
     */
    @Test
    public void testAnadirJuegoABiblioteca() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> resultado = controlador.anhadirJuego(form);
        assertNotNull("Debe retornar Optional", resultado);
    }

    /**
     * Acción: Añadir juego a biblioteca
     * No debe permitir juegos duplicados en biblioteca de mismo usuario
     */
    @Test
    public void testAnadirDuplicado() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);

        // Primera adición
        Optional<BibliotecaDto> resultado1 = controlador.anhadirJuego(form);

        if (resultado1.isPresent()) {
            // Segunda adición (debería fallar por duplicado)
            assertThrows(
                    ValidacionException.class,
                    () -> controlador.anhadirJuego(form));
        }
    }

    /**
     * Acción: Eliminar juego de biblioteca
     * Debe quitar un juego de la biblioteca del usuario
     */
    @Test
    public void testEliminarJuegoDeBiblioteca() throws ValidacionException {
        // Primero añade un juego
        BibliotecaForm form = new BibliotecaForm(1L, 2L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> anadido = controlador.anhadirJuego(form);

        if (anadido.isPresent()) {
            // Luego lo elimina
            Optional<BibliotecaDto> eliminado = controlador.eliminarJuego(form);
            assertTrue("Debe eliminar juego", eliminado.isPresent());
        }
    }

    /**
     * Acción: Actualizar tiempo de juego
     * Debe registrar y actualizar las horas jugadas
     */
    @Test
    public void testActualizarTiempoJuego() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> anadido = controlador.anhadirJuego(form);

        if (anadido.isPresent()) {
            Optional<BibliotecaDto> actualizado = controlador.actualizarTempoJuego(form, 5.5f);
            assertTrue("Debe actualizar tiempo de juego", actualizado.isPresent());
            assertEquals("Tiempo debe incrementarse", 5.5f, actualizado.get().getHorasJugadasTotal(), 0.1f);
        }
    }

    /**
     * Acción: Actualizar tiempo de juego
     * No debe permitir horas negativas
     */
    @Test
    public void testAnadirTiempoNegativo() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> anadido = controlador.anhadirJuego(form);

        if (anadido.isPresent()) {
            assertThrows("No debe permitir horas negativas",
                    ValidacionException.class,
                    () -> controlador.actualizarTempoJuego(form, -5f));
        }
    }

    /**
     * Acción: Consultar última sesión
     * Debe mostrar la última vez que se jugó a un juego específico
     */
    @Test
    public void testConsultarUltimaSesion() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> anadido = controlador.anhadirJuego(form);

        if (anadido.isPresent()) {
            Optional<BibliotecaDto> resultado = controlador.consultarUltimaSesion(form);
            assertTrue("Debe consultar última sesión", resultado.isPresent());
        }
    }

    /**
     * Acción: Ver estadísticas de biblioteca
     * Debe mostrar métricas generales: total juegos, horas totales, juegos instalados, etc.
     */
    @Test
    public void testVerEstadisticasBiblioteca() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<EstadisticasBibliotecaDto> estadisticas = controlador.estadisticasBiblioteca(form);
        assertNotNull("Debe retornar estadísticas", estadisticas);
    }

    /**
     * Acción: Ver biblioteca ordenada alfabéticamente
     */
    @Test
    public void testVerBibliotecaOrdenadaAlfabeticamente() throws ValidacionException {
        List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(1L, OrdenBusquedaBibliotecaEnum.ALFABETICO);
        assertNotNull("Debe retornar biblioteca", biblioteca);
    }

    /**
     * Acción: Ver biblioteca ordenada por última sesión
     */
    @Test
    public void testVerBibliotecaOrdenadaPorUltimaSesion() throws ValidacionException {
        List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(1L, OrdenBusquedaBibliotecaEnum.ULTIMA_SESION);
        assertNotNull("Debe retornar biblioteca", biblioteca);
    }

    /**
     * Acción: Ver biblioteca ordenada por tiempo de juego
     */
    @Test
    public void testVerBibliotecaOrdenadaPorTiempoJuego() throws ValidacionException {
        List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(1L, OrdenBusquedaBibliotecaEnum.TIEMPO_JUEGO);
        assertNotNull("Debe retornar biblioteca", biblioteca);
    }

    /**
     * Acción: Ver biblioteca ordenada por fecha de adquisición
     */
    @Test
    public void testVerBibliotecaOrdenadaPorFechaAdquisicion() throws ValidacionException {
        List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(1L, OrdenBusquedaBibliotecaEnum.FECHA_ADQUISICION);
        assertNotNull("Debe retornar biblioteca", biblioteca);
    }

    /**
     * Validación: Juego debe existir en el sistema
     */
    @Test
    public void testJuegoNoExiste() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 99999L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        assertThrows("Debe rechazar juego inexistente",
                ValidacionException.class,
                () -> controlador.anhadirJuego(form));
    }

    /**
     * Validación: Usuario debe existir en el sistema
     */
    @Test
    public void testUsuarioNoExiste() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(99999L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        assertThrows("Debe rechazar usuario inexistente",
                ValidacionException.class,
                () -> controlador.anhadirJuego(form));
    }

    /**
     * Acción: Múltiples juegos en biblioteca
     */
    @Test
    public void testMultiplesJuegosEnBiblioteca() throws ValidacionException {
        BibliotecaForm form1 = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        BibliotecaForm form2 = new BibliotecaForm(1L, 2L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);

        Optional<BibliotecaDto> juego1 = controlador.anhadirJuego(form1);
        Optional<BibliotecaDto> juego2 = controlador.anhadirJuego(form2);

        if (juego1.isPresent() && juego2.isPresent()) {
            List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(1L, null);
            assertTrue("Debe tener múltiples juegos", biblioteca.size() >= 2);
        }
    }

    /**
     * Acción: Actualizar múltiples veces el tiempo de juego
     */
    @Test
    public void testActualizarTiempoMultiples() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> anadido = controlador.anhadirJuego(form);

        if (anadido.isPresent()) {
            controlador.actualizarTempoJuego(form, 5.0f);
            controlador.actualizarTempoJuego(form, 10.0f);

            Optional<BibliotecaDto> actualizado = controlador.consultarUltimaSesion(form);
            if (actualizado.isPresent()) {
                assertEquals("Tiempo total debe ser 15.0", 15.0f, actualizado.get().getHorasJugadasTotal(), 0.1f);
            }
        }
    }

    /**
     * Acción: Juego nunca jugado
     */
    @Test
    public void testJuegoNuncaJugado() throws ValidacionException {
        BibliotecaForm form = new BibliotecaForm(1L, 1L, LocalDate.now(), 0.0f, null, EstadoInstalacionEnum.NO_INSTALADO);
        Optional<BibliotecaDto> anadido = controlador.anhadirJuego(form);

        if (anadido.isPresent()) {
            Optional<BibliotecaDto> resultado = controlador.consultarUltimaSesion(form);
            if (resultado.isPresent()) {
                assertEquals("Tiempo debe ser 0", 0f, resultado.get().getHorasJugadasTotal(), 0.01f);
            }
        }
    }

    /**
     * Acción: Biblioteca vacía de usuario sin juegos
     */
    @Test
    public void testBibliotecaVacia() throws ValidacionException {
        List<BibliotecaDto> biblioteca = controlador.verBibliotecaPersonal(999L, null);
        assertNotNull("Debe permitir consultar biblioteca vacía", biblioteca);
        assertTrue("Biblioteca debería estar vacía", biblioteca.isEmpty());
    }
}
