package org.alexyivan.controlador;


import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.EstadisticasResenhaDTO;
import org.alexyivan.modelo.dto.ResenhaDto;
import org.alexyivan.repositorio.interfaces.IResenhaRepo;

import java.util.List;
import java.util.Optional;

public class ResenhaControlador implements IResenhaControlador {

    private final IResenhaRepo resenhaRepo;

    public ResenhaControlador(IResenhaRepo resenhaRepo) {
        this.resenhaRepo = resenhaRepo;
    }

    @Override
    public Optional<ResenhaDto> escribirResenha() throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public boolean eliminarResenha() throws ValidacionException {
        return false;
    }

    @Override
    public List<ResenhaDto> verResenhasJuego() throws ValidacionException {
        return List.of();
    }

    @Override
    public boolean ocultarResenha(long idUsuario, long idResenha) throws ValidacionException {
        return false;
    }

    @Override
    public Optional<EstadisticasResenhaDTO> consultarEstadisticas() throws ValidacionException {
        return Optional.empty();
    }

    @Override
    public List<ResenhaDto> verResenhaUsuario(long idUsuario) {
        return List.of();
    }
}
