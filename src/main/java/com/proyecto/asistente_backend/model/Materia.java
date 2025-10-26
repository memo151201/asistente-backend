package com.proyecto.asistente_backend.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "materias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;


    @Column(length = 50)
    private String codigo;

    @Column(name = "nivel_dificultad", length = 20)
    private String nivelDificultad; // BASICO, INTERMEDIO, AVANZADO

    @Column(name = "activa")
    private Boolean activa = true;

    // Relación con Tema
    @JsonManagedReference  // ✅ evita recursión infinita
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tema> temas;
}
