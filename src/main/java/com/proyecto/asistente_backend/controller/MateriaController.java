package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.model.Materia;
import com.proyecto.asistente_backend.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    // GET: Obtener todas las materias
    @GetMapping
    public ResponseEntity<List<Materia>> obtenerTodas() {
        return ResponseEntity.ok(materiaService.obtenerTodas());
    }

    // GET: Obtener materias activas
    @GetMapping("/activas")
    public ResponseEntity<List<Materia>> obtenerActivas() {
        return ResponseEntity.ok(materiaService.obtenerActivas());
    }

    // GET: Obtener materia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerPorId(@PathVariable Long id) {
        return materiaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener materia por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Materia> obtenerPorCodigo(@PathVariable String codigo) {
        return materiaService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener por nivel de dificultad
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Materia>> obtenerPorNivel(@PathVariable String nivel) {
        return ResponseEntity.ok(materiaService.obtenerPorNivel(nivel));
    }

    // POST: Crear materia
    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody Materia materia) {
        Materia nuevaMateria = materiaService.crearMateria(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMateria);
    }

    // PUT: Actualizar materia
    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarMateria(@PathVariable Long id, @RequestBody Materia materia) {
        try {
            Materia materiaActualizada = materiaService.actualizarMateria(id, materia);
            return ResponseEntity.ok(materiaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Desactivar materia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarMateria(@PathVariable Long id) {
        try {
            materiaService.desactivarMateria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}