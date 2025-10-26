package com.proyecto.asistente_backend.repository;


import com.proyecto.asistente_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe un email
    boolean existsByEmail(String email);

    // Buscar usuarios por rol
    java.util.List<Usuario> findByRol(Usuario.RolUsuario rol);

    // Buscar usuarios activos
    java.util.List<Usuario> findByActivoTrue();
}