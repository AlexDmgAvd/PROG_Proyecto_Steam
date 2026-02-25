package org.alexyivan.controlador;

import org.alexyivan.modelo.dto.BibliotecaDTO;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaENUM;
import org.alexyivan.modelo.form.CompraForm;

import java.util.List;
import java.util.Optional;

public interface IBibliotecaControlador {


    List<BibliotecaDTO> verBibliotecaPersonal(long id, OrdenBusquedaBibliotecaENUM busquedaBiblioteca);

    boolean anhadirJuego(CompraForm compra);

    boolean eliminarJuego(long usuarioID, long juegoID);

    float actualizarTempoJuego(long idUsuario, long idJuego, float horas);

    String consultarUltimaSesion(long idUsuario, long idJuego);
}
