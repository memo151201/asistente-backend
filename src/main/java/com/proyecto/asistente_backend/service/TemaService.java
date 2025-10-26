package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Tema;
import com.proyecto.asistente_backend.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    // Crear tema
    public Tema crearTema(Tema tema) {
        return temaRepository.save(tema);
    }

    // Obtener todos los temas
    public List<Tema> obtenerTodos() {
        return temaRepository.findAll();
    }

    // Obtener tema por ID
    public Optional<Tema> obtenerPorId(Long id) {
        return temaRepository.findById(id);
    }

    // Obtener temas de una materia
    public List<Tema> obtenerPorMateria(Long materiaId) {
        return temaRepository.findByMateriaIdOrderByOrdenAsc(materiaId);
    }

    // Buscar temas por nombre
    public List<Tema> buscarPorNombre(String nombre) {
        return temaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Actualizar tema
    public Tema actualizarTema(Long id, Tema temaActualizado) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));

        tema.setNombre(temaActualizado.getNombre());
        tema.setDescripcion(temaActualizado.getDescripcion());
        tema.setOrden(temaActualizado.getOrden());

        return temaRepository.save(tema);
    }

    // Eliminar tema
    public void eliminarTema(Long id) {
        temaRepository.deleteById(id);
    }
}