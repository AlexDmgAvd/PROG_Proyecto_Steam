package org.alexyivan.transaction;

import org.alexyivan.exception.ValidacionException;

import java.util.Optional;

/**
 * Abstracción de unidad de trabajo atómica.
 * Desacopla el manejo de transacciones de los repositorios y el controlador.
 */
public interface ITransactionManager {

    /**
     * Ejecuta {@code work} dentro de una unidad de trabajo atómica.
     * Si ocurre cualquier excepción, la unidad se deshace (rollback)
     * y retorna {@link Optional#empty()}.
     */
    <T> T inTransaction(IExceptionSupplier<T> work) throws ValidacionException;
}
