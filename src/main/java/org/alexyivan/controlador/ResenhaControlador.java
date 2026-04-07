package org.alexyivan.controlador;


import org.alexyivan.exception.ValidacionException;
import org.alexyivan.mapper.Mapper;
import org.alexyivan.modelo.dto.EstadisticasResenhaDto;
import org.alexyivan.modelo.dto.ResenhaDto;
import org.alexyivan.modelo.enums.EstadoResenhaEnum;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.ErrorType;
import org.alexyivan.modelo.form.ResenhaForm;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.repositorio.interfaces.IResenhaRepo;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResenhaControlador implements IResenhaControlador {

    private final IResenhaRepo resenhaRepo;
    private final IJuegoRepo juegoRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IBibliotecaRepo bibliotecaRepo;


    public ResenhaControlador(IResenhaRepo resenhaRepo, IJuegoRepo juegoRepo,
                              IUsuarioRepo usuarioRepo, IBibliotecaRepo bibliotecaRepo) {
        this.resenhaRepo = resenhaRepo;
        this.juegoRepo = juegoRepo;
        this.usuarioRepo = usuarioRepo;
        this.bibliotecaRepo = bibliotecaRepo;
    }

    @Override
    public Optional<ResenhaDto> escribirResenha(ResenhaForm formularioResenha) throws ValidacionException {
        var errores = formularioResenha.validar();

        var usuario = usuarioRepo.obtenerPorId(formularioResenha.getIdUsuario());
        var juego = juegoRepo.obtenerPorId(formularioResenha.getIdJuego());
        var bibliotecaUsuario = bibliotecaRepo.buscarJuegoUsuario(formularioResenha.getIdJuego(), formularioResenha.getIdUsuario());
        var resenhas = resenhaRepo.obtenerTodos().stream()
                .filter(r -> r.getIdUsuario() == formularioResenha.getIdUsuario())
                .filter(r -> r.getIdJuego() == formularioResenha.getIdJuego())
                .filter(r -> r.getTextoAnalisis().equalsIgnoreCase(formularioResenha.getTextoAnalisis())).findFirst();

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (juego.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (bibliotecaUsuario.isEmpty()) {
            errores.add(new ErrorDto("juego", ErrorType.JUEGO_NO_EN_BIBLIOTECA));
        }
        if (resenhas.isPresent()) {
            errores.add(new ErrorDto("reseña", ErrorType.RESENHA_DUPLICADA));
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }

        var resenha = resenhaRepo.crear(new ResenhaForm(formularioResenha.getIdUsuario(), formularioResenha.getUsuario(),
                formularioResenha.getIdJuego(), formularioResenha.getJuego(), formularioResenha.isRecomendado(),
                formularioResenha.getTextoAnalisis(), formularioResenha.getHorasJugadas(), formularioResenha.getFechaPublicacion(),
                formularioResenha.getUltimaFechaEdicion(), EstadoResenhaEnum.PUBLICADA));

        return Optional.ofNullable(Mapper.mapResenhaEntidadADto(resenha.orElse(null)));
    }

    @Override
    public Optional<ResenhaDto> eliminarResenha(long id, ResenhaForm formularioResenha) throws ValidacionException {
        var errores = formularioResenha.validar();

        var resenha = resenhaRepo.obtenerPorId(id);


        if (resenha.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (resenha.get().getIdUsuario() == formularioResenha.getIdUsuario()){
            errores.add(new ErrorDto("id", ErrorType.RESENHA_NO_PERTENECE));
        }

        if (resenha.get().getEstado() == EstadoResenhaEnum.BORRADA){
            errores.add(new ErrorDto("borrada", ErrorType.RESENHA_YA_BORRADA));
        }

        if (!errores.isEmpty()){
            throw new ValidacionException(errores);
        }

        resenha = resenhaRepo.actualizar(resenha.get().getId(), new ResenhaForm(formularioResenha.getIdUsuario(), formularioResenha.getUsuario(),
                formularioResenha.getIdJuego(), formularioResenha.getJuego(), formularioResenha.isRecomendado(),
                formularioResenha.getTextoAnalisis(), formularioResenha.getHorasJugadas(), formularioResenha.getFechaPublicacion(),
                formularioResenha.getUltimaFechaEdicion(),EstadoResenhaEnum.BORRADA));


        return Optional.ofNullable(Mapper.mapResenhaEntidadADto(resenha.orElse(null)));
    }

    @Override
    public List<ResenhaDto> verResenhasJuego() throws ValidacionException {
        //Todo
        return List.of();
    }

    @Override
    public Optional<ResenhaDto> ocultarResenha(long id, ResenhaForm formularioResenha) throws ValidacionException {
        var errores = formularioResenha.validar();

        var resenha = resenhaRepo.obtenerPorId(id);


        if (resenha.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (resenha.get().getIdUsuario() == formularioResenha.getIdUsuario()){
            errores.add(new ErrorDto("id", ErrorType.RESENHA_NO_PERTENECE));
        }
       if (resenha.get().getEstado() == EstadoResenhaEnum.BORRADA){
           errores.add(new ErrorDto("borrada", ErrorType.RESENHA_YA_BORRADA));

       }
        if (resenha.get().getEstado() == EstadoResenhaEnum.OCULTA){
            errores.add(new ErrorDto("borrada", ErrorType.RESENHA_YA_OCULTA));

        }

        if (!errores.isEmpty()){
            throw new ValidacionException(errores);
        }

        resenha = resenhaRepo.actualizar(resenha.get().getId(), new ResenhaForm(formularioResenha.getIdUsuario(), formularioResenha.getUsuario(),
                formularioResenha.getIdJuego(), formularioResenha.getJuego(), formularioResenha.isRecomendado(),
                formularioResenha.getTextoAnalisis(), formularioResenha.getHorasJugadas(), formularioResenha.getFechaPublicacion(),
                formularioResenha.getUltimaFechaEdicion(),EstadoResenhaEnum.OCULTA));


        return Optional.ofNullable(Mapper.mapResenhaEntidadADto(resenha.orElse(null)));
    }

    @Override
    public Optional<EstadisticasResenhaDto> consultarEstadisticas() throws ValidacionException {
        //Todo
        return Optional.empty();
    }

    @Override
    public List<ResenhaDto> verResenhaUsuario(ResenhaForm formularioResenha, EstadoResenhaEnum estado) {
        var errores = formularioResenha.validar();

        var usuario = usuarioRepo.obtenerPorId(formularioResenha.getIdUsuario());

        if (usuario.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()){
            throw new ValidacionException(errores);
        }

       var resenhas = resenhaRepo.obtenerTodos().stream()
               .filter(r -> r.getIdUsuario() == usuario.get().getId());
        //Todo buscar como mapear una lista completa


        return List.of(resenhas);
    }
}
