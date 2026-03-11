package org.alexyivan.modelo.entidad;

import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.PegiEnum;

import java.time.LocalDate;

public record JuegoEntidad(long id, String titulo, String descripcion, String desarrolladora,
                           LocalDate fechaPublicacion, float precioBase, int descuentoActual,
                           String genero, PegiEnum rangoEdad, String idiomasDisponibles, EstadoJuegoEnum estado) {


}
