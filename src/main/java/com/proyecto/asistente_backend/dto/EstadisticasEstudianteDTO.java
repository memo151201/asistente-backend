package com.proyecto.asistente_backend.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasEstudianteDTO {
    private Long usuarioId;
    private String nombreCompleto;
    private String email;
    private Integer totalPreguntas;
    private Integer preguntasCorrectas;
    private Integer totalEjercicios;
    private Integer ejerciciosCorrectos;
    private Double porcentajeAciertos;
    private Integer puntuacionTotal;
}