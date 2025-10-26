package com.proyecto.asistente_backend.repository;


import com.proyecto.asistente_backend.model.Subtema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtemaRepository extends JpaRepository<Subtema, Long> {

    // Buscar subtemas de un tema específico
    List<Subtema> findByTemaId(Long temaId);

    // Buscar subtemas de un tema ordenados
    List<Subtema> findByTemaIdOrderByOrdenAsc(Long temaId);

    // Buscar subtema por nombre
    List<Subtema> findByNombreContainingIgnoreCase(String nombre);
}