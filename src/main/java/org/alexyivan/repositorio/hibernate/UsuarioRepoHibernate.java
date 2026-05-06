package org.alexyivan.repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.alexyivan.modelo.entidad.JuegoEntidad;
import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.form.UsuarioForm;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;
import org.alexyivan.transaction.ISesionManager;

import java.util.List;
import java.util.Optional;

public class  UsuarioRepoHibernate implements IUsuarioRepo {

    private final ISesionManager sesionManager;

    public UsuarioRepoHibernate(ISesionManager sesionManager) {
        this.sesionManager = sesionManager;
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorEmail(String email) {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = criteriaQuery.from(UsuarioEntidad.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("email"), email));

        return  session.createQuery(criteriaQuery).getResultStream().findFirst();
    }



    @Override
    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombre) {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = criteriaQuery.from(UsuarioEntidad.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("nombre"), nombre));

        return  session.createQuery(criteriaQuery).getResultStream().findFirst();
    }

    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm form) {

        var session = sesionManager.getSession();
        var usuario = new UsuarioEntidad(0, form.getNombreUsuario(), form.getEmail(), form.getContrasena(),
                form.getNombreReal(), form.getPais(), form.getFechaNacimiento(), form.getFechaRegistro(),
                form.getAvatar(), form.getSaldo(), form.getEstado());
        session.persist(usuario);
        return Optional.of(usuario);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorId(Long id) {

        var session = sesionManager.getSession();

        return Optional.ofNullable(session.find(UsuarioEntidad.class, id));
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {

        var session = sesionManager.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = criteriaQuery.from(UsuarioEntidad.class);

        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<UsuarioEntidad> actualizar(Long id, UsuarioForm form) {

        var session = sesionManager.getSession();
        var usuarioOpt = this.obtenerPorId(id);

        if (usuarioOpt.isEmpty())
            return Optional.empty();

        session.merge(new UsuarioEntidad(id, form.getNombreUsuario(), form.getEmail(),
                form.getContrasena(), form.getNombreReal(),
                form.getPais(), form.getFechaNacimiento(), form.getFechaRegistro(),
                form.getAvatar(), form.getSaldo(), form.getEstado()));

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {
        var session = sesionManager.getSession();

        var usuario = this.obtenerPorId(id);
        if (usuario.isEmpty())
            return false;

        session.remove(usuario);

        return true;
    }
}
