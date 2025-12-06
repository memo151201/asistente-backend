package com.proyecto.asistente_backend.controller;

import com.proyecto.asistente_backend.dto.EstadisticasEstudianteDTO;
import com.proyecto.asistente_backend.service.EstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    @GetMapping("/estudiantes")
    public ResponseEntity<List<EstadisticasEstudianteDTO>> obtenerEstadisticasEstudiantes() {
        List<EstadisticasEstudianteDTO> estadisticas = estadisticasService.obtenerEstadisticasEstudiantes();
        return ResponseEntity.ok(estadisticas);
    }
}