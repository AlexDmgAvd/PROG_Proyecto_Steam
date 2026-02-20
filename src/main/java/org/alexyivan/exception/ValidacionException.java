package org.alexyivan.exception;

import org.alexyivan.modelo.form.ErrorDto;

import java.util.List;

public class ValidacionException extends RuntimeException {

    private List<ErrorDto> errores;
    public ValidacionException(List<ErrorDto> errorDtos) {
        super();
        this.errores = errorDtos;
    }

    public List<ErrorDto> getErrores(){
        return errores;
    }
}
