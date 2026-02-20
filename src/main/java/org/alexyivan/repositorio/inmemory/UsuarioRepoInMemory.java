package org.alexyivan.repositorio.inmemory;

import org.alexyivan.modelo.entidad.UsuarioEntidad;
import org.alexyivan.modelo.form.UsuarioForm;
import org.alexyivan.repositorio.interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepoInMemory implements IUsuarioRepo {

    private static List<UsuarioEntidad> usuarios = new ArrayList<>();
    private static long idCounter = usuarios.size() + 1;


    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm usuarioForm) {
        var usuario = new UsuarioEntidad(idCounter++, usuarioForm.getNombreUsuario(), usuarioForm.getEmail(), usuarioForm.getContrasena(),
                usuarioForm.getNombreReal(), usuarioForm.getPais(), usuarioForm.getFechaNacimiento(), usuarioForm.getFechaRegistro(),
                usuarioForm.getAvatar(), usuarioForm.getSaldo());
        usuarios.add(usuario);
        return Optional.of(usuario);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorId(Long id) {
        return usuarios.stream().filter(u -> u.id() == id).findFirst();
    }
    @Override
    public Optional<UsuarioEntidad> obtenerPorEmail (String email){
        return usuarios.stream().filter(u -> u.email().equals(email)).findFirst();
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Optional<UsuarioEntidad> actualizar(Long id, UsuarioForm usuarioForm) {
        var usuarioOpt = obtenerPorId(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Juego no encontrado");
        }
        var usuarioActualizado = new UsuarioEntidad(id, usuarioForm.getNombreUsuario(), usuarioForm.getEmail(), usuarioForm.getContrasena(),
                usuarioForm.getNombreReal(), usuarioForm.getPais(), usuarioForm.getFechaNacimiento(), usuarioForm.getFechaRegistro(),
                usuarioForm.getAvatar(), usuarioForm.getSaldo());
        usuarios.removeIf(u -> u.id() == id);
        usuarios.add(usuarioActualizado);
        return Optional.of(usuarioActualizado);
    }

    @Override
    public boolean eliminar(Long id) {
        return false;
    }
}
