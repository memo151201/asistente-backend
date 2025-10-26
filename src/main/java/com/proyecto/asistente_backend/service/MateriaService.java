package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Materia;
import com.proyecto.asistente_backend.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    // Crear materia
    public Materia crearMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    // Obtener todas las materias
    public List<Materia> obtenerTodas() {
        return materiaRepository.findAll();
    }

    // Obtener materias activas
    public List<Materia> obtenerActivas() {
        return materiaRepository.findByActivaTrue();
    }

    // Obtener materia por ID
    public Optional<Materia> obtenerPorId(Long id) {
        return materiaRepository.findById(id);
    }

    // Obtener materia por código
    public Optional<Materia> obtenerPorCodigo(String codigo) {
        return materiaRepository.findByCodigo(codigo);
    }

    // Obtener por nivel de dificultad
    public List<Materia> obtenerPorNivel(String nivel) {
        return materiaRepository.findByNivelDificultad(nivel);
    }

    // Actualizar materia
    public Materia actualizarMateria(Long id, Materia materiaActualizada) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        materia.setNombre(materiaActualizada.getNombre());
        materia.setDescripcion(materiaActualizada.getDescripcion());
        materia.setCodigo(materiaActualizada.getCodigo());
        materia.setNivelDificultad(materiaActualizada.getNivelDificultad());

        return materiaRepository.save(materia);
    }

    // Eliminar materia (desactivar)
    public void desactivarMateria(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        materia.setActiva(false);
        materiaRepository.save(materia);
    }

    // Eliminar permanentemente
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }
}