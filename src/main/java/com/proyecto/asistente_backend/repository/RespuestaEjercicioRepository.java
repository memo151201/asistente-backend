package com.proyecto.asistente_backend.repository;

import com.proyecto.asistente_backend.model.RespuestaEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RespuestaEjercicioRepository extends JpaRepository<RespuestaEjercicio, Long> {

    // Obtener respuestas de un usuario
    List<RespuestaEjercicio> findByUsuarioIdOrderByFechaRespuestaDesc(Long usuarioId);

    // Obtener respuestas de un ejercicio específico
    List<RespuestaEjercicio> findByEjercicioId(Long ejercicioId);

    // Obtener respuestas de un usuario para un ejercicio específico
    List<RespuestaEjercicio> findByUsuarioIdAndEjercicioIdOrderByFechaRespuestaDesc(
            Long usuarioId,
            Long ejercicioId
    );

    // Obtener respuestas por estado
    List<RespuestaEjercicio> findByEstado(RespuestaEjercicio.EstadoEvaluacion estado);

    // Contar respuestas de un usuario
    Long countByUsuarioId(Long usuarioId);

    // Obtener promedio de puntaje de un usuario
    @Query("SELECT AVG(r.puntaje) FROM RespuestaEjercicio r WHERE r.usuario.id = :usuarioId")
    Double obtenerPromedioUsuario(@Param("usuarioId") Long usuarioId);

    // Obtener respuestas correctas de un usuario
    Long countByUsuarioIdAndEstado(
            Long usuarioId,
            RespuestaEjercicio.EstadoEvaluacion estado
    );
}