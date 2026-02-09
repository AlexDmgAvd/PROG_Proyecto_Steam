package Modelo.Form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BibliotecaForm {
    private Long usuarioId;
    private Long juegoId;
    private LocalDateTime fechaAdquisicion;
    private Double tiempoJuegoTotal;
    private LocalDateTime ultimaFechaJuego;
    private String estadoInstalacion;

    // Constructor
    public BibliotecaForm(Long usuarioId, Long juegoId, LocalDateTime fechaAdquisicion, Double tiempoJuegoTotal, LocalDateTime ultimaFechaJuego, String estadoInstalacion) {
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

    public LocalDateTime getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public Double getTiempoJuegoTotal() {
        return tiempoJuegoTotal;
    }

    public LocalDateTime getUltimaFechaJuego() {
        return ultimaFechaJuego;
    }

    public String getEstadoInstalacion() {
        return estadoInstalacion;
    }

    // Estados válidos de instalación
    private static final List<String> ESTADOS_INSTALACION = Arrays.asList(
            "INSTALADO", "NO_INSTALADO"
    );

    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();


        // Usuario ID (requerido)
        if (usuarioId == null) {
            errores.add(new ErrorDto("usuarioId", ErrorType.REQUERIDO));
        }

        // Juego ID (requerido)
        if (juegoId == null) {
            errores.add(new ErrorDto("juegoId", ErrorType.REQUERIDO));
        }

        // Fecha de adquisición
        if (fechaAdquisicion == null) {
            errores.add(new ErrorDto("fechaAdquisicion", ErrorType.REQUERIDO));
        } else if (fechaAdquisicion.isAfter(LocalDateTime.now())) {
            errores.add(new ErrorDto("fechaAdquisicion", ErrorType.FECHA_FUTURA));
        }

        // Tiempo de juego total (opcional)
        if (tiempoJuegoTotal != null) {
            if (tiempoJuegoTotal < 0) {
                errores.add(new ErrorDto("tiempoJuegoTotal", ErrorType.VALOR_NEGATIVO));
            }
        }

        // Última fecha de juego (opcional)
        if (ultimaFechaJuego != null) {
            if (ultimaFechaJuego.isAfter(LocalDateTime.now())) {
                errores.add(new ErrorDto("ultimaFechaJuego", ErrorType.FECHA_FUTURA));
            }
            if (fechaAdquisicion != null && ultimaFechaJuego.isBefore(fechaAdquisicion)) {
                errores.add(new ErrorDto("ultimaFechaJuego", ErrorType.FECHA_FUTURA));
            }
        }

        // Estado de instalación (opcional)
        if (estadoInstalacion != null && !ESTADOS_INSTALACION.contains(estadoInstalacion)) {
            errores.add(new ErrorDto("estadoInstalacion", ErrorType.ESTADO_INVALIDO));
        }

        return errores;
    }
}


