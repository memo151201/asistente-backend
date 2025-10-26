package com.proyecto.asistente_backend.service;


import com.proyecto.asistente_backend.model.Subtema;
import com.proyecto.asistente_backend.service.IAService;
import com.proyecto.asistente_backend.service.SubtemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Generar preguntas dinámicas con IA
     * POST /api/ia/generar-preguntas/{subtemaId}
     */
    @PostMapping("/generar-preguntas/{subtemaId}")
    public ResponseEntity<Map<String, Object>> generarPreguntas(@PathVariable Long subtemaId) {
        try {
            // Obtener subtema
            Subtema subtema = subtemaService.obtenerPorId(subtemaId)
                    .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

            // Generar preguntas con IA
            List<Map<String, Object>> preguntas = iaService.generarPreguntasDinamicas(
                    subtema.getNombre(),
                    subtema.getContenido()
            );

            if (preguntas.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "No se pudieron generar preguntas");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("preguntas", preguntas);
            response.put("subtema", subtema.getNombre());
            response.put("total", preguntas.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al generar preguntas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}