package com.proyecto.asistente_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "temas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "orden")
    private Integer orden; // Para ordenar los temas dentro de una materia
    @JsonBackReference  // ✅ Evita la recursión infinita con Materia
    // 🔁 Relación con Materia (padre)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    // 🔁 Relación con Subtema (hijos)
    @JsonManagedReference  // ✅ Controla la serialización hacia los subtemas
    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Subtema> subtemas;
}
