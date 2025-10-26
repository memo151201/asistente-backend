package com.proyecto.asistente_backend.service;


import com.proyecto.asistente_backend.model.Usuario;
import com.proyecto.asistente_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear usuario
    public Usuario crearUsuario(Usuario usuario) {
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener usuario por email
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Actualizar usuario
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());

        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario (desactivar)
    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    // Obtener usuarios activos
    public List<Usuario> obtenerActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    // Obtener usuarios por rol
    public List<Usuario> obtenerPorRol(Usuario.RolUsuario rol) {
        return usuarioRepository.findByRol(rol);
    }
}