package org.alexyivan.transaction;

import org.alexyivan.exception.ValidacionException;

public interface IExceptionSupplier<T> {

    T get() throws ValidacionException;
}
