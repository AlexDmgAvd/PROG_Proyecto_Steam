package org.alexyivan.controlador;

import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

public class BibliotecaControlador implements IBibliotecaControlador {

    private final IBibliotecaRepo bibliotecaRepo ;

    public BibliotecaControlador(IBibliotecaRepo bibliotecaRepo) {
        this.bibliotecaRepo = bibliotecaRepo;
    }

}
