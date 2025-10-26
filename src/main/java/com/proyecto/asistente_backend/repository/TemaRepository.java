package com.proyecto.asistente_backend.repository;


import com.proyecto.asistente_backend.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

    // Buscar temas de una materia específica
    List<Tema> findByMateriaId(Long materiaId);

    // Buscar temas de una materia ordenados
    List<Tema> findByMateriaIdOrderByOrdenAsc(Long materiaId);

    // Buscar tema por nombre
    List<Tema> findByNombreContainingIgnoreCase(String nombre);
}