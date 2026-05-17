package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.form.UsuarioForm;

import java.util.Optional;

/**
 * Interfaz repositorio para operaciones CRUD de Usuarios.
 * Gestiona el acceso a datos de cuentas de usuario en la plataforma.
 */
public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {


    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario a buscar.
     * @return Un Optional con el usuario si existe, vacío en caso contrario.
     */
    Optional<UsuarioEntidad> obtenerPorEmail(String email);

    /**
     * Busca un usuario por su nombre de usuario (identificador único alfanumérico).
     *
     * @param nombre Nombre de usuario a buscar.
     * @return Un Optional con el usuario si existe, vacío en caso contrario.
     */
    Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombre);


}
