package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoENUM;
import org.alexyivan.modelo.enums.PegiENUM;

import java.time.LocalDate;

public record JuegoEntidad(long id, String titulo, String descripcion, String desarrolladora,
                           LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                           String genero, PegiENUM rangoEdad, EstadoENUM estado) {




}
