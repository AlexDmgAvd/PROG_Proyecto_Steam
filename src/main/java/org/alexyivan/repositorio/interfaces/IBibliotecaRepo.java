package org.alexyivan.repositorio.interfaces;


import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.form.BibliotecaForm;

import java.util.Optional;

/**
 * Interfaz repositorio para operaciones CRUD de la Biblioteca de usuarios.
 * Gestiona el acceso a datos de la biblioteca personal de cada usuario.
 */
public interface IBibliotecaRepo extends ICrud<BibliotecaEntidad, BibliotecaForm, Long> {

    /**
     * Busca un juego específico en la biblioteca de un usuario.
     *
     * @param idJuego Identificador único del juego a buscar.
     * @param idUsuario Identificador único del usuario propietario de la biblioteca.
     * @return Un Optional con la entrada de biblioteca si existe, vacío en caso contrario.
     */
    Optional<BibliotecaEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario);


}
