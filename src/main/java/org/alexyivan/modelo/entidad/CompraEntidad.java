package org.alexyivan.modelo.entidad;

public record CompraEntidad(long id, long idUsuario, long idJuego,
                            float precioSinDescuento, int descuentoAplicado) {
}
