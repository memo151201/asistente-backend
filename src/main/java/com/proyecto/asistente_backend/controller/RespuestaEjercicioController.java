package com.proyecto.asistente_backend.controller;


import com.proyecto.asistente_backend.model.RespuestaEjercicio;
import com.proyecto.asistente_backend.service.RespuestaEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@CrossOrigin(origins = "*")
public class RespuestaEjercicioController {

    @Autowired
    private RespuestaEjercicioService respuestaEjercicioService;

    // Ver respuestas de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RespuestaEjercicio>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(respuestaEjercicioService.obtenerPorUsuario(usuarioId));
    }

    // Ver respuestas de un ejercicio
    @GetMapping("/ejercicio/{ejercicioId}")
    public ResponseEntity<List<RespuestaEjercicio>> obtenerPorEjercicio(@PathVariable Long ejercicioId) {
        return ResponseEntity.ok(respuestaEjercicioService.obtenerPorEjercicio(ejercicioId));
    }

    // Ver una respuesta específica
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaEjercicio> obtenerPorId(@PathVariable Long id) {
        return respuestaEjercicioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ver todas las respuestas
    @GetMapping
    public ResponseEntity<List<RespuestaEjercicio>> obtenerTodas() {
        return ResponseEntity.ok(respuestaEjercicioService.obtenerTodas());
    }
}