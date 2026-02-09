package Modelo.Form;

public enum ErrorType {

    REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato es inválido"),
    VALOR_DEMASIADO_ALTO("El valor es demasiado alto"),
    VALOR_DEMASIADO_BAJO("El valor es demasiado bajo"),
    NO_ENCONTRADO("No se encontró el elemento"),
    DUPLICADO("El elemento está duplicado"),
    EDAD_INSUFICIENTE("El usuario debe tener al menos 13 años"),
    FECHA_FUTURA("La fecha no puede ser futura"),
    LONGITUD_INVALIDA("La longitud no es válida"),
    EMAIL_INVALIDO("El formato del email no es válido"),
    CONTRASENA_DEBIL("La contraseña no cumple los requisitos de seguridad"),
    PAIS_INVALIDO("El país no es válido"),
    VALOR_NEGATIVO("El valor no puede ser negativo"),
    DESCUENTO_INVALIDO("El descuento debe estar entre 0 y 100"),
    EDAD_CLASIFICACION_INVALIDA("La clasificación por edad no es válida"),
    ESTADO_INVALIDO("El estado no es válido"),
    METODO_PAGO_INVALIDO("El método de pago no es válido"),
    SALDO_INSUFICIENTE("Saldo insuficiente"),
    JUEGO_NO_COMPRABLE("El juego no está disponible para compra"),
    RESENA_DUPLICADA("Ya existe una reseña para este juego"),
    FECHA_ANTERIOR_REGISTRO("La fecha no puede ser anterior al registro del usuario"),
    JUEGO_NO_EN_BIBLIOTECA("El juego no está en la biblioteca del usuario"),
    USUARIO_SIN_PERMISO("El usuario no tiene permiso para realizar esta acción"),
    COMPRA_NO_VERIFICADA("No se encontró una compra verificada para este juego"),
    HORAS_INSUFICIENTES("Horas de juego insuficientes para esta operación"),
    PLAZO_EXPIRADO("El plazo para esta operación ha expirado");

    private final String mensaje;

    private ErrorType(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}