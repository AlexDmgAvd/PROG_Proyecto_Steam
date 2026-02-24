package org.alexyivan.repositorio.inmemory;

import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.CompraEntidad;
import org.alexyivan.modelo.entidad.EstadisiticasJuegoEntidad;
import org.alexyivan.modelo.form.CompraForm;
import org.alexyivan.repositorio.interfaces.IEstadisticasJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstadisticasJuegoInMemory implements IEstadisticasJuegoRepo {
    private static final List<EstadisiticasJuegoEntidad> estadisticas = new ArrayList<>();
    private static long idCounter = estadisticas.size() + 1;


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
