package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Subtema;
import com.proyecto.asistente_backend.repository.SubtemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubtemaService {

    @Autowired
    private SubtemaRepository subtemaRepository;

    // Crear subtema
    public Subtema crearSubtema(Subtema subtema) {
        return subtemaRepository.save(subtema);
    }

    // Obtener todos los subtemas
    public List<Subtema> obtenerTodos() {
        return subtemaRepository.findAll();
    }

    // Obtener subtema por ID
    public Optional<Subtema> obtenerPorId(Long id) {
        return subtemaRepository.findById(id);
    }

    // Obtener subtemas de un tema
    public List<Subtema> obtenerPorTema(Long temaId) {
        return subtemaRepository.findByTemaIdOrderByOrdenAsc(temaId);
    }

    // Buscar subtemas por nombre
    public List<Subtema> buscarPorNombre(String nombre) {
        return subtemaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Actualizar subtema
    public Subtema actualizarSubtema(Long id, Subtema subtemaActualizado) {
        Subtema subtema = subtemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

        subtema.setNombre(subtemaActualizado.getNombre());
        subtema.setDescripcion(subtemaActualizado.getDescripcion());
        subtema.setContenido(subtemaActualizado.getContenido());
        subtema.setOrden(subtemaActualizado.getOrden());

        return subtemaRepository.save(subtema);
    }

    // Eliminar subtema
    public void eliminarSubtema(Long id) {
        subtemaRepository.deleteById(id);
    }
}