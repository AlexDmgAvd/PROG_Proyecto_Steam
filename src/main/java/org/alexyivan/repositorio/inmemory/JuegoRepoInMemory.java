package org.alexyivan.repositorio.inmemory;

import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoRepoInMemory implements IJuegoRepo {
    private static List<JuegoEntidad> juegos = new ArrayList<>();
    private static long idCounter = juegos.size() + 1;

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm juegoForm) {
        var juego = new JuegoEntidad(idCounter++, juegoForm.getTitulo(), juegoForm.getDescripcion(), juegoForm.getDesarrolladora(), juegoForm.getFechaPublicacion(),
                juegoForm.getPrecioBase(), juegoForm.getDescuentoActual(), juegoForm.getGenero(), juegoForm.getRangoEdad(), juegoForm.getEstado());
        juegos.add(juego);
        return Optional.of(juego);
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long id) {
        return juegos.stream()
                .filter(j -> j.id() == id)
                .findFirst();
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        return new ArrayList<>(juegos);
    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long id, JuegoForm juegoForm) {
        var juegosOpt = obtenerPorId(id);
        if (juegosOpt.isEmpty()) {
            throw new IllegalArgumentException("Juego no encontrado");
        }
var juegoActualizado = new JuegoEntidad(id,juegoForm.getTitulo(), juegoForm.getDescripcion(),
        juegoForm.getDesarrolladora(), juegoForm.getFechaPublicacion(),
        juegoForm.getPrecioBase(), juegoForm.getDescuentoActual(), juegoForm.getGenero(),
        juegoForm.getRangoEdad(), juegoForm.getEstado());
        juegos.removeIf(j -> j.id()== id);
        juegos.add(juegoActualizado);
        return Optional.of(juegoActualizado);

    }

    @Override
    public boolean eliminar(Long id) {
        return juegos.removeIf(j -> j.id() == id);
    }
}
