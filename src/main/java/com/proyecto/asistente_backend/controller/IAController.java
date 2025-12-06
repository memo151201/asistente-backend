package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.dto.EvaluarEjercicioRequest;
import com.proyecto.asistente_backend.model.Contenido;
import com.proyecto.asistente_backend.model.Pregunta;
import com.proyecto.asistente_backend.model.RespuestaEjercicio;
import com.proyecto.asistente_backend.model.Subtema;
import com.proyecto.asistente_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "*")
public class IAController {

    @Autowired
    private IAService iaService;

    @Autowired
    private SubtemaService subtemaService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ContenidoService contenidoService;

    @Autowired
    private PreguntaService preguntaService;

    /**
     * Explicar contenido del subtema con IA
     * POST /api/ia/explicar/{subtemaId}
     */
    @PostMapping("/explicar/{subtemaId}")
    public ResponseEntity<Map<String, String>> explicarContenido(@PathVariable Long subtemaId) {
        try {
            // Obtener subtema
            Subtema subtema = subtemaService.obtenerPorId(subtemaId)
                    .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

            // Generar explicación con IA
            String explicacion = iaService.explicarContenido(
                    subtema.getNombre(),
                    subtema.getContenido()
            );

            Map<String, String> response = new HashMap<>();
            response.put("explicacion", explicacion);
            response.put("subtema", subtema.getNombre());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al generar explicación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Generar ejemplos de código con IA
     * POST /api/ia/ejemplos/{subtemaId}
     */
    @PostMapping("/ejemplos/{subtemaId}")
    public ResponseEntity<Map<String, String>> generarEjemplos(@PathVariable Long subtemaId) {
        try {
            // Obtener subtema
            Subtema subtema = subtemaService.obtenerPorId(subtemaId)
                    .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

            // Generar ejemplos con IA
            String ejemplos = iaService.generarEjemplos(
                    subtema.getNombre(),
                    subtema.getContenido()
            );

            Map<String, String> response = new HashMap<>();
            response.put("ejemplos", ejemplos);
            response.put("subtema", subtema.getNombre());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al generar ejemplos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Generar preguntas dinámicas con IA y GUARDARLAS en BD
     * POST /api/ia/generar-preguntas/{subtemaId}
     */
    @PostMapping("/generar-preguntas/{subtemaId}")
    public ResponseEntity<Map<String, Object>> generarPreguntas(@PathVariable Long subtemaId) {
        try {
            System.out.println("🎯 ========== GENERANDO PREGUNTAS CON CONTENIDOS DE BD ==========");

            // 1️⃣ Obtener subtema
            Subtema subtema = subtemaService.obtenerPorId(subtemaId)
                    .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

            System.out.println("📚 Subtema: " + subtema.getNombre());

            // 2️⃣ Obtener TODOS los contenidos del subtema
            List<Contenido> contenidos = contenidoService.obtenerPorSubtema(subtemaId);

            System.out.println("📖 Contenidos encontrados: " + contenidos.size());

            if (contenidos.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "El subtema no tiene contenidos. Agrega contenidos primero.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // 3️⃣ Concatenar TODO el contenido en un solo texto
            StringBuilder textoCompleto = new StringBuilder();

            for (Contenido contenido : contenidos) {
                textoCompleto.append("=== ").append(contenido.getTitulo()).append(" ===\n\n");
                textoCompleto.append(contenido.getCuerpo()).append("\n\n");
                textoCompleto.append("---\n\n");
            }

            String contenidoParaIA = textoCompleto.toString();

            System.out.println("📝 Texto para IA (" + contenidoParaIA.length() + " caracteres):");
            System.out.println(contenidoParaIA.substring(0, Math.min(300, contenidoParaIA.length())) + "...");

            // 4️⃣ Generar preguntas con IA
            List<Map<String, Object>> preguntasIA = iaService.generarPreguntasDinamicas(
                    subtema.getNombre(),
                    contenidoParaIA
            );

            if (preguntasIA.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "No se pudieron generar preguntas");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }

            // 5️⃣ ✅ GUARDAR LAS PREGUNTAS EN LA BD
            List<Pregunta> preguntasGuardadas = new ArrayList<>();

            for (Map<String, Object> preguntaMap : preguntasIA) {
                Pregunta pregunta = new Pregunta();

                pregunta.setEnunciado((String) preguntaMap.get("enunciado"));
                pregunta.setOpcionA((String) preguntaMap.get("opcionA"));
                pregunta.setOpcionB((String) preguntaMap.get("opcionB"));
                pregunta.setOpcionC((String) preguntaMap.get("opcionC"));
                pregunta.setOpcionD((String) preguntaMap.get("opcionD"));
                pregunta.setRespuestaCorrecta((String) preguntaMap.get("respuestaCorrecta"));
                pregunta.setExplicacion((String) preguntaMap.get("explicacion"));
                pregunta.setNivelDificultad(Pregunta.NivelDificultad.MEDIO);
                pregunta.setSubtema(subtema);
                pregunta.setActiva(true);

                // Guardar en BD
                Pregunta guardada = preguntaService.crearPregunta(pregunta);
                preguntasGuardadas.add(guardada);

                System.out.println("💾 Pregunta guardada: " + guardada.getId());
            }

            System.out.println("✅ Total preguntas guardadas: " + preguntasGuardadas.size());

            Map<String, Object> response = new HashMap<>();
            response.put("preguntas", preguntasGuardadas);
            response.put("subtema", subtema.getNombre());
            response.put("total", preguntasGuardadas.size());
            response.put("contenidosUsados", contenidos.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Error al generar preguntas: " + e.getMessage());
            e.printStackTrace();

            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al generar preguntas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @PostMapping("/evaluar-ejercicio")
    public ResponseEntity<?> evaluarEjercicio(
            @RequestBody EvaluarEjercicioRequest request

    ) {
        try {
            // Extraer usuario del token
            Long usuarioId = 1L;

            RespuestaEjercicio evaluacion = iaService.evaluarEjercicio(
                    request.getEjercicioId(),
                    request.getRespuestaUsuario(),
                    usuarioId
            );

            return ResponseEntity.ok(evaluacion);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
}