package org.alexyivan.repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.alexyivan.modelo.entidad.BibliotecaEntidad;
import org.alexyivan.modelo.entidad.CompraEntidad;
import org.alexyivan.modelo.form.CompraForm;
import org.alexyivan.repositorio.interfaces.ICompraRepo;
import org.alexyivan.transaction.ISesionManager;

import java.util.List;
import java.util.Optional;

public class CompraRepoHibernate implements ICompraRepo {

    private final ISesionManager sesionManager;

    public CompraRepoHibernate(ISesionManager sesionManager) {
        this.sesionManager = sesionManager;
    }

    @Override
    public Optional<CompraEntidad> buscarJuegoUsuario(Long idJuego, Long idUsuario) {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CompraEntidad> criteriaQuery = criteriaBuilder.createQuery(CompraEntidad.class);
        Root<CompraEntidad> root = criteriaQuery.from(CompraEntidad.class);

        var predicado1 = criteriaBuilder.equal(root.get("idUsuario"), idUsuario);
        var predicado2 = criteriaBuilder.equal(root.get("idJuego"), idJuego);
        criteriaQuery.select(root).where(criteriaBuilder.and(predicado1,predicado2));

        return session.createQuery(criteriaQuery).getResultStream().findFirst();
    }

    @Override
    public Optional<CompraEntidad> crear(CompraForm form) {

        var session = sesionManager.getSession();
        var compra = new CompraEntidad(0, form.getUsuarioId(), form.getJuegoId(), form.getPrecioSinDescuento(),
                form.getDescuento());
        session.persist(compra);

        return Optional.of(compra);
    }

    @Override
    public Optional<CompraEntidad> obtenerPorId(Long id) {

        var session = sesionManager.getSession();

        return Optional.ofNullable(session.find(CompraEntidad.class, id));
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CompraEntidad> criteriaQuery = criteriaBuilder.createQuery(CompraEntidad.class);
        Root<CompraEntidad> root = criteriaQuery.from(CompraEntidad.class);

        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<CompraEntidad> actualizar(Long id, CompraForm form) {

        var session = sesionManager.getSession();
        var compraOpt = this.obtenerPorId(id);

        if (compraOpt.isEmpty())
            return Optional.empty();

        session.merge(new CompraEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getPrecioSinDescuento(),
                form.getDescuento()));

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {

        var session = sesionManager.getSession();

        var compra = this.obtenerPorId(id);
        if (compra.isEmpty())
            return false;

        session.remove(compra);

        return true;
    }
}
