package org.alexyivan.repositorio.inmemory;

import org.alexyivan.Modelo.Entidad.JuegoEntidad;
import org.alexyivan.Modelo.Entidad.ResenhaEntidad;
import org.alexyivan.Modelo.Form.JuegoForm;
import org.alexyivan.Modelo.Form.ResenhaForm;
import org.alexyivan.repositorio.interfaces.IResenhaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResenhaRepoInMemory implements IResenhaRepo {

    private static List<ResenhaEntidad> resenhas = new ArrayList<>();
    private static long idCounter = resenhas.size() + 1;


    @Override
    public Optional<ResenhaEntidad> crear(ResenhaForm resenhaForm) {
        var resenha = new ResenhaEntidad(idCounter++, resenhaForm.getIdUsuario(), resenhaForm.getIdJuego(), resenhaForm.isRecomendado(),
                resenhaForm.getTextoAnalisis(), resenhaForm.getHorasJugadas(), resenhaForm.getFechaPublicacion(), resenhaForm.getUltimaFechaEdicion());
        resenhas.add(resenha);
        return Optional.of(resenha);

    }

    @Override
    public Optional<ResenhaEntidad> obtenerPorId(Long id) {
        return resenhas.stream()
                .filter(r -> r.id() == id)
                .findFirst();
    }

    @Override
    public List<ResenhaEntidad> obtenerTodos() {
        return new ArrayList<>(resenhas);
    }

    @Override
    public Optional<ResenhaEntidad> actualizar(Long id, ResenhaForm resenhaForm) {

        var resenhaOpt = obtenerPorId(id);
        if (resenhaOpt.isEmpty()) {
            throw new IllegalArgumentException("Juego no encontrado");
        }
        var resenhaActualizada = new ResenhaEntidad(id, resenhaForm.getIdUsuario(), resenhaForm.getIdJuego(),
                resenhaForm.isRecomendado(), resenhaForm.getTextoAnalisis(), resenhaForm.getHorasJugadas(),
                resenhaForm.getFechaPublicacion(), resenhaForm.getUltimaFechaEdicion());
        resenhas.removeIf(r -> r.id() == id);
        resenhas.add(resenhaActualizada);
        return Optional.of(resenhaActualizada);
    }

    @Override
    public boolean eliminar(Long id) {
        return resenhas.removeIf(r -> r.id() == id);
    }
}
