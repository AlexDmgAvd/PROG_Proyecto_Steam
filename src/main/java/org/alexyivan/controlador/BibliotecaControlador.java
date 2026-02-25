package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.JuegoDTO;
import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.enums.EstadoInstalacionENUM;
import org.alexyivan.modelo.enums.OrdenBusquedaBibliotecaENUM;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.modelo.form.CompraForm;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaControlador implements IBibliotecaControlador {

    private final IBibliotecaRepo bibliotecaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;

    public BibliotecaControlador(IBibliotecaRepo bibliotecaRepo, IUsuarioRepo usuarioRepo, IJuegoRepo juegoRepo) {
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
    }

    @Override
    public List<JuegoDTO> verBibliotecaPersonal(long id, OrdenBusquedaBibliotecaENUM busquedaBiblioteca) {

        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(id);

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }


        List<JuegoEntidad> juegosFiltrados;
        List<JuegoEntidad> juegosUsuario;
        juegosFiltrados = juegoRepo.obtenerTodos();
        var biblioteca = bibliotecaRepo;
        var jf = juegosFiltrados.stream();


        juegosUsuario = jf.filter(j -> j.equals()biblioteca.obtenerTodos().)


        return


    }

    @Override
    public boolean anhadirJuego(CompraForm compra) {

        var errores = compra.validar();
        var usuario = usuarioRepo.obtenerPorId(compra.getUsuarioId());
        var juego = juegoRepo.obtenerPorId(compra.getJuegoId());
        var biblioteca = bibliotecaRepo.obtenerTodos();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!biblioteca.stream().filter(b -> b.idJuego() == compra.getJuegoId()).findFirst().isEmpty()
                && biblioteca.stream().filter(b -> b.idUsuario() == compra.getUsuarioId()).findFirst().isPresent()) {

            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));

        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var b = bibliotecaRepo.crear(new BibliotecaForm(compra.getUsuarioId(), compra.getJuegoId(),
                compra.getFechaCompra().toLocalDate(), 0.0f, null,
                EstadoInstalacionENUM.NO_INSTALADO.toString()));
        return true;


    }

    @Override
    public boolean eliminarJuego(long usuarioID, long juegoID) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(usuarioID);
        var juego = juegoRepo.obtenerPorId(juegoID);
        var biblioteca = bibliotecaRepo.obtenerTodos();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (biblioteca.stream().filter(b -> b.idUsuario() == usuarioID).findFirst().isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (biblioteca.stream().filter(b -> b.idJuego() == juegoID).findFirst().isEmpty()
                && biblioteca.stream().filter(b -> b.idUsuario() == usuarioID).findFirst().isPresent()) {

            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));

        }

        List<BibliotecaEntidad> bibliotecaBuscada;
        bibliotecaBuscada = bibliotecaRepo.obtenerTodos();
        var br = bibliotecaBuscada;

        br.stream().filter(b -> b.idUsuario() == usuarioID);
        br.stream().filter(b -> b.idJuego() == juegoID);

        long id = bibliotecaBuscada.getFirst().id();
        bibliotecaRepo.eliminar(id);

        return true;


    }

    @Override
    public float actualizarTempoJuego(long idUsuario, long idJuego, float horas) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(idUsuario);
        var juego = juegoRepo.obtenerPorId(idJuego);


        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (horas < 0) {
            errores.add(new ErrorDto("horas", ErrorType.HORAS_INSUFICIENTES));

        }
        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        var biblioteca = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.idUsuario() == idUsuario
                && b.idJuego() == idJuego).findFirst();


        bibliotecaRepo.actualizar(biblioteca.get().id(), new BibliotecaForm(biblioteca.get().idUsuario(), biblioteca.get().idJuego(),
                biblioteca.get().fechaAdquisicion(), (biblioteca.get().horasJugadasTotal() + horas), biblioteca.get().ultimaFechaDeJuego(),
                biblioteca.get().estadoInstalacion()));

        return biblioteca.get().horasJugadasTotal() + horas;

    }

    @Override
    public String consultarUltimaSesion(long idUsuario, long idJuego) {
        List<ErrorDto> errores = new ArrayList<>();

        var usuario = usuarioRepo.obtenerPorId(idUsuario);
        var juego = juegoRepo.obtenerPorId(idJuego);


        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        var biblioteca = bibliotecaRepo.obtenerTodos().stream().filter(b -> b.idUsuario() == idUsuario
                && b.idJuego() == idJuego).findFirst();

        var ultimaSesion = biblioteca.get().ultimaFechaDeJuego();

        long dias = ChronoUnit.DAYS.between(ultimaSesion, LocalDate.now());

        String salida = "Última sesión" + dias + ultimaSesion;
        return salida;

    }



}
