package com.proyecto.asistente_backend.dto;

public class EvaluarEjercicioRequest {

    private Long ejercicioId;
    private String respuestaUsuario;

    // Constructores
    public EvaluarEjercicioRequest() {}

    public EvaluarEjercicioRequest(Long ejercicioId, String respuestaUsuario) {
        this.ejercicioId = ejercicioId;
        this.respuestaUsuario = respuestaUsuario;
    }

    // Getters y Setters
    public Long getEjercicioId() {
        return ejercicioId;
    }

    public void setEjercicioId(Long ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    public String getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(String respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }
}