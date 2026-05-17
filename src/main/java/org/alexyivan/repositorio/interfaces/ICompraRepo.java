package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.CompraEntidad;
import org.alexyivan.modelo.form.CompraForm;

import java.util.Optional;

/**
 * Interfaz repositorio para operaciones CRUD de Compras.
 * Gestiona el acceso a datos de transacciones de compra realizadas por usuarios.
 */
public interface ICompraRepo extends ICrud<CompraEntidad, CompraForm, Long> {

    /**
     * Busca una compra específica de un juego por parte de un usuario.
     *
     * @param idJuego Identificador único del juego comprado.
     * @param idUsuario Identificador único del usuario que realizó la compra.
     * @return Un Optional con la compra si existe, vacío en caso contrario.
     */
    Optional<CompraEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario);

}
