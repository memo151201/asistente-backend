package com.proyecto.asistente_backend.dto;




import com.proyecto.asistente_backend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para responder con el token JWT y datos del usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol; // Se envía como String
}

