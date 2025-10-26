package com.proyecto.asistente_backend.repository;


import com.proyecto.asistente_backend.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    // Buscar preguntas de un subtema
    List<Pregunta> findBySubtemaId(Long subtemaId);

    // Buscar preguntas activas de un subtema
    List<Pregunta> findBySubtemaIdAndActivaTrue(Long subtemaId);

    // Buscar por nivel de dificultad
    List<Pregunta> findByNivelDificultad(Pregunta.NivelDificultad nivel);

    // Buscar preguntas activas por nivel
    List<Pregunta> findBySubtemaIdAndNivelDificultadAndActivaTrue(
            Long subtemaId,
            Pregunta.NivelDificultad nivel
    );

    // Obtener preguntas aleatorias de un subtema
    @Query(value = "SELECT * FROM preguntas WHERE subtema_id = ?1 AND activa = true ORDER BY RAND() LIMIT ?2",
            nativeQuery = true)
    List<Pregunta> findRandomBySubtemaId(Long subtemaId, int cantidad);
}