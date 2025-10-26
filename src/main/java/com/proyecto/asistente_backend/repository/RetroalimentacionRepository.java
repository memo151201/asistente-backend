package com.proyecto.asistente_backend.repository;

import com.proyecto.asistente_backend.model.Retroalimentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetroalimentacionRepository extends JpaRepository<Retroalimentacion, Long> {

    // Buscar retroalimentaciones de un usuario
    List<Retroalimentacion> findByUsuarioId(Long usuarioId);

    // Buscar retroalimentaciones de una pregunta
    List<Retroalimentacion> findByPreguntaId(Long preguntaId);

    // Buscar respuestas correctas de un usuario
    List<Retroalimentacion> findByUsuarioIdAndEsCorrectaTrue(Long usuarioId);

    // Buscar respuestas incorrectas de un usuario
    List<Retroalimentacion> findByUsuarioIdAndEsCorrectaFalse(Long usuarioId);

    // Contar respuestas correctas de un usuario
    long countByUsuarioIdAndEsCorrectaTrue(Long usuarioId);

    // Contar total de respuestas de un usuario
    long countByUsuarioId(Long usuarioId);

    // Obtener historial de un usuario ordenado por fecha
    List<Retroalimentacion> findByUsuarioIdOrderByFechaRespuestaDesc(Long usuarioId);

    // Calcular promedio de aciertos de un usuario
    @Query("SELECT (COUNT(r) * 100.0 / (SELECT COUNT(r2) FROM Retroalimentacion r2 WHERE r2.usuario.id = ?1)) " +
            "FROM Retroalimentacion r WHERE r.usuario.id = ?1 AND r.esCorrecta = true")
    Double calcularPorcentajeAciertos(Long usuarioId);
}
