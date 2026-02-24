package org.alexyivan.controlador;

import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IResenhaRepo;

public class ResenhaControlador implements IResenhaControlador {

    private final IResenhaRepo resenhaRepo ;

    public ResenhaControlador(IResenhaRepo resenhaRepo) {
        this.resenhaRepo = resenhaRepo;
    }

}
