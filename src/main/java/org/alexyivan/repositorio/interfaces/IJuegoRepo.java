package org.alexyivan.repositorio.interfaces;

import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

public interface IJuegoRepo extends ICrud<JuegoEntidad, JuegoForm, Long> {

    Optional<JuegoEntidad> obtenerTitulo (String titulo);

    //texto, categoria, rangoPrecio, clasificacion, estado

    List<Optional<JuegoEntidad>> buscarTexto(String texto);

    List<Optional<JuegoEntidad>> buscarCategoria(String categoria);

    List<Optional<JuegoEntidad>> buscarRangoPrecio(float minPrecio, float maxPrecio);

    List<Optional<JuegoEntidad>> buscarClasificacion(String clasificacion);

    List<Optional<JuegoEntidad>> buscarEstado(String estado);




}
