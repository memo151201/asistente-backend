package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.model.Pregunta;
import com.proyecto.asistente_backend.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preguntas")
@CrossOrigin(origins = "*")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    // GET: Obtener todas las preguntas
    @GetMapping
    public ResponseEntity<List<Pregunta>> obtenerTodas() {
        return ResponseEntity.ok(preguntaService.obtenerTodas());
    }

    // GET: Obtener pregunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pregunta> obtenerPorId(@PathVariable Long id) {
        return preguntaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener preguntas por subtema
    @GetMapping("/subtema/{subtemaId}")
    public ResponseEntity<List<Pregunta>> obtenerPorSubtema(@PathVariable Long subtemaId) {
        return ResponseEntity.ok(preguntaService.obtenerPorSubtema(subtemaId));
    }

    // GET: Obtener preguntas por nivel
    @GetMapping("/subtema/{subtemaId}/nivel/{nivel}")
    public ResponseEntity<List<Pregunta>> obtenerPorNivel(
            @PathVariable Long subtemaId,
            @PathVariable String nivel) {
        Pregunta.NivelDificultad nivelDificultad = Pregunta.NivelDificultad.valueOf(nivel.toUpperCase());
        return ResponseEntity.ok(preguntaService.obtenerPorNivel(subtemaId, nivelDificultad));
    }

    // GET: Obtener preguntas aleatorias
    @GetMapping("/subtema/{subtemaId}/aleatorias")
    public ResponseEntity<List<Pregunta>> obtenerAleatorias(
            @PathVariable Long subtemaId,
            @RequestParam(defaultValue = "5") int cantidad) {
        return ResponseEntity.ok(preguntaService.obtenerPreguntasAleatorias(subtemaId, cantidad));
    }

    // POST: Verificar respuesta
    @PostMapping("/{id}/verificar")
    public ResponseEntity<Map<String, Object>> verificarRespuesta(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String respuestaUsuario = body.get("respuesta");
        boolean esCorrecta = preguntaService.verificarRespuesta(id, respuestaUsuario);

        return ResponseEntity.ok(Map.of(
                "correcta", esCorrecta,
                "mensaje", esCorrecta ? "¡Respuesta correcta!" : "Respuesta incorrecta"
        ));
    }

    // POST: Crear pregunta
    @PostMapping
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nuevaPregunta = preguntaService.crearPregunta(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPregunta);
    }

    // PUT: Actualizar pregunta
    @PutMapping("/{id}")
    public ResponseEntity<Pregunta> actualizarPregunta(@PathVariable Long id, @RequestBody Pregunta pregunta) {
        try {
            Pregunta preguntaActualizada = preguntaService.actualizarPregunta(id, pregunta);
            return ResponseEntity.ok(preguntaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Desactivar pregunta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPregunta(@PathVariable Long id) {
        try {
            preguntaService.desactivarPregunta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}