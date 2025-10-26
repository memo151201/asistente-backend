package com.proyecto.asistente_backend.controller;



import com.proyecto.asistente_backend.dto.AuthResponse;
import com.proyecto.asistente_backend.dto.LoginRequest;
import com.proyecto.asistente_backend.dto.RegisterRequest;
import com.proyecto.asistente_backend.model.Usuario;
import com.proyecto.asistente_backend.repository.UsuarioRepository;
import com.proyecto.asistente_backend.security.JwtTokenProvider;
import com.proyecto.asistente_backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de autenticación - Login, Registro, Perfil
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * POST /api/auth/login
     * Iniciar sesión y obtener token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar token JWT
            String jwt = tokenProvider.generateToken(authentication);

            // Obtener datos del usuario
            Usuario usuario = customUserDetailsService.getUsuarioByEmail(loginRequest.getEmail());

            // Crear respuesta
            AuthResponse response = AuthResponse.builder()
                    .token(jwt)
                    .tipo("Bearer")
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .email(usuario.getEmail())
                    .rol(usuario.getRol().name()) // ⭐ Convertir enum a String
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            error.put("mensaje", "Email o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    /**
     * POST /api/auth/register
     * Registrar nuevo usuario
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Verificar si el email ya existe
            if (usuarioRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Email ya registrado");
                error.put("mensaje", "Ya existe un usuario con este email");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // Validar y convertir rol
            String rolString = registerRequest.getRol();
            if (rolString == null || rolString.isEmpty()) {
                rolString = "ESTUDIANTE"; // Rol por defecto
            }

            Usuario.RolUsuario rol;
            try {
                rol = Usuario.RolUsuario.valueOf(rolString.toUpperCase());
            } catch (IllegalArgumentException e) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Rol inválido");
                error.put("mensaje", "El rol debe ser ESTUDIANTE, PROFESOR o ADMINISTRADOR");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // Crear nuevo usuario
            Usuario usuario = Usuario.builder()
                    .nombre(registerRequest.getNombre())
                    .apellido(registerRequest.getApellido())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .rol(rol) // ⭐ Usar el enum
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();

            usuarioRepository.save(usuario);

            // Generar token JWT para el nuevo usuario
            String jwt = tokenProvider.generateTokenFromEmail(usuario.getEmail());

            // Crear respuesta
            AuthResponse response = AuthResponse.builder()
                    .token(jwt)
                    .tipo("Bearer")
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .email(usuario.getEmail())
                    .rol(usuario.getRol().name()) // ⭐ Convertir enum a String
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar usuario");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/auth/me
     * Obtener datos del usuario autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            Usuario usuario = customUserDetailsService.getUsuarioByEmail(email);

            Map<String, Object> response = new HashMap<>();
            response.put("id", usuario.getId());
            response.put("nombre", usuario.getNombre());
            response.put("apellido", usuario.getApellido());
            response.put("email", usuario.getEmail());
            response.put("rol", usuario.getRol().name()); // ⭐ Convertir enum a String
            response.put("activo", usuario.getActivo());
            response.put("fechaRegistro", usuario.getFechaRegistro());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No autenticado");
            error.put("mensaje", "Token inválido o expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}