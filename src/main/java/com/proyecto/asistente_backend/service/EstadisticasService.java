package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.dto.EstadisticasEstudianteDTO;
import com.proyecto.asistente_backend.model.Usuario;
import com.proyecto.asistente_backend.repository.RespuestaEjercicioRepository;
import com.proyecto.asistente_backend.repository.RetroalimentacionRepository;
import com.proyecto.asistente_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadisticasService {

    private final UsuarioRepository usuarioRepository;
    private final RetroalimentacionRepository retroalimentacionRepository;
    private final RespuestaEjercicioRepository respuestaEjercicioRepository;

    public List<EstadisticasEstudianteDTO> obtenerEstadisticasEstudiantes() {
        List<Usuario> estudiantes = usuarioRepository.findByRol(Usuario.RolUsuario.ESTUDIANTE);
        List<EstadisticasEstudianteDTO> estadisticas = new ArrayList<>();

        for (Usuario estudiante : estudiantes) {
            EstadisticasEstudianteDTO dto = calcularEstadisticas(estudiante);
            estadisticas.add(dto);
        }

        return estadisticas;
    }

    private EstadisticasEstudianteDTO calcularEstadisticas(Usuario usuario) {
        EstadisticasEstudianteDTO dto = new EstadisticasEstudianteDTO();

        dto.setUsuarioId(usuario.getId());
        dto.setNombreCompleto(usuario.getNombre() + " " + usuario.getApellido());
        dto.setEmail(usuario.getEmail());

        // Estadísticas de preguntas (Retroalimentacion)
        Long totalPreguntas = retroalimentacionRepository.countByUsuarioId(usuario.getId());
        Long preguntasCorrectas = retroalimentacionRepository.countByUsuarioIdAndEsCorrectaTrue(usuario.getId());
        dto.setTotalPreguntas(totalPreguntas.intValue());
        dto.setPreguntasCorrectas(preguntasCorrectas.intValue());

        // Estadísticas de ejercicios (RespuestaEjercicio)
        Long totalEjercicios = respuestaEjercicioRepository.countByUsuarioId(usuario.getId());
        Long ejerciciosCorrectos = respuestaEjercicioRepository.countCorrectasByUsuarioId(usuario.getId());
        Integer puntajeTotal = respuestaEjercicioRepository.sumPuntajeByUsuarioId(usuario.getId());

        dto.setTotalEjercicios(totalEjercicios.intValue());
        dto.setEjerciciosCorrectos(ejerciciosCorrectos.intValue());
        dto.setPuntuacionTotal(puntajeTotal != null ? puntajeTotal : 0);

        // Calcular porcentaje general
        int totalRespuestas = totalPreguntas.intValue() + totalEjercicios.intValue();
        int totalCorrectas = preguntasCorrectas.intValue() + ejerciciosCorrectos.intValue();

        if (totalRespuestas > 0) {
            dto.setPorcentajeAciertos((totalCorrectas * 100.0) / totalRespuestas);
        } else {
            dto.setPorcentajeAciertos(0.0);
        }

        return dto;
    }
}