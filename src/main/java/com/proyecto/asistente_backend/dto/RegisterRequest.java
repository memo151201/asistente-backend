package com.proyecto.asistente_backend.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registro de nuevo usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol; // Se recibe como String y se convierte a enum
}