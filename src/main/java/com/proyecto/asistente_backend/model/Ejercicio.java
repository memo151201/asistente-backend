package com.proyecto.asistente_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ejercicio")
@Data
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String enunciado;

    @Column(columnDefinition = "TEXT")
    private String descripcion; // Instrucciones detalladas

    @Enumerated(EnumType.STRING)
    private TipoEjercicio tipo;

    @Enumerated(EnumType.STRING)
    private NivelDificultad nivelDificultad;

    @Column(columnDefinition = "TEXT")
    private String solucionReferencia; // Solución de ejemplo (opcional)

    @ManyToOne
    @JoinColumn(name = "subtema_id", nullable = false)
    private Subtema subtema;

    private Boolean activo = true;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum TipoEjercicio {
        CODIGO, SQL, PROBLEMA, TEXTO_LIBRE, ANALISIS
    }

    public enum NivelDificultad {
        FACIL, MEDIO, DIFICIL
    }
}