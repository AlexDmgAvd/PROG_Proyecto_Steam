package org.alexyivan.repositorio.hibernate;

import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;

import java.util.List;
import java.util.Optional;

public class BibliotecaRepoHibernate implements IBibliotecaRepo {



    @Override
    public Optional<BibliotecaEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario) {
        return Optional.empty();
    }

    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm dto) {
        return Optional.empty();
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long aLong, BibliotecaForm dto) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long aLong) {
        return false;
    }
}
