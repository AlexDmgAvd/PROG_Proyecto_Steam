package org.alexyivan.transaction;

import org.alexyivan.exception.ValidacionException;

import java.util.Optional;

/**
 * Implementaci�n no-op de {@link ITransactionManager}.
 * Se usa con repositorios en memoria donde no existe el concepto de
 * transacci�n.
 */
public class NoOpTransactionManager implements ITransactionManager {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T inTransaction(IExceptionSupplier<T> work) throws ValidacionException {
        try {
            return work.get();
        }catch(ValidacionException e){
            throw e;
        }
        catch (Exception e) {
            try {
                return (T) Optional.empty();
            } catch (ClassCastException ex) {
                return null;
            }
        }
    }
}
