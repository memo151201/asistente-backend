package com.proyecto.asistente_backend.controller;


import com.proyecto.asistente_backend.model.Ejercicio;
import com.proyecto.asistente_backend.service.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
@CrossOrigin(origins = "*")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    // POST: Admin crea ejercicio
    @PostMapping
    public ResponseEntity<Ejercicio> crearEjercicio(@RequestBody Ejercicio ejercicio) {
        Ejercicio nuevoEjercicio = ejercicioService.crearEjercicio(ejercicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEjercicio);
    }

    // GET: Obtener todos los ejercicios
    @GetMapping
    public ResponseEntity<List<Ejercicio>> obtenerTodos() {
        return ResponseEntity.ok(ejercicioService.obtenerTodos());
    }

    // GET: Obtener ejercicios por subtema
    @GetMapping("/subtema/{subtemaId}")
    public ResponseEntity<List<Ejercicio>> obtenerPorSubtema(@PathVariable Long subtemaId) {
        return ResponseEntity.ok(ejercicioService.obtenerPorSubtema(subtemaId));
    }

    // GET: Obtener ejercicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio> obtenerPorId(@PathVariable Long id) {
        return ejercicioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Actualizar ejercicio
    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> actualizarEjercicio(
            @PathVariable Long id,
            @RequestBody Ejercicio ejercicio
    ) {
        try {
            Ejercicio actualizado = ejercicioService.actualizarEjercicio(id, ejercicio);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar ejercicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEjercicio(@PathVariable Long id) {
        try {
            ejercicioService.eliminarEjercicio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}