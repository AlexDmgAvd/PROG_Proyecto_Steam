package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.EstadisticasResenhaDto;
import org.alexyivan.modelo.dto.ResenhaDto;
import org.alexyivan.modelo.enums.BusquedaResenhasValoracionEnum;
import org.alexyivan.modelo.enums.EstadoResenhaEnum;
import org.alexyivan.modelo.enums.OrdenResenhasEnum;
import org.alexyivan.modelo.form.ResenhaForm;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de lógica de negocio para Reseñas.
 * Gestiona operaciones relacionadas con reseñas, valoraciones y comentarios de juegos.
 */
public interface IResenhaControlador {

    /**
     * Registra una nueva reseña de un juego escrita por un usuario.
     *
     * @param formularioResenha Formulario con los datos de la reseña (juego, usuario, calificación, texto).
     * @return Un Optional con los datos de la reseña creada, o vacío si falla.
     * @throws ValidacionException Si los datos de la reseña no son válidos.
     */
    Optional<ResenhaDto> escribirResenha(ResenhaForm formularioResenha) throws ValidacionException;

    /**
     * Elimina una reseña existente.
     *
     * @param idResenha Identificador único de la reseña a eliminar.
     * @param idUsuario Identificador único del usuario propietario de la reseña.
     * @return Un Optional con los datos de la reseña eliminada, o vacío si falla.
     * @throws ValidacionException Si la reseña no existe o el usuario no tiene permisos para eliminarla.
     */
    Optional<ResenhaDto> eliminarResenha(long idResenha, long idUsuario) throws ValidacionException;

    /**
     * Obtiene todas las reseñas asociadas a un juego específico, con opciones de filtrado y ordenamiento.
     *
     * @param id Identificador único del juego.
     * @param valoracion Filtro opcional por rango de valoración (reseñas positivas, negativas, etc.).
     * @param orden Criterio opcional de ordenamiento (por fecha, utilidad, valoración, etc.).
     * @return Lista de reseñas del juego, filtradas y ordenadas según los criterios especificados.
     * @throws ValidacionException Si el identificador del juego no es válido.
     */
    List<ResenhaDto> verResenhasJuego(long id, Optional<BusquedaResenhasValoracionEnum> valoracion,
                                      Optional<OrdenResenhasEnum> orden) throws ValidacionException;

    /**
     * Oculta una reseña existente sin eliminarla de la base de datos.
     *
     * @param idResenha Identificador único de la reseña a ocultar.
     * @param idUsuario Identificador único del usuario propietario de la reseña.
     * @return Un Optional con la reseña actualizada con estado oculto, o vacío si falla.
     * @throws ValidacionException Si la reseña no existe o el usuario no tiene permisos para ocultarla.
     */
    Optional<ResenhaDto> ocultarResenha(long idResenha, long idUsuario) throws ValidacionException;

    /**
     * Obtiene todas las reseñas escritas por un usuario específico, con opción de filtrado por estado.
     *
     * @param formularioResenha Formulario con la información del usuario autor de las reseñas.
     * @param estado Filtro opcional por estado de la reseña (visible, oculta, denunciada, etc.).
     * @return Lista de reseñas del usuario, filtradas por el estado especificado si se proporciona.
     */
    List<ResenhaDto> verResenhaUsuario(ResenhaForm formularioResenha, Optional<EstadoResenhaEnum> estado);
}
