package org.alexyivan.Modelo.Entidad;

public record CompraEntidad(long id, long idUsuario, long idJuego,
                            float precioSinDescuento, int descuentoAplicado) {
}
