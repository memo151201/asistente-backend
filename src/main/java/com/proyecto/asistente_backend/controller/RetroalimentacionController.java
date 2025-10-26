package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.model.Retroalimentacion;
import com.proyecto.asistente_backend.service.RetroalimentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retroalimentaciones")
@CrossOrigin(origins = "*")
public class RetroalimentacionController {

    @Autowired
    private RetroalimentacionService retroalimentacionService;

    // GET: Obtener todas las retroalimentaciones
    @GetMapping
    public ResponseEntity<List<Retroalimentacion>> obtenerTodas() {
        return ResponseEntity.ok(retroalimentacionService.obtenerTodas());
    }

    // GET: Obtener retroalimentación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Retroalimentacion> obtenerPorId(@PathVariable Long id) {
        return retroalimentacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener historial de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Retroalimentacion>> obtenerHistorialUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(retroalimentacionService.obtenerHistorialUsuario(usuarioId));
    }

    // GET: Obtener estadísticas de un usuario
    @GetMapping("/usuario/{usuarioId}/estadisticas")
    public ResponseEntity<RetroalimentacionService.EstadisticasUsuario> obtenerEstadisticas(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(retroalimentacionService.obtenerEstadisticas(usuarioId));
    }

    // POST: Guardar retroalimentación (responder pregunta)
    @PostMapping
    public ResponseEntity<Retroalimentacion> guardarRetroalimentacion(@RequestBody Map<String, Object> body) {
        Long usuarioId = Long.valueOf(body.get("usuarioId").toString());
        Long preguntaId = Long.valueOf(body.get("preguntaId").toString());
        String respuestaUsuario = body.get("respuesta").toString();
        Integer tiempoRespuesta = body.get("tiempoRespuesta") != null
                ? Integer.valueOf(body.get("tiempoRespuesta").toString())
                : null;

        Retroalimentacion retroalimentacion = retroalimentacionService.guardarRetroalimentacion(
                usuarioId, preguntaId, respuestaUsuario, tiempoRespuesta);

        return ResponseEntity.status(HttpStatus.CREATED).body(retroalimentacion);
    }
}