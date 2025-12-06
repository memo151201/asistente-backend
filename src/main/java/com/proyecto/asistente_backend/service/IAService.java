package com.proyecto.asistente_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.asistente_backend.model.*;
import com.proyecto.asistente_backend.repository.EjercicioRepository;
import com.proyecto.asistente_backend.repository.RespuestaEjercicioRepository;
import com.proyecto.asistente_backend.repository.UsuarioRepository;
import com.proyecto.asistente_backend.service.ContenidoService;
import com.proyecto.asistente_backend.repository.EjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class IAService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;
    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private RespuestaEjercicioRepository respuestaEjercicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContenidoService contenidoService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public IAService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * FUNCIÓN 1: Explicar contenido con IA
     */
    public String explicarContenido(String nombreSubtema, String contenidoOriginal) {
        try {
            System.out.println("📚 ========== EXPLICANDO CONTENIDO CON IA ==========");

            String systemPrompt = "Eres un profesor de programación experto y didáctico. " +
                    "Tu objetivo es explicar conceptos de manera clara, con ejemplos prácticos y analogías. " +
                    "Usa un lenguaje sencillo pero técnicamente correcto.";

            String userPrompt = String.format(
                    "Explica este concepto de programación de manera didáctica y completa:\n\n" +
                            "Tema: %s\n" +
                            "Contenido base: %s\n\n" +
                            "Proporciona:\n" +
                            "1. Una explicación clara y detallada (3-4 párrafos)\n" +
                            "2. Por qué es importante este concepto\n" +
                            "3. Cuándo se usa en la vida real\n" +
                            "4. Consejos para aprenderlo mejor",
                    nombreSubtema,
                    contenidoOriginal
            );

            return llamarGroqIA(systemPrompt, userPrompt, 500);

        } catch (Exception e) {
            System.err.println("❌ Error al explicar contenido: " + e.getMessage());
            return contenidoOriginal;
        }
    }

    /**
     * FUNCIÓN 2: Generar ejemplos de código con IA
     */
    public String generarEjemplos(String nombreSubtema, String contenido) {
        try {
            System.out.println("💡 ========== GENERANDO EJEMPLOS CON IA ==========");

            String systemPrompt = "Eres un profesor de programación que crea ejemplos de código " +
                    "claros, comentados y progresivos (de simple a complejo).";

            String userPrompt = String.format(
                    "Genera 3 ejemplos de código en Java sobre: %s\n\n" +
                            "Contexto: %s\n\n" +
                            "Requisitos:\n" +
                            "1. Ejemplo BÁSICO (3-5 líneas)\n" +
                            "2. Ejemplo INTERMEDIO (8-10 líneas)\n" +
                            "3. Ejemplo AVANZADO (15-20 líneas)\n" +
                            "4. Cada ejemplo debe estar comentado\n" +
                            "5. Explica qué hace cada ejemplo en 1 frase",
                    nombreSubtema,
                    contenido
            );

            return llamarGroqIA(systemPrompt, userPrompt, 800);

        } catch (Exception e) {
            System.err.println("❌ Error al generar ejemplos: " + e.getMessage());
            return "No se pudieron generar ejemplos. Intenta de nuevo.";
        }
    }

    /**
     * FUNCIÓN 3: Generar preguntas dinámicas - VERSIÓN MEJORADA
     */
    public List<Map<String, Object>> generarPreguntasDinamicas(String nombreSubtema, String contenido) {
        try {
            System.out.println("❓ ========== GENERANDO PREGUNTAS DINÁMICAS CON IA ==========");

            String systemPrompt = "Eres un experto en crear evaluaciones educativas. " +
                    "Creas preguntas de opción múltiple desafiantes pero justas, con 4 opciones donde " +
                    "solo una es correcta y las demás son distractores plausibles. " +
                    "IMPORTANTE: Responde SOLO con JSON válido, sin texto adicional antes o después.";

            String userPrompt = String.format(
                    "Genera 5 preguntas de opción múltiple sobre: %s\n\n" +
                            "Contenido: %s\n\n" +
                            "Formato JSON (CRÍTICO - responde SOLO con el array JSON):\n" +
                            "[\n" +
                            "  {\n" +
                            "    \"enunciado\": \"Texto de la pregunta aquí\",\n" +
                            "    \"opcionA\": \"Primera opción completa\",\n" +
                            "    \"opcionB\": \"Segunda opción completa\",\n" +
                            "    \"opcionC\": \"Tercera opción completa\",\n" +
                            "    \"opcionD\": \"Cuarta opción completa\",\n" +
                            "    \"respuestaCorrecta\": \"A\",\n" +
                            "    \"explicacion\": \"Explicación de por qué es correcta\"\n" +
                            "  }\n" +
                            "]\n\n" +
                            "REGLAS CRÍTICAS:\n" +
                            "- Las opciones deben ser TEXTOS COMPLETOS, no solo letras\n" +
                            "- Cada opción debe tener contenido descriptivo\n" +
                            "- 2 preguntas fáciles, 2 medias, 1 difícil\n" +
                            "- NO agregues texto antes o después del JSON\n" +
                            "- Responde ÚNICAMENTE con el array JSON",
                    nombreSubtema,
                    contenido
            );

            String respuestaIA = llamarGroqIA(systemPrompt, userPrompt, 1200);

            System.out.println("📋 Respuesta completa de IA:");
            System.out.println(respuestaIA);

            // Parsear JSON con Jackson
            List<Map<String, Object>> preguntas = parsearPreguntasConJackson(respuestaIA);

            System.out.println("✅ Preguntas parseadas: " + preguntas.size());

            // Validar que las preguntas tengan contenido
            for (int i = 0; i < preguntas.size(); i++) {
                Map<String, Object> p = preguntas.get(i);
                System.out.println("\n📝 Pregunta " + (i+1) + ":");
                System.out.println("   Enunciado: " + p.get("enunciado"));
                System.out.println("   Opción A: " + p.get("opcionA"));
                System.out.println("   Opción B: " + p.get("opcionB"));
                System.out.println("   Opción C: " + p.get("opcionC"));
                System.out.println("   Opción D: " + p.get("opcionD"));
                System.out.println("   Correcta: " + p.get("respuestaCorrecta"));
            }

            return preguntas;

        } catch (Exception e) {
            System.err.println("❌ Error al generar preguntas: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * FUNCIÓN 4: Retroalimentación personalizada
     */
    public String generarRetroalimentacion(
            String pregunta,
            String respuestaCorrecta,
            String respuestaUsuario,
            String explicacionOriginal) {

        try {
            System.out.println("🤖 Generando feedback con IA...");

            String systemPrompt = "Eres un tutor de programación amigable y didáctico en español. " +
                    "Tu objetivo es ayudar al estudiante a entender por qué su respuesta fue incorrecta " +
                    "de manera clara, breve y motivadora. Usa máximo 3 frases cortas.";

            String userPrompt = String.format(
                    "El estudiante respondió incorrectamente:\n\n" +
                            "Pregunta: %s\n" +
                            "Respuesta correcta: %s\n" +
                            "Respuesta del estudiante: %s\n\n" +
                            "Genera retroalimentación que:\n" +
                            "1. Sea empática\n" +
                            "2. Explique por qué está incorrecta\n" +
                            "3. Enseñe la respuesta correcta\n" +
                            "4. Dé un consejo práctico\n" +
                            "Máximo 3 frases.",
                    pregunta,
                    respuestaCorrecta,
                    respuestaUsuario
            );

            String feedback = llamarGroqIA(systemPrompt, userPrompt, 250);
            System.out.println("🤖 Feedback generado: " + feedback);
            return feedback;

        } catch (Exception e) {
            System.err.println("❌ Error al generar retroalimentación: " + e.getMessage());
            return explicacionOriginal;
        }
    }
    public RespuestaEjercicio evaluarEjercicio(
            Long ejercicioId,
            String respuestaUsuario,
            Long usuarioId
    ) {

        // 1. Buscar el ejercicio
        Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        // 2. Obtener CONTENIDO del subtema (esto es clave)
        String contenidoSubtema = contenidoService
                .obtenerContenidoCompletoParaIA(ejercicio.getSubtema().getId());

        // 3. Crear prompt para Grok
        String prompt = String.format("""
        Eres un profesor experto. Evalúa la respuesta de un estudiante.
        
        CONTEXTO DEL TEMA (contenido educativo):
        %s
        
        EJERCICIO PLANTEADO:
        %s
        %s
        
        %s
        
        RESPUESTA DEL ESTUDIANTE:
        %s
        
        Evalúa la respuesta considerando:
        1. ¿Es correcta según el contenido del tema?
        2. ¿Demuestra comprensión?
        3. ¿Tiene errores conceptuales o de sintaxis?
        
        Responde SOLO con este JSON (sin markdown):
        {
          "estado": "CORRECTO o PARCIALMENTE_CORRECTO o INCORRECTO",
          "puntaje": 85,
          "retroalimentacion": "Evaluación detallada y constructiva"
        }
        """,
                contenidoSubtema,
                ejercicio.getEnunciado(),
                ejercicio.getDescripcion() != null ? "Descripción: " + ejercicio.getDescripcion() : "",
                ejercicio.getSolucionReferencia() != null ?
                        "Solución de referencia: " + ejercicio.getSolucionReferencia() : "",
                respuestaUsuario
        );

        // 4. Llamar a Grok
        String respuestaGrok = llamarGroqIA(
                "Eres un profesor experto evaluador",
                prompt,
                500
        );

        // 5. Parsear respuesta
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonLimpio = respuestaGrok
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            JsonNode evaluacion = mapper.readTree(jsonLimpio);

            // 6. Crear respuesta
            RespuestaEjercicio respuesta = new RespuestaEjercicio();
            respuesta.setEjercicio(ejercicio);
            respuesta.setUsuario(usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
            respuesta.setRespuestaUsuario(respuestaUsuario);
            respuesta.setRetroalimentacionIA(evaluacion.path("retroalimentacion").asText());
            respuesta.setEstado(RespuestaEjercicio.EstadoEvaluacion.valueOf(
                    evaluacion.path("estado").asText()
            ));
            respuesta.setPuntaje(evaluacion.path("puntaje").asInt());

            return respuestaEjercicioRepository.save(respuesta);

        } catch (Exception e) {
            throw new RuntimeException("Error al parsear evaluación: " + e.getMessage());
        }
    }

    /**
     * Genera un mensaje motivacional para respuestas correctas
     */
    public String generarMensajeMotivacional() {
        String[] mensajes = {
                "¡Excelente! 🌟 Demostraste un gran dominio del tema.",
                "¡Perfecto! 👏 Tu comprensión es sólida. Sigue así.",
                "¡Muy bien! 🎯 Has entendido el concepto correctamente.",
                "¡Correcto! 💪 Tu conocimiento va en aumento.",
                "¡Fantástico! ⭐ Estás dominando este tema.",
                "¡Bien hecho! 🚀 Tu progreso es notable."
        };

        int random = (int) (Math.random() * mensajes.length);
        return mensajes[random];
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Método central para llamar a Groq IA
     */
    private String llamarGroqIA(String systemPrompt, String userPrompt, int maxTokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemPrompt);
        messages.add(systemMessage);

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userPrompt);
        messages.add(userMessage);

        body.put("messages", messages);
        body.put("temperature", 0.7);
        body.put("max_tokens", maxTokens);
        body.put("top_p", 0.9);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        System.out.println("🚀 Enviando request a Groq...");

        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        System.out.println("📥 Respuesta recibida de Groq");

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                String content = (String) message.get("content");
                return content.trim();
            }
        }

        throw new RuntimeException("No se recibió respuesta válida de Groq");
    }

    /**
     * Parsea el JSON de preguntas usando Jackson (más robusto)
     */
    private List<Map<String, Object>> parsearPreguntasConJackson(String respuestaIA) {
        List<Map<String, Object>> preguntas = new ArrayList<>();

        try {
            // Limpiar el texto: buscar el array JSON
            String jsonLimpio = extraerJSON(respuestaIA);
            System.out.println("🔧 JSON limpio: " + jsonLimpio);

            // Parsear con Jackson
            JsonNode rootNode = objectMapper.readTree(jsonLimpio);

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    Map<String, Object> pregunta = new HashMap<>();

                    pregunta.put("enunciado", node.get("enunciado").asText());
                    pregunta.put("opcionA", node.get("opcionA").asText());
                    pregunta.put("opcionB", node.get("opcionB").asText());
                    pregunta.put("opcionC", node.get("opcionC").asText());
                    pregunta.put("opcionD", node.get("opcionD").asText());
                    pregunta.put("respuestaCorrecta", node.get("respuestaCorrecta").asText());
                    pregunta.put("explicacion", node.get("explicacion").asText());

                    preguntas.add(pregunta);
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Error al parsear JSON con Jackson: " + e.getMessage());
            e.printStackTrace();
        }

        return preguntas;
    }

    /**
     * Extrae el array JSON de la respuesta (maneja texto extra)
     */
    private String extraerJSON(String texto) {
        // Buscar el inicio y fin del array JSON
        int inicio = texto.indexOf("[");
        int fin = texto.lastIndexOf("]");

        if (inicio != -1 && fin != -1 && fin > inicio) {
            return texto.substring(inicio, fin + 1);
        }

        // Si no encuentra [], buscar {}
        inicio = texto.indexOf("{");
        fin = texto.lastIndexOf("}");

        if (inicio != -1 && fin != -1 && fin > inicio) {
            // Si encuentra un objeto, envuélvelo en array
            return "[" + texto.substring(inicio, fin + 1) + "]";
        }

        return texto;
    }
}