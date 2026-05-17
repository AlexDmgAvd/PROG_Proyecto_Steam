package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.modelo.form.UsuarioForm;

import java.util.Optional;

/**
 * Controlador de lógica de negocio para Usuarios.
 * Gestiona operaciones de registro, autenticación, consulta y gestión del saldo de usuarios.
 */
public interface IUsuarioControlador {

    /**
     * Registra un nuevo usuario en la plataforma.
     *
     * @param usuarioForm Formulario con los datos del usuario a registrar (nombre, correo, contraseña, etc.).
     * @return Un Optional con los datos del usuario creado, o vacío si el registro falla.
     * @throws ValidacionException Si los datos del usuario no son válidos o ya existe una cuenta con ese correo/usuario.
     */
    Optional<UsuarioDto> registrarUsuario(UsuarioForm usuarioForm) throws ValidacionException;

    /**
     * Obtiene la información de un usuario buscando por su nombre de usuario único.
     *
     * @param nombreUsuario Nombre de usuario único a buscar.
     * @return Un Optional con los datos del usuario, o vacío si no existe.
     * @throws ValidacionException Si el nombre de usuario no es válido.
     */
    Optional<UsuarioDto> consultarUsuarioNombreUsuario(String nombreUsuario) throws ValidacionException;

    /**
     * Obtiene la información completa de un usuario por su identificador único.
     *
     * @param id Identificador único del usuario.
     * @return Un Optional con los datos completos del usuario, o vacío si no existe.
     * @throws ValidacionException Si el identificador no es válido.
     */
    Optional<UsuarioDto> consultarUsuarioId(Long id) throws ValidacionException;

    /**
     * Añade fondos al saldo de crédito de un usuario.
     *
     * @param id Identificador único del usuario.
     * @param cantidad Cantidad de dinero a añadir al saldo.
     * @return Un Optional con el usuario actualizado con el nuevo saldo, o vacío si falla.
     * @throws ValidacionException Si el identificador es inválido o la cantidad es negativa.
     */
    Optional<UsuarioDto> anhadirSaldo(long id, float cantidad) throws ValidacionException;

    /**
     * Obtiene el saldo de crédito actual de un usuario.
     *
     * @param id Identificador único del usuario.
     * @return Un Optional con la información del usuario incluido su saldo actual, o vacío si no existe.
     * @throws ValidacionException Si el identificador no es válido.
     */
    Optional<UsuarioDto> consultarSaldo(long id) throws ValidacionException;


}
