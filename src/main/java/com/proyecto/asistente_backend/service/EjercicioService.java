package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Ejercicio;
import com.proyecto.asistente_backend.model.Subtema;
import com.proyecto.asistente_backend.repository.EjercicioRepository;
import com.proyecto.asistente_backend.repository.SubtemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private SubtemaRepository subtemaRepository;

    public Ejercicio crearEjercicio(Ejercicio ejercicio) {
        // Validar que el subtema exista
        Subtema subtema = subtemaRepository.findById(ejercicio.getSubtema().getId())
                .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

        ejercicio.setSubtema(subtema);
        return ejercicioRepository.save(ejercicio);
    }

    public List<Ejercicio> obtenerTodos() {
        return ejercicioRepository.findAll();
    }

    public Optional<Ejercicio> obtenerPorId(Long id) {
        return ejercicioRepository.findById(id);
    }

    public List<Ejercicio> obtenerPorSubtema(Long subtemaId) {
        return ejercicioRepository.findBySubtemaIdAndActivoTrue(subtemaId);
    }

    public Ejercicio actualizarEjercicio(Long id, Ejercicio ejercicioActualizado) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        ejercicio.setEnunciado(ejercicioActualizado.getEnunciado());
        ejercicio.setDescripcion(ejercicioActualizado.getDescripcion());
        ejercicio.setTipo(ejercicioActualizado.getTipo());
        ejercicio.setNivelDificultad(ejercicioActualizado.getNivelDificultad());
        ejercicio.setSolucionReferencia(ejercicioActualizado.getSolucionReferencia());

        return ejercicioRepository.save(ejercicio);
    }

    public void eliminarEjercicio(Long id) {
        ejercicioRepository.deleteById(id);
    }
}