package org.alexyivan.repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.BibliotecaForm;
import org.alexyivan.repositorio.interfaces.IBibliotecaRepo;
import org.alexyivan.transaction.ISesionManager;

import java.util.List;
import java.util.Optional;

public class BibliotecaRepoHibernate implements IBibliotecaRepo {

    private final ISesionManager sesionManager;

    public BibliotecaRepoHibernate(ISesionManager sesionManager) {
        this.sesionManager = sesionManager;
    }

    @Override
    public Optional<BibliotecaEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario) {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BibliotecaEntidad> criteriaQuery = criteriaBuilder.createQuery(BibliotecaEntidad.class);
        Root<BibliotecaEntidad> root = criteriaQuery.from(BibliotecaEntidad.class);

        var predicado1 = criteriaBuilder.equal(root.get("idUsuario"), idUsuario);
        var predicado2 = criteriaBuilder.equal(root.get("idJuego"), idJuego);
        criteriaQuery.select(root).where(criteriaBuilder.and(predicado1,predicado2));


        return session.createQuery(criteriaQuery).getResultStream().findFirst();
    }

    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm form) {

        var session = sesionManager.getSession();
        var biblioteca = new BibliotecaEntidad(-1, form.getUsuarioId(), form.getJuegoId(),
                form.getTiempoJuegoTotal(), form.getUltimaFechaJuego(), form.getFechaAdquisicion(),
                form.getEstadoInstalacion());
        session.persist(biblioteca);

        return Optional.of(biblioteca);
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {

        var session = sesionManager.getSession();

        return Optional.ofNullable(session.find(BibliotecaEntidad.class, id));
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BibliotecaEntidad> criteriaQuery = criteriaBuilder.createQuery(BibliotecaEntidad.class);
        Root<BibliotecaEntidad> root = criteriaQuery.from(BibliotecaEntidad.class);

        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm form) {

        var session = sesionManager.getSession();
        var bibliotecaOpt = this.obtenerPorId(id);

        if (bibliotecaOpt.isEmpty())
            return Optional.empty();

        session.merge(new BibliotecaEntidad(id, form.getUsuarioId(), form.getJuegoId(),
                form.getTiempoJuegoTotal(), form.getUltimaFechaJuego(), form.getFechaAdquisicion(),
                form.getEstadoInstalacion()));

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {

        var session = sesionManager.getSession();

        var biblioteca = this.obtenerPorId(id);
        if (biblioteca.isEmpty())
            return false;

        session.remove(biblioteca);

        return true;
    }
}
