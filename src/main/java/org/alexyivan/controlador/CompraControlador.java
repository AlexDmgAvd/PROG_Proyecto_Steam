package org.alexyivan.controlador;

import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.ICompraRepo;

public class CompraControlador implements ICompraControlador {

    private final ICompraRepo compraRepo ;

    public CompraControlador(ICompraRepo compraRepo) {
        this.compraRepo = compraRepo;
    }

}
