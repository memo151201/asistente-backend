package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.model.Tema;
import com.proyecto.asistente_backend.service.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temas")
@CrossOrigin(origins = "*")
public class TemaController {

    @Autowired
    private TemaService temaService;

    // GET: Obtener todos los temas
    @GetMapping
    public ResponseEntity<List<Tema>> obtenerTodos() {
        return ResponseEntity.ok(temaService.obtenerTodos());
    }

    // GET: Obtener tema por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tema> obtenerPorId(@PathVariable Long id) {
        return temaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener temas por materia
    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<List<Tema>> obtenerPorMateria(@PathVariable Long materiaId) {
        return ResponseEntity.ok(temaService.obtenerPorMateria(materiaId));
    }

    // GET: Buscar temas por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Tema>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(temaService.buscarPorNombre(nombre));
    }

    // POST: Crear tema
    @PostMapping
    public ResponseEntity<Tema> crearTema(@RequestBody Tema tema) {
        Tema nuevoTema = temaService.crearTema(tema);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTema);
    }

    // PUT: Actualizar tema
    @PutMapping("/{id}")
    public ResponseEntity<Tema> actualizarTema(@PathVariable Long id, @RequestBody Tema tema) {
        try {
            Tema temaActualizado = temaService.actualizarTema(id, tema);
            return ResponseEntity.ok(temaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar tema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTema(@PathVariable Long id) {
        try {
            temaService.eliminarTema(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}