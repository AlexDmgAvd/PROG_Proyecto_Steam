package org.alexyivan.modelo.form;

import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.modelo.enums.EstadoResenhaEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResenhaForm {
    public static final int LONGITUD_MIN_CARACTERES = 50;
    public static final int LONGITUD_MAX_CARACTERES = 8000;
    public static final int CONSTANTE_CERO = 0;
    public static final int CONSTANTE_DIEZ = 10;

    private Long idUsuario;
    private UsuarioDto usuario;
    private Long idJuego;
    private JuegoDto juego;
    private Boolean recomendado;
    private String textoAnalisis;
    private Float horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate ultimaFechaEdicion;
    private EstadoResenhaEnum estado;

    public ResenhaForm(long idUsuario, UsuarioDto usuario, long idJuego, JuegoDto juego,
                       boolean recomendado, String textoAnalisis, float horasJugadas, LocalDate fechaPublicacion,
                       LocalDate ultimaFechaEdicion, EstadoResenhaEnum estado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.recomendado = recomendado;
        this.textoAnalisis = textoAnalisis;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.ultimaFechaEdicion = ultimaFechaEdicion;
        this.estado = estado;
    }

    // Getters
    public long getIdUsuario() {
        return idUsuario;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public JuegoDto getJuego() {
        return juego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public String getTextoAnalisis() {
        return textoAnalisis;
    }

    public float getHorasJugadas() {
        return horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public LocalDate getUltimaFechaEdicion() {
        return ultimaFechaEdicion;
    }

    public EstadoResenhaEnum getEstado() {
        return estado;
    }

    /**
     * Valida los datos del formulario de Reseña.
     *
     * @return Lista de errores encontrados durante la validación. Vacía si todos los campos son válidos.
     */
    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();


        // usuarioId
        if (idUsuario == null) {
            errores.add(new ErrorDto("usuarioId", ErrorType.REQUERIDO));
        }


        // juegoId
        if (idJuego == null) {
            errores.add(new ErrorDto("juegoId", ErrorType.REQUERIDO));
        }


        // recomendado
        if (recomendado == null) {
            errores.add(new ErrorDto("recomendado", ErrorType.REQUERIDO));
        }


        // texto analisis
        if (textoAnalisis == null || textoAnalisis.isBlank()) {
            errores.add(new ErrorDto("textoAnalisis", ErrorType.REQUERIDO));
        }

        if (textoAnalisis != null) {

            if (textoAnalisis.length() < LONGITUD_MIN_CARACTERES) {
                errores.add(new ErrorDto("textoAnalisis", ErrorType.VALOR_DEMASIADO_BAJO));
            }
            if (textoAnalisis.length() > LONGITUD_MAX_CARACTERES) {
                errores.add(new ErrorDto("textoAnalisis", ErrorType.VALOR_DEMASIADO_ALTO));
            }

        }


        // horas jugadas
        if (horasJugadas == null) {
            errores.add(new ErrorDto("horasJugadas", ErrorType.REQUERIDO));
        }

        if (horasJugadas != null) {

            if (horasJugadas < CONSTANTE_CERO) {
                errores.add(new ErrorDto("horasJugadas", ErrorType.VALOR_NEGATIVO));
            }

            if (Math.round(horasJugadas * CONSTANTE_DIEZ) != horasJugadas * CONSTANTE_DIEZ) {
                errores.add(new ErrorDto("horasJugadas", ErrorType.FORMATO_INVALIDO));
            }
        }


        // fecha publicacion
        if (fechaPublicacion == null) {
            errores.add(new ErrorDto("fechaPublicacion", ErrorType.REQUERIDO));
        }

        if (fechaPublicacion != null) {

            if (fechaPublicacion.isAfter(LocalDate.now())) {
                errores.add(new ErrorDto("fechaPublicacion", ErrorType.FECHA_FUTURA));
            }
        }


        // ultima fecha edicion
        if (ultimaFechaEdicion != null) {

            if (ultimaFechaEdicion.isAfter(LocalDate.now())) {
                errores.add(new ErrorDto("ultimaFechaEdicion", ErrorType.FECHA_FUTURA));
            }

            if (fechaPublicacion != null && ultimaFechaEdicion.isBefore(fechaPublicacion)) {
                errores.add(new ErrorDto("ultimaFechaEdicion", ErrorType.FECHA_FUTURA));
            }
        }

        return errores;
    }

}
