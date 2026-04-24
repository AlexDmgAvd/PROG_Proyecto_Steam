package org.alexyivan.repositorio.hibernate;

import org.alexyivan.modelo.entidad.ResenhaEntidad;
import org.alexyivan.modelo.form.ResenhaForm;
import org.alexyivan.repositorio.interfaces.IResenhaRepo;

import java.util.List;
import java.util.Optional;

public class ResenhaRepoHibernate implements IResenhaRepo {
    @Override
    public Optional<ResenhaEntidad> crear(ResenhaForm dto) {
        return Optional.empty();
    }

    @Override
    public Optional<ResenhaEntidad> obtenerPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<ResenhaEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<ResenhaEntidad> actualizar(Long aLong, ResenhaForm dto) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long aLong) {
        return false;
    }
}
