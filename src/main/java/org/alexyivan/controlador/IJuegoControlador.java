package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaJuegoEnum;
import org.alexyivan.modelo.form.BusquedaJuegosForm;
import org.alexyivan.modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de lógica de negocio para Juegos.
 * Gestiona operaciones de catálogo de juegos, búsqueda, filtrado y administración de estado.
 */
public interface IJuegoControlador {

    /**
     * Añade un nuevo juego al catálogo.
     *
     * @param formulario Formulario con los datos del juego a añadir.
     * @return Un Optional con los datos del juego creado, o vacío si falla.
     * @throws ValidacionException Si los datos del juego no son válidos.
     */
    Optional<JuegoDto> anhadirJuegoCatalogo(JuegoForm formulario) throws ValidacionException;

    /**
     * Busca juegos en el catálogo según criterios especificados.
     *
     * @param busquedaJuegos Formulario con los criterios de búsqueda (título, género, rango de precio, etc.).
     * @return Lista de juegos que coinciden con los criterios de búsqueda.
     * @throws ValidacionException Si los criterios de búsqueda no son válidos.
     */
    List<JuegoDto> buscarJuegos(BusquedaJuegosForm busquedaJuegos) throws ValidacionException;

    /**
     * Obtiene todos los juegos del catálogo, ordenados según un criterio especificado.
     *
     * @param orden Criterio de ordenamiento de los juegos (por título, fecha, precio, etc.).
     * @return Lista de todos los juegos del catálogo, ordenados según el criterio especificado.
     * @throws ValidacionException Si el criterio de ordenamiento no es válido.
     */
    List<JuegoDto> listarTodosJuegos(OrdenBusquedaJuegoEnum orden) throws ValidacionException;

    /**
     * Obtiene la información detallada de un juego específico.
     *
     * @param id Identificador único del juego.
     * @return Un Optional con los datos completos del juego, o vacío si no existe.
     * @throws ValidacionException Si el identificador no es válido.
     */
    Optional<JuegoDto> consultarJuego(long id) throws ValidacionException;

    /**
     * Actualiza el descuento aplicado a un juego.
     *
     * @param id Identificador único del juego a actualizar.
     * @param descuento Porcentaje de descuento a aplicar (0-100).
     * @return Un Optional con el juego actualizado, o vacío si falla.
     * @throws ValidacionException Si el identificador es inválido o el descuento está fuera de rango.
     */
    Optional<JuegoDto> actualizarDescuento(long id, int descuento) throws ValidacionException;

    /**
     * Cambia el estado de disponibilidad de un juego en el catálogo.
     *
     * @param id Identificador único del juego.
     * @param estado Nuevo estado del juego (disponible, agotado, descontinuado, etc.).
     * @return Un Optional con el juego actualizado, o vacío si falla.
     * @throws ValidacionException Si el identificador es inválido o el estado no es válido.
     */
    Optional<JuegoDto> cambiarEstado(long id, EstadoJuegoEnum estado) throws ValidacionException;


}
