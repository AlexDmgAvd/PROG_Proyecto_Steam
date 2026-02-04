package Modelo.Dto;

import java.util.List;
import java.util.Optional;

public class CompraDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private enum metodoDePago {TARJETA_CREDITO, PAYPAL, CARTERA_STEAM, TRANSFERENCIA, OTROS};
    private float precioCompleto;
    private int descuentoAplicado;
    private enum estado {COMPLETADO, REEMBOLSADO};

    public CompraDTO(long id, long idUsuario, Optional <UsuarioDTO> usuario, long idJuego,
                     Optional <JuegoDTO> juego, float precioCompleto, int descuentoAplicado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario.orElse(null);
        this.idJuego = idJuego;
        this.juego = juego.orElse(null);
        this.precioCompleto = precioCompleto;
        this.descuentoAplicado = descuentoAplicado;
    }



}
