package com.proyecto.asistente_backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "preguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name = "opcion_a", nullable = false, columnDefinition = "TEXT")
    private String opcionA;

    @Column(name = "opcion_b", nullable = false, columnDefinition = "TEXT")
    private String opcionB;

    @Column(name = "opcion_c", nullable = false, columnDefinition = "TEXT")
    private String opcionC;

    @Column(name = "opcion_d", nullable = false, columnDefinition = "TEXT")
    private String opcionD;

    @Column(name = "respuesta_correcta", nullable = false, length = 1)
    private String respuestaCorrecta; // A, B, C, o D

    @Column(columnDefinition = "TEXT")
    private String explicacion; // Explicación de por qué esa respuesta es correcta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelDificultad nivelDificultad;

    @Column(name = "activa")
    private Boolean activa = true;

    // Relación con Subtema
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtema_id", nullable = false)
    private Subtema subtema;

    public enum NivelDificultad {
        FACIL,
        MEDIO,
        DIFICIL
    }
}