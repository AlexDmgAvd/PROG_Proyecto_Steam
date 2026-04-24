package org.alexyivan.modelo.entidad;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "compras")
@Entity
public class CompraEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_usuario")
    private long idUsuario;
    @Column(name = "id_juego")
    private long idJuego;
    @Column(name = "precio_sin_descuento")
    private float precioSinDescuento;
    @Column(name = "descuento_aplicado")
    private int descuentoAplicado;

    // Constructor
    public CompraEntidad(long id, long idUsuario, long idJuego,
                         float precioSinDescuento, int descuentoAplicado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
    }

    // Constructor vacio
    public CompraEntidad(){
    }

    // Constructor sin ID
    public CompraEntidad(long idUsuario, long idJuego, float precioSinDescuento, int descuentoAplicado) {
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public float getPrecioSinDescuento() {
        return precioSinDescuento;
    }

    public void setPrecioSinDescuento(float precioSinDescuento) {
        this.precioSinDescuento = precioSinDescuento;
    }

    public int getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(int descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    @Override
    public String toString() {
        return "CompraEntidad{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", precioSinDescuento=" + precioSinDescuento +
                ", descuentoAplicado=" + descuentoAplicado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompraEntidad that = (CompraEntidad) o;
        return id == that.id && idUsuario == that.idUsuario && idJuego == that.idJuego &&
                Float.compare(precioSinDescuento, that.precioSinDescuento) == 0 &&
                descuentoAplicado == that.descuentoAplicado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idJuego, precioSinDescuento, descuentoAplicado);
    }
}
