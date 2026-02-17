package org.alexyivan.Modelo.Form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BibliotecaForm {
    private Long usuarioId;
    private Long juegoId;
    private LocalDate fechaAdquisicion;
    private Float tiempoJuegoTotal;
    private LocalDate ultimaFechaJuego;
    private String estadoInstalacion;

    // Constructor
    public BibliotecaForm(Long usuarioId, Long juegoId, LocalDate fechaAdquisicion, Float tiempoJuegoTotal, LocalDate ultimaFechaJuego, String estadoInstalacion) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuegoTotal = tiempoJuegoTotal;
        this.ultimaFechaJuego = ultimaFechaJuego;;
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

    public String getEstadoInstalacion() {
        return estadoInstalacion;
    }


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
        if (tiempoJuegoTotal != null && tiempoJuegoTotal < 0) {
                errores.add(new ErrorDto("tiempoJuegoTotal", ErrorType.VALOR_NEGATIVO));
            }

        if (tiempoJuegoTotal != null ){
            if (Math.round(tiempoJuegoTotal * 10) != tiempoJuegoTotal * 10) {
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


