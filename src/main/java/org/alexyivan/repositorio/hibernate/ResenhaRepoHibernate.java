package org.alexyivan.repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.entidad.ResenhaEntidad;
import org.alexyivan.modelo.form.ResenhaForm;
import org.alexyivan.repositorio.interfaces.IResenhaRepo;
import org.alexyivan.transaction.ISesionManager;

import java.util.List;
import java.util.Optional;

public class ResenhaRepoHibernate implements IResenhaRepo {

    private final ISesionManager sesionManager;

    public ResenhaRepoHibernate(ISesionManager sesionManager) {
        this.sesionManager = sesionManager;
    }

    @Override
    public Optional<ResenhaEntidad> crear(ResenhaForm form) {

        var session = sesionManager.getSession();
        var resenha = new ResenhaEntidad(0, form.getIdUsuario(), form.getIdJuego(), form.isRecomendado(),
                form.getTextoAnalisis(), form.getHorasJugadas(), form.getFechaPublicacion(),
                form.getUltimaFechaEdicion(), form.getEstado());
        session.persist(resenha);

        return Optional.of(resenha);
    }

    @Override
    public Optional<ResenhaEntidad> obtenerPorId(Long id) {

        var session = sesionManager.getSession();

        return Optional.ofNullable(session.find(ResenhaEntidad.class, id));
    }

    @Override
    public List<ResenhaEntidad> obtenerTodos() {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ResenhaEntidad> criteriaQuery = criteriaBuilder.createQuery(ResenhaEntidad.class);
        Root<ResenhaEntidad> root = criteriaQuery.from(ResenhaEntidad.class);

        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<ResenhaEntidad> actualizar(Long id, ResenhaForm form) {

        var session = sesionManager.getSession();
        var resenhaOpt = this.obtenerPorId(id);

        if (resenhaOpt.isEmpty())
            return Optional.empty();

        session.merge(new ResenhaEntidad(id, form.getIdUsuario(), form.getIdJuego(), form.isRecomendado(),
                form.getTextoAnalisis(), form.getHorasJugadas(), form.getFechaPublicacion(),
                form.getUltimaFechaEdicion(), form.getEstado()));


        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {
        var session = sesionManager.getSession();

        var resenha = this.obtenerPorId(id);
        if (resenha.isEmpty())
            return false;

        session.remove(resenha);

        return true;
    }
}
