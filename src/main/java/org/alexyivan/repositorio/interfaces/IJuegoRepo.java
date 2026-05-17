package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz repositorio para operaciones CRUD de Juegos.
 * Gestiona el acceso a datos de los juegos disponibles en el catálogo.
 */
public interface IJuegoRepo extends ICrud<JuegoEntidad, JuegoForm, Long> {

    /**
     * Busca un juego en el catálogo por su título exacto.
     *
     * @param titulo Título del juego a buscar.
     * @return Un Optional con el juego si existe, vacío en caso contrario.
     */
    Optional<JuegoEntidad> obtenerTitulo(String titulo);


}
