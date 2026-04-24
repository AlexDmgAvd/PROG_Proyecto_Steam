package org.alexyivan.repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;
import org.alexyivan.transaction.ISesionManager;

import java.util.List;
import java.util.Optional;

public class JuegoRepoHibernate implements IJuegoRepo {

    private final ISesionManager sesionManager;

    public JuegoRepoHibernate(ISesionManager sesionManager) {
        this.sesionManager = sesionManager;
    }

    @Override
    public Optional<JuegoEntidad> obtenerTitulo(String titulo) {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<JuegoEntidad> criteriaQuery = criteriaBuilder.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = criteriaQuery.from(JuegoEntidad.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("titulo"), titulo));

        return  session.createQuery(criteriaQuery).getResultStream().findFirst();
    }

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {

        var session = sesionManager.getSession();
        var juego = new JuegoEntidad(-1, form.getTitulo(), form.getDescripcion(), form.getDesarrolladora(),
                form.getFechaPublicacion(), form.getPrecioBase(), form.getDescuentoActual(), form.getGenero(),
                form.getRangoEdad(), form.getIdiomasDisponibles(), form.getEstado());
        session.persist(juego);

        return Optional.of(juego);
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long id) {

        var session = sesionManager.getSession();

        return Optional.ofNullable(session.find(JuegoEntidad.class, id));
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<JuegoEntidad> criteriaQuery = criteriaBuilder.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = criteriaQuery.from(JuegoEntidad.class);

        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long id, JuegoForm form) {
        var session = sesionManager.getSession();
        var juegoOpt = this.obtenerPorId(id);

        if (juegoOpt.isEmpty())
            return Optional.empty();

        session.merge(new JuegoEntidad(id, form.getTitulo(), form.getDescripcion(),
                form.getDesarrolladora(), form.getFechaPublicacion(),
                form.getPrecioBase(), form.getDescuentoActual(), form.getGenero(),
                form.getRangoEdad(), form.getIdiomasDisponibles(), form.getEstado()));

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {
        var session = sesionManager.getSession();

        var juego = this.obtenerPorId(id);
        if (juego.isEmpty())
            return false;

        session.remove(juego);

        return true;
    }
}
