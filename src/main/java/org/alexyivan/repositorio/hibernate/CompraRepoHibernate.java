package org.alexyivan.repositorio.hibernate;

import org.alexyivan.modelo.entidad.CompraEntidad;
import org.alexyivan.modelo.form.CompraForm;
import org.alexyivan.repositorio.interfaces.ICompraRepo;

import java.util.List;
import java.util.Optional;

public class CompraRepoHibernate implements ICompraRepo {

    @Override
    public Optional<CompraEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario) {
        return Optional.empty();
    }

    @Override
    public Optional<CompraEntidad> crear(CompraForm dto) {
        return Optional.empty();
    }

    @Override
    public Optional<CompraEntidad> obtenerPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<CompraEntidad> actualizar(Long aLong, CompraForm dto) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long aLong) {
        return false;
    }
}
