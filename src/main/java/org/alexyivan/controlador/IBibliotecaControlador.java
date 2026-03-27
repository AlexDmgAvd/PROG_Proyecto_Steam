package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.BibliotecaDto;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaEnum;
import org.alexyivan.modelo.form.CompraForm;

import java.util.List;
import java.util.Optional;

public interface IBibliotecaControlador {


    List<BibliotecaDto> verBibliotecaPersonal(long id, OrdenBusquedaBibliotecaEnum busquedaBiblioteca);

    Optional<BibliotecaDto> anhadirJuego(CompraForm compra);

    Optional<BibliotecaDto> eliminarJuego(long usuarioId, long juegoId);

    float actualizarTempoJuego(long idUsuario, long idJuego, float horas);

    String consultarUltimaSesion(long idUsuario, long idJuego);
}
