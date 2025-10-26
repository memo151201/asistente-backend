package com.proyecto.asistente_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "subtemas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subtema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "orden")
    private Integer orden;

    // Relación con Tema (SOLO UNA VEZ)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id", nullable = false)
    private Tema tema;

    // Relación con Pregunta (SOLO UNA VEZ)
    @JsonManagedReference
    @OneToMany(mappedBy = "subtema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;
}