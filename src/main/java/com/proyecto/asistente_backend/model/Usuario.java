package com.proyecto.asistente_backend.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolUsuario rol;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo")
    private Boolean activo = true;

    // AGREGAR ESTA ANOTACIÓN ⬇️
    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Retroalimentacion> retroalimentaciones;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    public enum RolUsuario {
        ESTUDIANTE,
        ADMINISTRADOR,
        PROFESOR
    }
}