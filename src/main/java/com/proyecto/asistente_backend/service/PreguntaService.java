package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Pregunta;
import com.proyecto.asistente_backend.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    // Crear pregunta
    public Pregunta crearPregunta(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    // Obtener todas las preguntas
    public List<Pregunta> obtenerTodas() {
        return preguntaRepository.findAll();
    }

    // Obtener pregunta por ID
    public Optional<Pregunta> obtenerPorId(Long id) {
        return preguntaRepository.findById(id);
    }

    // Obtener preguntas de un subtema
    public List<Pregunta> obtenerPorSubtema(Long subtemaId) {
        return preguntaRepository.findBySubtemaIdAndActivaTrue(subtemaId);
    }

    // Obtener preguntas por nivel de dificultad
    public List<Pregunta> obtenerPorNivel(Long subtemaId, Pregunta.NivelDificultad nivel) {
        return preguntaRepository.findBySubtemaIdAndNivelDificultadAndActivaTrue(subtemaId, nivel);
    }

    // Obtener preguntas aleatorias
    public List<Pregunta> obtenerPreguntasAleatorias(Long subtemaId, int cantidad) {
        return preguntaRepository.findRandomBySubtemaId(subtemaId, cantidad);
    }

    // Verificar respuesta
    public boolean verificarRespuesta(Long preguntaId, String respuestaUsuario) {
        Pregunta pregunta = preguntaRepository.findById(preguntaId)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        return pregunta.getRespuestaCorrecta().equalsIgnoreCase(respuestaUsuario);
    }

    // Actualizar pregunta
    public Pregunta actualizarPregunta(Long id, Pregunta preguntaActualizada) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

        pregunta.setEnunciado(preguntaActualizada.getEnunciado());
        pregunta.setOpcionA(preguntaActualizada.getOpcionA());
        pregunta.setOpcionB(preguntaActualizada.getOpcionB());
        pregunta.setOpcionC(preguntaActualizada.getOpcionC());
        pregunta.setOpcionD(preguntaActualizada.getOpcionD());
        pregunta.setRespuestaCorrecta(preguntaActualizada.getRespuestaCorrecta());
        pregunta.setExplicacion(preguntaActualizada.getExplicacion());
        pregunta.setNivelDificultad(preguntaActualizada.getNivelDificultad());

        return preguntaRepository.save(pregunta);
    }

    // Desactivar pregunta
    public void desactivarPregunta(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        pregunta.setActiva(false);
        preguntaRepository.save(pregunta);
    }

    // Eliminar pregunta
    public void eliminarPregunta(Long id) {
        preguntaRepository.deleteById(id);
    }
}