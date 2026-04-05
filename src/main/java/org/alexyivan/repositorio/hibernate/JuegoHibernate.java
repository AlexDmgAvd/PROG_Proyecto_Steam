package org.alexyivan.repositorio.hibernate;

import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.form.JuegoForm;
import org.alexyivan.repositorio.interfaces.IJuegoRepo;

import java.util.List;
import java.util.Optional;

public class JuegoHibernate implements IJuegoRepo {

    private ISessionManager sm;

    public JuegoHibernate(ISessionManager sm) {
        this.sm = sm;
    }


    @Override
    public Optional<JuegoEntidad> obtenerTitulo(String titulo) {
        var session = sm.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<JuegoEntidad> cq = cb.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = cq.from(JuegoEntidad.class);

        cq.select(root).where(cb.equal(root.get("titulo"), titulo));

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm juegoForm) {
        var session = sm.getSession();
        var juego = new JuegoEntidad(0, juegoForm.getTitulo(), juegoForm.getDescripcion(),
                juegoForm.getDesarrolladora(), juegoForm.getFechaPublicacion(),
                juegoForm.getPrecioBase(), juegoForm.getDescuentoActual(), juegoForm.getGenero(),
                juegoForm.getRangoEdad(), juegoForm.getIdiomasDisponibles(), juegoForm.getEstado());

        session.persist(juego);

        return Optional.of(juego);


    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long id) {
        var session = sm.getSession();
        return Optional.empty(session.find(JuegiEntidad.class, id));


    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        var session = sm.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<JuegoEntidad> cq = cb.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = cq.from(JuegoEntidad.class);

        cq.select(root).orderBy(cb.asc(root.get("titulo")));

        return session.createQuery(cq).getResultList;

    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long id, JuegoForm juegoForm) {
        var session = sm.getSession();
        var juegoOpt = this.obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            return Optional.empty();
        }

        session.merge(new JuegoEntidad(id, juegoForm.getTitulo(), juegoForm.getDescripcion(),
                juegoForm.getDesarrolladora(), juegoForm.getFechaPublicacion(),
                juegoForm.getPrecioBase(), juegoForm.getDescuentoActual(), juegoForm.getGenero(),
                juegoForm.getRangoEdad(), juegoForm.getIdiomasDisponibles(), juegoForm.getEstado()));

        return obtenerPorId(id);

    }

    @Override
    public boolean eliminar(Long id) {
        var session = sm.getSession();
        var juego = this.obtenerPorId(id);

        if (juego.isEmpty()) {
            return false;
        }

        session.remove(juego);
        return true;
    }
}
