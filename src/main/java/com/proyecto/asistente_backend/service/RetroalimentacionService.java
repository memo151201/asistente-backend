package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Pregunta;
import com.proyecto.asistente_backend.model.Retroalimentacion;
import com.proyecto.asistente_backend.model.Usuario;
import com.proyecto.asistente_backend.repository.PreguntaRepository;
import com.proyecto.asistente_backend.repository.RetroalimentacionRepository;
import com.proyecto.asistente_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RetroalimentacionService {

    @Autowired
    private RetroalimentacionRepository retroalimentacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private IAService iaService;

    // Obtener todas las retroalimentaciones
    public List<Retroalimentacion> obtenerTodas() {
        return retroalimentacionRepository.findAll();
    }

    // Obtener retroalimentación por ID
    public Optional<Retroalimentacion> obtenerPorId(Long id) {
        return retroalimentacionRepository.findById(id);
    }

    // Obtener historial de un usuario
    public List<Retroalimentacion> obtenerHistorialUsuario(Long usuarioId) {
        return retroalimentacionRepository.findByUsuarioId(usuarioId);
    }

    // Guardar retroalimentación (CON IA)
    public Retroalimentacion guardarRetroalimentacion(
            Long usuarioId,
            Long preguntaId,
            String respuestaUsuario,
            Integer tiempoRespuesta) {

        // Buscar usuario y pregunta
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pregunta pregunta = preguntaRepository.findById(preguntaId)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

        // Validar respuesta
        boolean esCorrecta = respuestaUsuario.equalsIgnoreCase(pregunta.getRespuestaCorrecta());

        // Generar feedback
        String feedback;

        if (esCorrecta) {
            // Si es correcta: mensaje motivacional
            feedback = iaService.generarMensajeMotivacional();
            System.out.println("✅ Respuesta correcta - Mensaje motivacional");
        } else {
            // Si es incorrecta: generar feedback con IA
            System.out.println("🤖 Generando feedback con IA...");

            feedback = iaService.generarRetroalimentacion(
                    pregunta.getEnunciado(),
                    pregunta.getRespuestaCorrecta(),
                    respuestaUsuario,
                    pregunta.getExplicacion()
            );

            System.out.println("🤖 Feedback generado: " + feedback);
        }

        // Crear y guardar retroalimentación
        Retroalimentacion retroalimentacion = new Retroalimentacion();
        retroalimentacion.setUsuario(usuario);
        retroalimentacion.setPregunta(pregunta);
        retroalimentacion.setRespuestaUsuario(respuestaUsuario);
        retroalimentacion.setEsCorrecta(esCorrecta);
        retroalimentacion.setFeedbackIA(feedback);  // ✅ CORRECTO (A mayúscula)
        retroalimentacion.setFechaRespuesta(LocalDateTime.now());
        retroalimentacion.setTiempoRespuesta(tiempoRespuesta);

        return retroalimentacionRepository.save(retroalimentacion);
    }

    // Obtener estadísticas de un usuario
    public EstadisticasUsuario obtenerEstadisticas(Long usuarioId) {
        List<Retroalimentacion> historial = obtenerHistorialUsuario(usuarioId);

        long totalPreguntas = historial.size();
        long correctas = historial.stream().filter(Retroalimentacion::getEsCorrecta).count();
        long incorrectas = totalPreguntas - correctas;

        double porcentajeAcierto = totalPreguntas > 0
                ? (correctas * 100.0 / totalPreguntas)
                : 0.0;

        return new EstadisticasUsuario(totalPreguntas, correctas, incorrectas, porcentajeAcierto);
    }

    // Clase interna para estadísticas
    public static class EstadisticasUsuario {
        private long totalPreguntas;
        private long correctas;
        private long incorrectas;
        private double porcentajeAcierto;

        public EstadisticasUsuario(long totalPreguntas, long correctas,
                                   long incorrectas, double porcentajeAcierto) {
            this.totalPreguntas = totalPreguntas;
            this.correctas = correctas;
            this.incorrectas = incorrectas;
            this.porcentajeAcierto = porcentajeAcierto;
        }

        // Getters
        public long getTotalPreguntas() { return totalPreguntas; }
        public long getCorrectas() { return correctas; }
        public long getIncorrectas() { return incorrectas; }
        public double getPorcentajeAcierto() { return porcentajeAcierto; }
    }
}