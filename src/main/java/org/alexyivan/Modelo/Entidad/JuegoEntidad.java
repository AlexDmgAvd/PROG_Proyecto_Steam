package org.alexyivan.Modelo.Entidad;

import org.alexyivan.Modelo.Enum.EstadoENUM;
import org.alexyivan.Modelo.Enum.PegiENUM;

import java.time.LocalDate;

public record JuegoEntidad(long id, String titulo, String descripcion, String desarrolladora,
                           LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                           String genero, PegiENUM rangoEdad, EstadoENUM estado) {




}
