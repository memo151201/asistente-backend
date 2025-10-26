package com.proyecto.asistente_backend.repository;

import com.proyecto.asistente_backend.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    // Buscar materia por nombre
    Optional<Materia> findByNombre(String nombre);

    // Buscar materia por código
    Optional<Materia> findByCodigo(String codigo);

    // Buscar materias activas
    List<Materia> findByActivaTrue();

    // Buscar por nivel de dificultad
    List<Materia> findByNivelDificultad(String nivelDificultad);
}