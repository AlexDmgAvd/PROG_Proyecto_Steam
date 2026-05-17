package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.ResenhaEntidad;
import org.alexyivan.modelo.form.ResenhaForm;

/**
 * Interfaz repositorio para operaciones CRUD de Reseñas.
 * Gestiona el acceso a datos de las reseñas y valoraciones de juegos realizadas por usuarios.
 */
public interface IResenhaRepo extends ICrud<ResenhaEntidad, ResenhaForm, Long> {
}
