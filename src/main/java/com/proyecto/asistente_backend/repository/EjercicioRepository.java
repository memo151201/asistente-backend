package com.proyecto.asistente_backend.repository;

import com.proyecto.asistente_backend.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    // Obtener ejercicios activos de un subtema
    List<Ejercicio> findBySubtemaIdAndActivoTrue(Long subtemaId);

    // Obtener todos los ejercicios de un subtema (incluye inactivos)
    List<Ejercicio> findBySubtemaIdOrderByFechaCreacionDesc(Long subtemaId);

    // Obtener ejercicios por tipo
    List<Ejercicio> findByTipo(Ejercicio.TipoEjercicio tipo);

    // Obtener ejercicios por nivel de dificultad
    List<Ejercicio> findByNivelDificultad(Ejercicio.NivelDificultad nivelDificultad);

    // Obtener ejercicios de un subtema por nivel
    List<Ejercicio> findBySubtemaIdAndNivelDificultadAndActivoTrue(
            Long subtemaId,
            Ejercicio.NivelDificultad nivelDificultad
    );

    // Contar ejercicios de un subtema
    Long countBySubtemaId(Long subtemaId);

    // Contar ejercicios activos de un subtema
    Long countBySubtemaIdAndActivoTrue(Long subtemaId);
}