package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Usuario;
import com.proyecto.asistente_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * Servicio personalizado para cargar detalles del usuario desde la base de datos
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            throw new UsernameNotFoundException("Usuario inactivo: " + email);
        }

        // Crear los authorities (roles) del usuario
        Collection<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()) // ⭐ Usar .name()
        );

        // Retornar UserDetails de Spring Security
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getActivo(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities
        );
    }

    /**
     * Método auxiliar para obtener el usuario completo
     */
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }
}