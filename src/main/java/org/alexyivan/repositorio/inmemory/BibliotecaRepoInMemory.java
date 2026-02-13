package org.alexyivan.repositorio.inmemory;

import org.alexyivan.Modelo.Entidad.BibliotecaEntidad;
import org.alexyivan.Modelo.Form.BibliotecaForm;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibliotecaRepoInMemory implements IBibliotecaRepo {
    private static final List<BibliotecaEntidad> bibliotecas = new ArrayList<>();
    private static long idCounter = bibliotecas.size() + 1;


    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm bibliotecaForm) {
        //var new BibliotecaEntidad(idCounter++,)
        return Optional.empty();
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {
        return bibliotecas.stream()
                .filter(b -> b.id() == id)
                .findFirst();
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        return new ArrayList<BibliotecaEntidad>(bibliotecas);
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm bibliotecaForm) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long id) {
        return bibliotecas.removeIf(b -> b.id() ==id);
    }
}
