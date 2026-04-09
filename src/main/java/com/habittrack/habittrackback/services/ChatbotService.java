package com.habittrack.habittrackback.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    @Value("${google.gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGeminiResponse(String userMessage) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key="
                + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String systemContext = """
                Eres HabitBot, el asistente inteligente oficial de la aplicación HabitTrack.
                Tu objetivo es ayudar a los usuarios a mantener su disciplina y sacar el máximo provecho de la plataforma.

                CONOCIMIENTO DE LA APP:
                - 'Mis Hábitos': Es la sección principal para crear, editar y marcar hábitos completados.
                - 'Analytics': Aquí los usuarios encuentran gráficos de progreso, historial de cumplimiento y sus rachas actuales.
                - 'Ajustes': Sección para gestionar el perfil, cambiar avatar, actualizar correo y configurar notificaciones.
                - 'Rachas': Explica que la constancia es clave; completar un hábito varios días seguidos aumenta la racha.

                FILOSOFÍA DE HÁBITOS:
                - Te basas en principios de psicología del comportamiento (como los de 'Hábitos Atómicos').
                - Recomiendas empezar con pasos muy pequeños para evitar el abandono.
                - Si un usuario dice que ha fallado, sé empático y motívalo a retomar el hábito de inmediato.

                REGLAS DE RESPUESTA:
                1. Tus respuestas deben ser breves, directas y motivadoras (máximo 3 párrafos).
                2. Usa un tono cercano y amigable.
                3. Resalta palabras clave en **negrita** para facilitar la lectura.
                4. Si el usuario pregunta sobre temas ajenos a la app o al desarrollo personal, intenta reconducir la conversación hacia el bienestar y la productividad.
                """;

        String cleanMessage = userMessage.replace("\"", "\\\"");
        String cleanContext = systemContext.replace("\"", "\\\"");

        String jsonBody = "{" +
                "\"contents\": [" +
                "{\"role\": \"user\", \"parts\":[{\"text\": \"" + cleanContext + "\"}]}," +
                "{\"role\": \"model\", \"parts\":[{\"text\": \"Entendido. Soy HabitBot y estoy listo para ayudar al usuario de HabitTrack con sus hábitos.\"}]},"
                +
                "{\"role\": \"user\", \"parts\":[{\"text\": \"" + cleanMessage + "\"}]}" +
                "]" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                return (String) parts.get(0).get("text");
            }
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            return "Lo siento, el servicio de Inteligencia Artificial está un poco saturado en este momento. ¡Vuelve a intentarlo en unos segundos! 🚀";
        }

        return "No recibí respuesta de la IA.";
    }
}
