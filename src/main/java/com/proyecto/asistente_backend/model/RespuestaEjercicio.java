package com.proyecto.asistente_backend.model;



import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuesta_ejercicio")
@Data
public class RespuestaEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id", nullable = false)
    private Ejercicio ejercicio;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String respuestaUsuario;

    @Column(columnDefinition = "TEXT")
    private String retroalimentacionIA;

    @Enumerated(EnumType.STRING)
    private EstadoEvaluacion estado;

    private Integer puntaje; // 0-100

    private LocalDateTime fechaRespuesta = LocalDateTime.now();

    public enum EstadoEvaluacion {
        CORRECTO, PARCIALMENTE_CORRECTO, INCORRECTO, PENDIENTE
    }
}