package com.proyecto.asistente_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Table(name = "retroalimentaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Retroalimentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // AGREGAR ESTA ANOTACIÓN ⬇️
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    @Column(name = "respuesta_usuario", nullable = false, length = 1)
    private String respuestaUsuario;

    @Column(name = "es_correcta", nullable = false)
    private Boolean esCorrecta;

    @Column(columnDefinition = "TEXT")
    private String feedbackIA;

    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    @Column(name = "tiempo_respuesta")
    private Integer tiempoRespuesta;

    @PrePersist
    protected void onCreate() {
        fechaRespuesta = LocalDateTime.now();
    }
}