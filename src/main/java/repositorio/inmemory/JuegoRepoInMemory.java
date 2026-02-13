package repositorio.inmemory;

import Modelo.Entidad.JuegoEntidad;
import Modelo.Form.JuegoForm;
import repositorio.interfaces.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoRepoInMemory implements IJuegoRepo {
    private static List<JuegoEntidad> juegos = new ArrayList<>();
    private static long idCounter = juegos.size() + 1;

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm dto) {
        return Optional.empty();
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long aLong, JuegoForm dto) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Long aLong) {
        return false;
    }
}
