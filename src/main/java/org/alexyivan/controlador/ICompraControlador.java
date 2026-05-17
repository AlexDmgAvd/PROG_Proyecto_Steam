package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.CompraDto;
import org.alexyivan.modelo.enums.OpcionesReembolsoEnum;
import org.alexyivan.modelo.form.BusquedaCompraForm;
import org.alexyivan.modelo.form.CompraForm;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Controlador de lógica de negocio para Compras.
 * Gestiona operaciones de compra, pagos, historial de transacciones y reembolsos.
 */
public interface ICompraControlador {

    /**
     * Realiza una nueva compra de un juego por parte de un usuario.
     *
     * @param formularioCompra Formulario con los datos de la compra a realizar.
     * @return Un Optional con los datos de la compra realizada, o vacío si falla.
     * @throws ValidacionException Si los datos de la compra no son válidos.
     */
    Optional<CompraDto> realizarCompra(CompraForm formularioCompra) throws ValidacionException;


    /**
     * Procesa el pago de una compra existente.
     *
     * @param id Identificador único de la compra.
     * @return Un Optional con la compra actualizada con el estado de pago procesado, o vacío si falla.
     * @throws ValidacionException Si la compra no existe o el pago no puede procesarse.
     */
    Optional<CompraDto> procesarPago(long id) throws ValidacionException;


    /**
     * Obtiene el historial de compras de un usuario con opciones de búsqueda y filtrado.
     *
     * @param formularioCompra Formulario base con información de la compra.
     * @param opcionesBusqueda Opciones y criterios de búsqueda para filtrar el historial.
     * @param id Identificador único del usuario del que se consulta el historial.
     * @return Lista de compras realizadas por el usuario, filtradas y ordenadas según los criterios especificados.
     * @throws ValidacionException Si los parámetros de búsqueda no son válidos.
     */
    List<CompraDto> consultarHistorialCompras(CompraForm formularioCompra, BusquedaCompraForm opcionesBusqueda, long id) throws ValidacionException;


    /**
     * Obtiene los detalles completos de una compra específica.
     *
     * @param idCompra Identificador único de la compra.
     * @param idUsuario Identificador único del usuario propietario de la compra.
     * @return Un Optional con los detalles de la compra, o vacío si no existe.
     * @throws ValidacionException Si los identificadores no son válidos o no corresponden.
     */
    Optional<CompraDto> consultarDetallesCompra(long idCompra, long idUsuario) throws ValidacionException;


    /**
     * Solicita un reembolso para una compra realizada.
     *
     * @param idCompra Identificador único de la compra a la que solicitar reembolso.
     * @param opcionesReembolso Opciones seleccionadas para el reembolso (parcial, total, etc.).
     * @return Un Optional con la compra actualizada con el estado de reembolso solicitado, o vacío si falla.
     * @throws ValidacionException Si la compra no es reembolsable o la solicitud no es válida.
     */
    Optional<CompraDto> solicitarReembolso(long idCompra, OpcionesReembolsoEnum opcionesReembolso) throws ValidacionException;


    /**
     * Genera una factura en archivo para una compra realizada.
     *
     * @param idCompra Identificador único de la compra de la que generar factura.
     * @return Ruta del archivo de factura generado.
     * @throws ValidacionException Si la compra no existe o no es válida para generar factura.
     * @throws IOException Si ocurre un error al escribir el archivo de factura.
     */
    Path generarFactura (long idCompra) throws ValidacionException, IOException;

}
