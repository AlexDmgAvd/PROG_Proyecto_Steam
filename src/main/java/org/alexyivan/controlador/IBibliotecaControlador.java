package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.dto.EstadisticasBibliotecaDto;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.form.BibliotecaForm;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de lógica de negocio para la Biblioteca personal de usuarios.
 * Gestiona operaciones relacionadas con la biblioteca de juegos comprados por cada usuario.
 */
public interface IBibliotecaControlador {


    /**
     * Obtiene todos los juegos de la biblioteca personal de un usuario.
     *
     * @param id Identificador único del usuario.
     * @param busquedaBiblioteca Criterio de ordenamiento para los resultados.
     * @return Lista de juegos en la biblioteca del usuario, ordenados según el criterio especificado.
     */
    List<BibliotecaDto> verBibliotecaPersonal(long id, OrdenBusquedaBibliotecaEnum busquedaBiblioteca);

    /**
     * Añade un juego a la biblioteca personal de un usuario.
     *
     * @param bibliotecaForm Formulario con los datos del juego a añadir a la biblioteca.
     * @return Un Optional con el registro de biblioteca creado, o vacío si la operación falla.
     */
    Optional<BibliotecaDto> anhadirJuego(BibliotecaForm bibliotecaForm);

    /**
     * Elimina un juego de la biblioteca personal de un usuario.
     *
     * @param bibliotecaForm Formulario con los datos para identificar el juego a eliminar.
     * @return Un Optional con el registro de biblioteca eliminado, o vacío si la operación falla.
     */
    Optional<BibliotecaDto> eliminarJuego(BibliotecaForm bibliotecaForm);

    /**
     * Actualiza el tiempo de juego registrado para un juego en la biblioteca.
     *
     * @param bibliotecaForm Formulario con los datos del juego a actualizar.
     * @param horas Cantidad de horas jugadas a registrar.
     * @return Un Optional con el registro de biblioteca actualizado, o vacío si la operación falla.
     */
    Optional<BibliotecaDto> actualizarTempoJuego(BibliotecaForm bibliotecaForm, float horas);

    /**
     * Obtiene información de la última sesión de juego registrada.
     *
     * @param bibliotecaForm Formulario con los datos del juego.
     * @return Un Optional con la información de la última sesión, o vacío si no existe.
     */
    Optional<BibliotecaDto> consultarUltimaSesion(BibliotecaForm bibliotecaForm);

    /**
     * Calcula y obtiene estadísticas generales de la biblioteca de un usuario.
     *
     * @param bibliotecaForm Formulario con los datos necesarios para calcular las estadísticas.
     * @return Un Optional con las estadísticas de la biblioteca, o vacío si no se pueden calcular.
     */
    Optional<EstadisticasBibliotecaDto> estadisticasBiblioteca (BibliotecaForm bibliotecaForm);
}
