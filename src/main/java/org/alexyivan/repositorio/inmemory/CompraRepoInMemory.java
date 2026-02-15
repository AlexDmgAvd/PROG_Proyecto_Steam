package org.alexyivan.repositorio.inmemory;

import org.alexyivan.Modelo.Entidad.BibliotecaEntidad;
import org.alexyivan.Modelo.Entidad.CompraEntidad;
import org.alexyivan.Modelo.Form.CompraForm;
import org.alexyivan.repositorio.interfaces.ICompraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraRepoInMemory implements ICompraRepo {

    private static final List<CompraEntidad> compras = new ArrayList<>();
    private static long idCounter = compras.size() + 1;

    @Override
    public Optional<CompraEntidad> crear(CompraForm compraForm) {
        var compra = new CompraEntidad(idCounter++,compraForm.getUsuarioId(), compraForm.getJuegoId(),
                compraForm.getPrecioSinDescuento(), compraForm.getDescuento());
        compras.add(compra);
        return Optional.of(compra);
    }

    @Override
    public Optional<CompraEntidad> obtenerPorId(Long id) {
        return compras.stream()
                .filter(c -> c.id() == id)
                .findFirst();
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {
        return new ArrayList<>(compras);
    }

    @Override
    public Optional<CompraEntidad> actualizar(Long id, CompraForm compraForm) {
        var compraOpt = obtenerPorId(id);
        if (compraOpt.isEmpty()) {
            throw new IllegalArgumentException("Compra no encontrada");
        }
        var compraActualizada = new CompraEntidad(id, compraForm.getUsuarioId(), compraForm.getJuegoId(),
                compraForm.getPrecioSinDescuento(), compraForm.getDescuento());
        compras.removeIf(c -> c.id() == id);
        compras.add(compraActualizada);
        return Optional.of(compraActualizada);
    }

    @Override
    public boolean eliminar(Long id) {
        return compras.removeIf(c -> c.id() == id);
    }
}
