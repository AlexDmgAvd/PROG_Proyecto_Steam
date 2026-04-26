package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.JuegoDto;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.enums.EstadoJuegoEnum;
import org.alexyivan.modelo.enums.OrdenBusquedaJuegoEnum;
import org.alexyivan.modelo.enums.PegiEnum;
import org.alexyivan.modelo.form.BusquedaJuegosForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.inmemory.JuegoRepoInMemory;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

import java.time.LocalDate;
import java.util.*;

public class JuegoControlador implements IJuegoControlador {

    private final IJuegoRepo juegoRepo;
    //private ItransactionManager tm;
    private static final int DESCUENTO_MIN = 0;
    private static final int DESCUENTO_MAX = 100;

    public JuegoControlador(IJuegoRepo juegoRepo) {
        this.juegoRepo = juegoRepo;
        //this.tm = tm;
    }

    @Override
    public Optional<JuegoDto> anhadirJuegoCatalogo(JuegoForm formulario) throws ValidacionException {

        //Validaciones
        var errores = formulario.validar();

        var juego = juegoRepo.obtenerTitulo(formulario.getTitulo());

        if (juego.isPresent()) {

            errores.add(new ErrorDto("existe", ErrorType.JUEGO_EXISTENTE));
            throw new IllegalStateException();
        }


        //Si la lista de errores no está vacía manda los errores
        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var juegoCreado = juegoRepo.crear(new JuegoForm(formulario.getTitulo(), formulario.getDescripcion(), formulario.getDesarrolladora(),
                formulario.getFechaPublicacion(), formulario.getPrecioBase(), formulario.getDescuentoActual(), formulario.getGenero(),
                formulario.getRangoEdad(), formulario.getIdiomasDisponibles(), formulario.getEstado()));

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoCreado.orElse(null)));
    }

    @Override
    public List<JuegoDto> buscarJuegos(BusquedaJuegosForm busquedaJuegos) throws ValidacionException {


        var errores = busquedaJuegos.validar();

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        List<JuegoEntidad> juegosFiltrados;
        juegosFiltrados = juegoRepo.obtenerTodos();
        var jf = juegosFiltrados.stream();


        if (!busquedaJuegos.getTitulo().isEmpty() || busquedaJuegos.getTitulo() == null) {
            jf.filter(j -> j.getTitulo().contains(busquedaJuegos.getTitulo()));
        }

        if (!busquedaJuegos.getGenero().isEmpty() || busquedaJuegos.getGenero() == null) {
            jf.filter(j -> j.getGenero().equals(busquedaJuegos.getGenero()));

        }
        if (busquedaJuegos.getPrecio() != null) {
            jf.filter(j -> j.getPrecioBase() <= busquedaJuegos.getPrecio());

        }
        if (!busquedaJuegos.getPegi().isEmpty() || busquedaJuegos.getPegi() == null) {
            jf.filter(j -> j.getRangoEdad().toString().equals(busquedaJuegos.getPegi()));
        }

        if (busquedaJuegos.getEstado() == null) {
            jf.filter(j -> j.getEstado().toString().equals(busquedaJuegos.getEstado()));
        }


        return jf.map(Mapper::mapJuegoEntidadADto).toList();
    }

    @Override
    public List<JuegoDto> listarTodosJuegos(OrdenBusquedaJuegoEnum orden) throws ValidacionException {

        List<JuegoEntidad> juegosOriginales;


        if (orden.equals(OrdenBusquedaJuegoEnum.ALFABETICO)) {
            List<JuegoDto> juegosFiltrados = juegoRepo.obtenerTodos().
                    stream().map(Mapper::mapJuegoEntidadADto).toList();
            juegosFiltrados.sort(Comparator.comparing(JuegoDto::getTitulo));

            return juegosFiltrados;
        }


        if (orden.equals(OrdenBusquedaJuegoEnum.PRECIO)) {
            List<JuegoDto> juegosFiltrados = juegoRepo.obtenerTodos().
                    stream().map(Mapper::mapJuegoEntidadADto).toList();
            juegosFiltrados.sort(Comparator.comparing(JuegoDto::getPrecioBase));

            return juegosFiltrados;

        }

        if (orden.equals(OrdenBusquedaJuegoEnum.FECHA)) {
            List<JuegoDto> juegosFiltrados = juegoRepo.obtenerTodos().
                    stream().map(Mapper::mapJuegoEntidadADto).toList();
            juegosFiltrados.sort(Comparator.comparing(JuegoDto::getFechaPublicacion));

            return juegosFiltrados;

        }

        juegosOriginales = juegoRepo.obtenerTodos();
        return juegosOriginales.stream().map(Mapper::mapJuegoEntidadADto).toList();

    }

    @Override
    public Optional<JuegoDto> consultarJuego(long id) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        var juego = juegoRepo.obtenerPorId(id);
        if (juego.isEmpty()) {

            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juego.orElse(null)));
    }

    @Override
    public Optional<JuegoDto> actualizarDescuento(long id, int descuento) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (descuento < DESCUENTO_MIN || descuento > DESCUENTO_MAX) {
            errores.add(new ErrorDto("descuento", ErrorType.DESCUENTO_INVALIDO));

        }

        var juego = juegoRepo.obtenerPorId(id);

        if (juego.isEmpty()) {

            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        var juegoActualizado = juegoRepo.actualizar(id, new JuegoForm(juego.get().getTitulo(), juego.get().getDescripcion(), juego.get().getDesarrolladora(),
                juego.get().getFechaPublicacion(), juego.get().getPrecioBase(), descuento, juego.get().getGenero(),
                juego.get().getRangoEdad(), juego.get().getIdiomasDisponibles(), juego.get().getEstado()));

        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoActualizado.orElse(null)));


    }

    @Override
    public Optional<JuegoDto> cambiarEstado(long id, EstadoJuegoEnum estado) throws ValidacionException {
        List<ErrorDto> errores = new ArrayList<>();

        if (estado == null) {
            errores.add(new ErrorDto("estado", ErrorType.ESTADO_INVALIDO));

        }

        var juego = juegoRepo.obtenerPorId(id);

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));

        }


        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);

        }


        var juegoActualizado = juegoRepo.actualizar(id, new JuegoForm(juego.get().getTitulo(), juego.get().getDescripcion(), juego.get().getDesarrolladora(),
                juego.get().getFechaPublicacion(), juego.get().getPrecioBase(), juego.get().getDescuentoActual(), juego.get().getGenero(),
                juego.get().getRangoEdad(), juego.get().getIdiomasDisponibles(), estado));
        return Optional.ofNullable(Mapper.mapJuegoEntidadADto(juegoActualizado.orElse(null)));
    }

    static void main(String[] args) {
        IJuegoRepo iJuegoRepo = new JuegoRepoInMemory();

        JuegoControlador controlador = new JuegoControlador(iJuegoRepo);

        var juegoCreado = controlador.anhadirJuegoCatalogo(new JuegoForm("Clair Obscure: Expedition 33", "Guía a la expedición 33 en su viaje " +
                "para destruir a la Peintresse para que no pinte la muerte. Explora un mundo inspirado por la Francia de la Belle Époque y " +
                "combate enemigos únicos" + " en este juego de rol por turnos con mecánicas en tiempo real.", "Sandfall Interactive",
                LocalDate.of(2025, 04, 24), 44.99f, 20, "RPG, TBS", PegiEnum.PEGI_18,
                "Español, Francés, Inglés", EstadoJuegoEnum.DISPONIBLE));

        System.out.println(juegoCreado.get().getTitulo() + " " + juegoCreado.get().getDescuentoActual() + juegoCreado.get().getEstado());

// TODO - Preguntar por esta línea Exception in thread "main" java.lang.IllegalStateException:
//  stream has already been operated upon or closed
        //var juegoEntontrados = controlador.buscarJuegos
        //        (new BusquedaJuegosForm("Clair Obscure: Expedition 33", "", null, "", null));
//
        //System.out.println(juegoEntontrados.getFirst().getTitulo());
//
        //TODO - Salta un error relacionado con la inmutabilidad de las listas
        //controlador.listarTodosJuegos(OrdenBusquedaJuegoEnum.PRECIO);

        var juegoConsultado = controlador.consultarJuego(1l);

        System.out.println(juegoConsultado.get().getTitulo());

        var juegoNuevoDescuento = controlador.actualizarDescuento(1l, 15);

        System.out.println(juegoNuevoDescuento.get().getTitulo() + " " + juegoNuevoDescuento.get().getDescuentoActual());

        //TODO - Preguntar si me está mostrando el mismo descuento porque está almacenado en memoria
        System.out.println(juegoCreado.get().getTitulo() + " " + juegoCreado.get().getDescuentoActual());

        var juegoNuevoEstado = controlador.cambiarEstado(1l, EstadoJuegoEnum.NO_DISPONIBLE);

        System.out.println(juegoNuevoEstado.get().getTitulo() + " " + juegoNuevoEstado.get().getEstado());

        try {
            controlador.anhadirJuegoCatalogo(new JuegoForm("Clair Obscure: Expedition 33", "Guía a la expedición 33 en su viaje " +
                    "para destruir a la Peintresse para que no pinte la muerte. Explora un mundo inspirado por la Francia de la Belle Époque y " +
                    "combate enemigos únicos" + " en este juego de rol por turnos con mecánicas en tiempo real.", "Sandfall Interactive",
                    LocalDate.of(2025, 04, 24), 44.99f, 20, "RPG, TBS", PegiEnum.PEGI_18,
                    "Español, Francés, Inglés", EstadoJuegoEnum.DISPONIBLE));
        } catch (IllegalStateException e) {
            System.err.println("Juego ya creado");
        }




    }
}
