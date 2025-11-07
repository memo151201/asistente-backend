package com.proyecto.asistente_backend.repository;

import com.proyecto.asistente_backend.model.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    List<Contenido> findBySubtemaIdOrderByOrdenAsc(Long subtemaId);

    List<Contenido> findBySubtemaIdAndActivoTrueOrderByOrdenAsc(Long subtemaId);

    List<Contenido> findByTipo(Contenido.TipoContenido tipo);

    Long countBySubtemaId(Long subtemaId);
}