package Modelo.Dto;

import Modelo.Enum.EstadoENUM;
import Modelo.Enum.PegiENUM;

import java.time.LocalDate;
import java.util.List;

public class JuegoDTO {
    private long id;
    private String titulo;
    private String descripcion;
    private String desarrolladora;
    private LocalDate fechaPublicacion;
    private float precioBase;
    private int descuentoActual;
    private String genero;
    private PegiENUM rangoEdad;
    private EstadoENUM estado;

    public JuegoDTO(long id, String titulo, String descripcion, String desarrolladora,
                    LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                    String genero, PegiENUM rangoEdad, EstadoENUM estado) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrolladora = desarrolladora;
        this.fechaPublicacion = fechaPublicacion;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.genero = genero;
        this.rangoEdad = rangoEdad;
        this.estado = estado;
    }
}
