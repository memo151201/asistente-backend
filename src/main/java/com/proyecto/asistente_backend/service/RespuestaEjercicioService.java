package com.proyecto.asistente_backend.service;

import com.proyecto.asistente_backend.model.RespuestaEjercicio;
import com.proyecto.asistente_backend.repository.RespuestaEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespuestaEjercicioService {

    @Autowired
    private RespuestaEjercicioRepository respuestaEjercicioRepository;

    public List<RespuestaEjercicio> obtenerPorUsuario(Long usuarioId) {
        return respuestaEjercicioRepository.findByUsuarioIdOrderByFechaRespuestaDesc(usuarioId);
    }

    public List<RespuestaEjercicio> obtenerPorEjercicio(Long ejercicioId) {
        return respuestaEjercicioRepository.findByEjercicioId(ejercicioId);
    }

    public Optional<RespuestaEjercicio> obtenerPorId(Long id) {
        return respuestaEjercicioRepository.findById(id);
    }

    public List<RespuestaEjercicio> obtenerTodas() {
        return respuestaEjercicioRepository.findAll();
    }
}