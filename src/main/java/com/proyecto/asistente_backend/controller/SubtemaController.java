package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.model.Subtema;
import com.proyecto.asistente_backend.service.SubtemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtemas")
@CrossOrigin(origins = "*")
public class SubtemaController {

    @Autowired
    private SubtemaService subtemaService;

    // GET: Obtener todos los subtemas
    @GetMapping
    public ResponseEntity<List<Subtema>> obtenerTodos() {
        return ResponseEntity.ok(subtemaService.obtenerTodos());
    }

    // GET: Obtener subtema por ID
    @GetMapping("/{id}")
    public ResponseEntity<Subtema> obtenerPorId(@PathVariable Long id) {
        return subtemaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener subtemas por tema
    @GetMapping("/tema/{temaId}")
    public ResponseEntity<List<Subtema>> obtenerPorTema(@PathVariable Long temaId) {
        return ResponseEntity.ok(subtemaService.obtenerPorTema(temaId));
    }

    // GET: Buscar subtemas por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Subtema>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(subtemaService.buscarPorNombre(nombre));
    }

    // POST: Crear subtema
    @PostMapping
    public ResponseEntity<Subtema> crearSubtema(@RequestBody Subtema subtema) {
        Subtema nuevoSubtema = subtemaService.crearSubtema(subtema);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSubtema);
    }

    // PUT: Actualizar subtema
    @PutMapping("/{id}")
    public ResponseEntity<Subtema> actualizarSubtema(@PathVariable Long id, @RequestBody Subtema subtema) {
        try {
            Subtema subtemaActualizado = subtemaService.actualizarSubtema(id, subtema);
            return ResponseEntity.ok(subtemaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar subtema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSubtema(@PathVariable Long id) {
        try {
            subtemaService.eliminarSubtema(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}