package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.dto.EstadisticasBibliotecaDto;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.form.BibliotecaForm;

import java.util.List;
import java.util.Optional;

public interface IBibliotecaControlador {


    List<BibliotecaDto> verBibliotecaPersonal(long id, OrdenBusquedaBibliotecaEnum busquedaBiblioteca);

    Optional<BibliotecaDto> anhadirJuego(BibliotecaForm bibliotecaForm);

    Optional<BibliotecaDto> eliminarJuego(long usuarioId, long juegoId);

    Optional<BibliotecaDto> actualizarTempoJuego(long idUsuario, long idJuego, float horas);

    Optional<BibliotecaDto> consultarUltimaSesion(long idUsuario, long idJuego);

    Optional<EstadisticasBibliotecaDto> estadisticasBiblioteca (long idUsuario);
}
