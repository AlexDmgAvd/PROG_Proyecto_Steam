package org.alexyivan.modelo.form;

import org.alexyivan.modelo.enums.EstadoInstalacionEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BibliotecaForm {
    public static final int CONSTANTE_CERO = 0;
    public static final int CONSTANTE_DIEZ = 10;
    private Long usuarioId;
    private Long juegoId;
    private LocalDate fechaAdquisicion;
    private Float tiempoJuegoTotal;
    private LocalDate ultimaFechaJuego;
    private EstadoInstalacionEnum estadoInstalacion;


    // Constructor
    public BibliotecaForm(Long usuarioId, Long juegoId, LocalDate fechaAdquisicion, Float tiempoJuegoTotal,
                          LocalDate ultimaFechaJuego, EstadoInstalacionEnum estadoInstalacion) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuegoTotal = tiempoJuegoTotal;
        this.ultimaFechaJuego = ultimaFechaJuego;
        this.estadoInstalacion = estadoInstalacion;
    }

    // Getters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getJuegoId() {
        return juegoId;
    }

    public Float getTiempoJuegoTotal() {
        return tiempoJuegoTotal;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public LocalDate getUltimaFechaJuego() {
        return ultimaFechaJuego;
    }

    public EstadoInstalacionEnum getEstadoInstalacion() {
        return estadoInstalacion;
    }


    /**
     * Valida los datos del formulario de biblioteca.
     *
     * @return Lista de errores encontrados durante la validación. Vacía si todos los campos son válidos.
     */
    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();


        // Usuario ID
        if (usuarioId == null) {
            errores.add(new ErrorDto("usuarioId", ErrorType.REQUERIDO));
        }

        // Juego ID
        if (juegoId == null) {
            errores.add(new ErrorDto("juegoId", ErrorType.REQUERIDO));
        }

        // Fecha de adquisición
        if (fechaAdquisicion == null) {
            errores.add(new ErrorDto("fechaAdquisicion", ErrorType.REQUERIDO));
        }
        if (fechaAdquisicion.isAfter(LocalDate.now())) {
            errores.add(new ErrorDto("fechaAdquisicion", ErrorType.FECHA_FUTURA));
        }

        // Tiempo de juego total
        if (tiempoJuegoTotal != null && tiempoJuegoTotal < CONSTANTE_CERO) {
            errores.add(new ErrorDto("tiempoJuegoTotal", ErrorType.VALOR_NEGATIVO));
        }

        if (tiempoJuegoTotal != null) {
            if (Math.round(tiempoJuegoTotal * CONSTANTE_DIEZ) != tiempoJuegoTotal * CONSTANTE_DIEZ) {
                errores.add(new ErrorDto("tiempoJuegoTotal", ErrorType.FORMATO_INVALIDO));
            }
        }

        // Última fecha de juego
        if (ultimaFechaJuego != null) {
            if (ultimaFechaJuego.isAfter(LocalDate.now())) {
                errores.add(new ErrorDto("ultimaFechaJuego", ErrorType.FECHA_FUTURA));
            }
            if (fechaAdquisicion != null && ultimaFechaJuego.isBefore(fechaAdquisicion)) {
                errores.add(new ErrorDto("ultimaFechaJuego", ErrorType.FECHA_FUTURA));
            }
        }


        return errores;
    }
}


