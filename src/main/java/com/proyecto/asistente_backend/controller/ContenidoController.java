package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.model.Contenido;
import com.proyecto.asistente_backend.service.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@CrossOrigin(origins = "*")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    // GET: Obtener todos los contenidos
    @GetMapping
    public ResponseEntity<List<Contenido>> obtenerTodos() {
        return ResponseEntity.ok(contenidoService.obtenerTodos());
    }

    // GET: Obtener contenido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contenido> obtenerPorId(@PathVariable Long id) {
        return contenidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener contenidos de un subtema (solo activos)
    @GetMapping("/subtema/{subtemaId}")
    public ResponseEntity<List<Contenido>> obtenerPorSubtema(@PathVariable Long subtemaId) {
        return ResponseEntity.ok(contenidoService.obtenerPorSubtema(subtemaId));
    }

    // GET: Obtener TODOS los contenidos de un subtema (incluye inactivos)
    @GetMapping("/subtema/{subtemaId}/todos")
    public ResponseEntity<List<Contenido>> obtenerTodosPorSubtema(@PathVariable Long subtemaId) {
        return ResponseEntity.ok(contenidoService.obtenerTodosPorSubtema(subtemaId));
    }

    // GET: Obtener texto completo para IA
    @GetMapping("/subtema/{subtemaId}/completo")
    public ResponseEntity<String> obtenerContenidoCompleto(@PathVariable Long subtemaId) {
        String contenido = contenidoService.obtenerContenidoCompletoParaIA(subtemaId);
        return ResponseEntity.ok(contenido);
    }

    // POST: Crear contenido
    @PostMapping
    public ResponseEntity<Contenido> crearContenido(@RequestBody Contenido contenido) {
        Contenido nuevoContenido = contenidoService.crearContenido(contenido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoContenido);
    }

    // PUT: Actualizar contenido
    @PutMapping("/{id}")
    public ResponseEntity<Contenido> actualizarContenido(@PathVariable Long id, @RequestBody Contenido contenido) {
        try {
            Contenido contenidoActualizado = contenidoService.actualizarContenido(id, contenido);
            return ResponseEntity.ok(contenidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Desactivar contenido (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarContenido(@PathVariable Long id) {
        try {
            contenidoService.desactivarContenido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar permanentemente
    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> eliminarContenido(@PathVariable Long id) {
        try {
            contenidoService.eliminarContenido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}