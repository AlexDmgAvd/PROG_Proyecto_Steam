package org.alexyivan.repositorio.inmemory;

import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibliotecaRepoInMemory implements IBibliotecaRepo {
    private static final List<BibliotecaEntidad> bibliotecas = new ArrayList<>();
    private static long idCounter = bibliotecas.size() + 1;


    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm bibliotecaForm) {
        var biblioteca = new BibliotecaEntidad(idCounter++, bibliotecaForm.getUsuarioId(), bibliotecaForm.getJuegoId(),
                bibliotecaForm.getTiempoJuegoTotal(), bibliotecaForm.getUltimaFechaJuego());
        bibliotecas.add(biblioteca);
        return Optional.of(biblioteca);
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {
        return bibliotecas.stream()
                .filter(b -> b.id() == id)
                .findFirst();
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        return new ArrayList<>(bibliotecas);
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm bibliotecaForm) {
        var bibliotecaOpt = obtenerPorId(id);
        if (bibliotecaOpt.isEmpty()) {
            throw new IllegalArgumentException("Biblioteca no encontrada");
        }
        var bibliotecaActualizada = new BibliotecaEntidad(id, bibliotecaForm.getUsuarioId(), bibliotecaForm.getJuegoId(),
                bibliotecaForm.getTiempoJuegoTotal(), bibliotecaForm.getUltimaFechaJuego());
        bibliotecas.removeIf(b -> b.id() == id);
        bibliotecas.add(bibliotecaActualizada);
        return Optional.of(bibliotecaActualizada);
    }

    @Override
    public boolean eliminar(Long id) {
        return bibliotecas.removeIf(b -> b.id() == id);
    }
}
