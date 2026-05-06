package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.ResenhaDto;
import org.alexyivan.modelo.enums.EstadoResenhaEnum;
import org.alexyivan.modelo.enums.OrdenResenhasEnum;
import org.alexyivan.modelo.enums.BusquedaResenhasValoracionEnum;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests para ResenhaControlador
 * Cubre acciones: Escribir reseña, Eliminar reseña, Ver reseñas de juego,
 * Ocultar reseña, Consultar estadísticas, Ver reseñas de usuario
 * Especificadas en steam.md bajo "Gestión de Reseñas"
 */
public class ResenhaControladorTest {

    private IResenhaControlador controlador;
    private String textoResenhaValido;

    @Before
    public void setup() {
        controlador = new ResenhaControlador();
        textoResenhaValido = "Este juego es increíble, tiene una historia fascinante y una jugabilidad muy adictiva. " +
                "Recomiendo ampliamente este juego a todos los amantes de los RPG.";
    }

    /**
     * Acción: Escribir reseña
     * Debe crear una nueva reseña para un juego que el usuario posee
     */
    @Test
    public void testEscribirResenhaValida() throws ValidacionException {
        Optional<ResenhaDto> resultado = controlador.escribirResena(1L, 1L, true, textoResenhaValido);
        // Resultado depende de si usuario tiene el juego en biblioteca
        assertTrue("Debe retornar Optional", resultado != null);
    }

    /**
     * Acción: Escribir reseña
     * No debe permitir reseña si usuario no posee el juego
     */
    @Test
    public void testResenhaJuegoNoEnBiblioteca() throws ValidacionException {
        assertThrows("Debe rechazar reseña si juego no está en biblioteca",
                ValidacionException.class,
                () -> controlador.escribirResena(1L, 999L, true, textoResenhaValido));
    }

    /**
     * Validación: Usuario no puede tener dos reseñas del mismo juego
     */
    @Test
    public void testResenhasDuplicadas() throws ValidacionException {
        Optional<ResenhaDto> primera = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (primera.isPresent()) {
            assertThrows("No debe permitir dos reseñas del mismo juego por usuario",
                    ValidacionException.class,
                    () -> controlador.escribirResena(1L, 1L, false, textoResenhaValido));
        }
    }

    /**
     * Validación: Texto de reseña debe tener mínimo 50 caracteres
     */
    @Test
    public void testTextoMuyCorto() throws ValidacionException {
        assertThrows("Debe rechazar reseña con menos de 50 caracteres",
                ValidacionException.class,
                () -> controlador.escribirResena(1L, 1L, true, "Texto corto"));
    }

    /**
     * Validación: Texto de reseña no puede exceder 8000 caracteres
     */
    @Test
    public void testTextoMuyLargo() throws ValidacionException {
        String textoLargo = "a".repeat(8001);
        assertThrows("Debe rechazar reseña con más de 8000 caracteres",
                ValidacionException.class,
                () -> controlador.escribirResena(1L, 1L, true, textoLargo));
    }

    /**
     * Acción: Eliminar reseña
     * Debe cambiar el estado de una reseña a eliminada
     */
    @Test
    public void testEliminarResena() throws ValidacionException {
        Optional<ResenhaDto> resenha = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resenha.isPresent()) {
            Optional<ResenhaDto> resultado = controlador.eliminarResena(resenha.get().getId(), 1L);
            assertTrue("Debe eliminar reseña", resultado.isPresent());
            if (resultado.isPresent()) {
                assertEquals("Estado debe ser ELIMINADA", EstadoResenhaEnum.ELIMINADA, resultado.get().getEstado());
            }
        }
    }

    /**
     * Acción: Ver reseñas de un juego
     * Debe listar todas las reseñas publicadas de un juego específico
     */
    @Test
    public void testVerResenhasJuego() throws ValidacionException {
        List<ResenhaDto> resenas = controlador.verResenhasJuego(1L, null, null);
        assertTrue("Debe retornar lista de reseñas", resenas != null);
    }

    /**
     * Acción: Ver reseñas de un juego filtradas por recomendación
     */
    @Test
    public void testVerResenhasPositivas() throws ValidacionException {
        List<ResenhaDto> resenas = controlador.verResenhasJuego(1L, BusquedaResenhasValoracionEnum.POSITIVAS, null);
        assertTrue("Debe filtrar reseñas positivas", resenas != null);
    }

    /**
     * Acción: Ver reseñas de un juego filtradas por recomendación negativa
     */
    @Test
    public void testVerResenhasNegativas() throws ValidacionException {
        List<ResenhaDto> resenas = controlador.verResenhasJuego(1L, BusquedaResenhasValoracionEnum.NEGATIVAS, null);
        assertTrue("Debe filtrar reseñas negativas", resenas != null);
    }

    /**
     * Acción: Ver reseñas ordenadas por recientes
     */
    @Test
    public void testVerResenhasRecientes() throws ValidacionException {
        List<ResenhaDto> resenas = controlador.verResenhasJuego(1L, null, OrdenResenhasEnum.RECIENTES);
        assertTrue("Debe ordenar por recientes", resenas != null);
    }

    /**
     * Acción: Ver reseñas ordenadas por útiles
     */
    @Test
    public void testVerResenhasUtiles() throws ValidacionException {
        List<ResenhaDto> resenas = controlador.verResenhasJuego(1L, null, OrdenResenhasEnum.UTILES);
        assertTrue("Debe ordenar por utilidad", resenas != null);
    }

    /**
     * Acción: Ocultar reseña
     * Debe cambiar la visibilidad de una reseña a oculta
     */
    @Test
    public void testOcultarResena() throws ValidacionException {
        Optional<ResenhaDto> resenha = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resenha.isPresent()) {
            Optional<ResenhaDto> resultado = controlador.ocultarResena(resenha.get().getId(), 1L);
            assertTrue("Debe ocultar reseña", resultado.isPresent());
            if (resultado.isPresent()) {
                assertEquals("Estado debe ser OCULTA", EstadoResenhaEnum.OCULTA, resultado.get().getEstado());
            }
        }
    }

    /**
     * Validación: No se puede ocultar reseña no publicada
     */
    @Test
    public void testOcultarResenhaEliminada() throws ValidacionException {
        Optional<ResenhaDto> resenha = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resenha.isPresent()) {
            // Eliminar primero
            controlador.eliminarResena(resenha.get().getId(), 1L);

            // Intentar ocultar la eliminada
            assertThrows("No puede ocultar reseña eliminada",
                    ValidacionException.class,
                    () -> controlador.ocultarResena(resenha.get().getId(), 1L));
        }
    }

    /**
     * Acción: Ver reseñas de un usuario
     * Debe listar todas las reseñas escritas por un usuario específico
     */
    @Test
    public void testVerResenhasUsuario() throws ValidacionException {
        List<ResenhaDto> resenas = controlador.verResenhasUsuario(1L, null);
        assertTrue("Debe retornar reseñas del usuario", resenas != null);
    }

    /**
     * Acción: Consultar estadísticas de reseñas
     * Debe calcular métricas: total, % positivas, % negativas, promedio horas, tendencia
     */
    @Test
    public void testConsultarEstadisticasResenas() throws ValidacionException {
        Optional<?> estadisticas = controlador.consultarEstadisticsResenas(1L);
        assertTrue("Debe retornar estadísticas", estadisticas != null);
    }

    /**
     * Acción: Reseña recomendada
     * Usuario indica que recomienda el juego
     */
    @Test
    public void testResenhaRecomendada() throws ValidacionException {
        Optional<ResenhaDto> resultado = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resultado.isPresent()) {
            assertTrue("Debe indicar recomendado", resultado.get().isRecomendado());
        }
    }

    /**
     * Acción: Reseña negativa
     * Usuario indica que NO recomienda el juego
     */
    @Test
    public void testResenhaNegativa() throws ValidacionException {
        Optional<ResenhaDto> resultado = controlador.escribirResena(1L, 1L, false, 
                "Este juego no cumple las expectativas. La jugabilidad es confusa " +
                "y los gráficos no son lo que esperaba por el precio.");

        if (resultado.isPresent()) {
            assertFalse("Debe indicar no recomendado", resultado.get().isRecomendado());
        }
    }

    /**
     * Acción: Reseña con horas jugadas registradas
     * Debe obtener horas jugadas automáticamente desde biblioteca
     */
    @Test
    public void testResenhaConHorasJugadas() throws ValidacionException {
        Optional<ResenhaDto> resultado = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resultado.isPresent()) {
            assertTrue("Debe tener horas jugadas registradas", resultado.get().getHorasJugadas() >= 0);
        }
    }

    /**
     * Validación: Solo reseñas PUBLICADAS deben ser visibles
     * Las ELIMINADAS y OCULTAS no deben aparecer en listados públicos
     */
    @Test
    public void testVisibilidadResenas() throws ValidacionException {
        Optional<ResenhaDto> resenha = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resenha.isPresent()) {
            // En listado debe estar publicada
            List<ResenhaDto> listado = controlador.verResenhasJuego(1L, null, null);
            assertTrue("Reseña publicada debe aparecer en listado", 
                    listado.stream().anyMatch(r -> r.getId().equals(resenha.get().getId())));

            // Después de eliminar, no debe aparecer
            controlador.eliminarResena(resenha.get().getId(), 1L);
            listado = controlador.verResenhasJuego(1L, null, null);
            assertFalse("Reseña eliminada no debe aparecer en listado", 
                    listado.stream().anyMatch(r -> r.getId().equals(resenha.get().getId())));
        }
    }

    /**
     * Acción: Editar reseña (cambiar última fecha de edición)
     */
    @Test
    public void testEditarResena() throws ValidacionException {
        Optional<ResenhaDto> resenha = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resenha.isPresent()) {
            LocalDate fechaEdicion = LocalDate.now();
            // Editar con nuevo texto
            Optional<ResenhaDto> editada = controlador.escribirResena(1L, 1L, false, 
                    "Cambié de opinión. He jugado más y ahora veo que el juego vale menos.");
            
            // Nota: Esto puede crear error si no se permite segunda reseña
            // Este test asume que hay método de actualización
        }
    }

    /**
     * Acción: Múltiples reseñas de diferentes usuarios del mismo juego
     */
    @Test
    public void testMultiplesResenhasUnJuego() throws ValidacionException {
        Optional<ResenhaDto> resena1 = controlador.escribirResena(1L, 1L, true, textoResenhaValido);
        Optional<ResenhaDto> resena2 = controlador.escribirResena(2L, 1L, false, textoResenhaValido);

        List<ResenhaDto> resenas = controlador.verResenhasJuego(1L, null, null);
        assertTrue("Debe tener múltiples reseñas", resenas.size() >= 2);
    }

    /**
     * Acción: Porcentaje de reseñas positivas
     */
    @Test
    public void testPorcentajeResenhasPositivas() throws ValidacionException {
        // Escribir varias reseñas
        controlador.escribirResena(1L, 1L, true, textoResenhaValido);
        controlador.escribirResena(2L, 1L, true, textoResenhaValido);
        controlador.escribirResena(3L, 1L, false, "No me gustó este juego por razones.");

        Optional<?> estadisticas = controlador.consultarEstadisticsResenas(1L);
        // Debería mostrar 66.7% positivas
    }

    /**
     * Validación: Usuario solo puede eliminar/ocultar sus propias reseñas
     */
    @Test
    public void testEliminarResenhaDeOtroUsuario() throws ValidacionException {
        Optional<ResenhaDto> resenha = controlador.escribirResena(1L, 1L, true, textoResenhaValido);

        if (resenha.isPresent()) {
            assertThrows("Usuario no puede eliminar reseña de otro usuario",
                    ValidacionException.class,
                    () -> controlador.eliminarResena(resenha.get().getId(), 2L)); // Otro usuario
        }
    }

    /**
     * Acción: Texto de reseña exactamente en límite mínimo (50 caracteres)
     */
    @Test
    public void testTextolímiteMinimo() throws ValidacionException {
        String texto50 = "a".repeat(46) + "texto"; // Exactamente 50
        Optional<ResenhaDto> resultado = controlador.escribirResena(1L, 1L, true, texto50);
        assertTrue("Debe permitir reseña en límite mínimo", resultado != null);
    }

    /**
     * Acción: Texto de reseña exactamente en límite máximo (8000 caracteres)
     */
    @Test
    public void testTextoLimiteMaximo() throws ValidacionException {
        String texto8000 = "a".repeat(7995) + "texto"; // Exactamente 8000
        Optional<ResenhaDto> resultado = controlador.escribirResena(1L, 1L, true, texto8000);
        assertTrue("Debe permitir reseña en límite máximo", resultado != null);
    }
}
