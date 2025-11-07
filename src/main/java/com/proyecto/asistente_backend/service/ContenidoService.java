package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.Contenido;
import com.proyecto.asistente_backend.model.Subtema;
import com.proyecto.asistente_backend.repository.ContenidoRepository;
import com.proyecto.asistente_backend.repository.SubtemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Autowired
    private SubtemaRepository subtemaRepository;

    // Crear contenido
    public Contenido crearContenido(Contenido contenido) {
        // Validar que el subtema exista
        Subtema subtema = subtemaRepository.findById(contenido.getSubtema().getId())
                .orElseThrow(() -> new RuntimeException("Subtema no encontrado"));

        contenido.setSubtema(subtema);

        // Si no hay orden, ponerlo al final
        if (contenido.getOrden() == null) {
            Long count = contenidoRepository.countBySubtemaId(subtema.getId());
            contenido.setOrden(count.intValue() + 1);
        }

        return contenidoRepository.save(contenido);
    }

    // Obtener todos los contenidos
    public List<Contenido> obtenerTodos() {
        return contenidoRepository.findAll();
    }

    // Obtener contenido por ID
    public Optional<Contenido> obtenerPorId(Long id) {
        return contenidoRepository.findById(id);
    }

    // Obtener contenidos de un subtema (solo activos)
    public List<Contenido> obtenerPorSubtema(Long subtemaId) {
        return contenidoRepository.findBySubtemaIdAndActivoTrueOrderByOrdenAsc(subtemaId);
    }

    // Obtener TODOS los contenidos de un subtema (incluye inactivos)
    public List<Contenido> obtenerTodosPorSubtema(Long subtemaId) {
        return contenidoRepository.findBySubtemaIdOrderByOrdenAsc(subtemaId);
    }

    // Obtener contenido completo en texto para la IA
    public String obtenerContenidoCompletoParaIA(Long subtemaId) {
        List<Contenido> contenidos = obtenerPorSubtema(subtemaId);

        StringBuilder contenidoCompleto = new StringBuilder();

        for (Contenido cont : contenidos) {
            contenidoCompleto.append("=== ").append(cont.getTitulo()).append(" ===\n");
            if (cont.getCuerpo() != null && !cont.getCuerpo().isEmpty()) {
                contenidoCompleto.append(cont.getCuerpo()).append("\n\n");
            }
        }

        return contenidoCompleto.toString();
    }

    // Actualizar contenido
    public Contenido actualizarContenido(Long id, Contenido contenidoActualizado) {
        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado"));

        contenido.setTitulo(contenidoActualizado.getTitulo());
        contenido.setTipo(contenidoActualizado.getTipo());
        contenido.setCuerpo(contenidoActualizado.getCuerpo());
        contenido.setUrl(contenidoActualizado.getUrl());
        contenido.setOrden(contenidoActualizado.getOrden());

        if (contenidoActualizado.getActivo() != null) {
            contenido.setActivo(contenidoActualizado.getActivo());
        }

        return contenidoRepository.save(contenido);
    }

    // Desactivar contenido
    public void desactivarContenido(Long id) {
        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado"));
        contenido.setActivo(false);
        contenidoRepository.save(contenido);
    }

    // Eliminar contenido permanentemente
    public void eliminarContenido(Long id) {
        contenidoRepository.deleteById(id);
    }
}