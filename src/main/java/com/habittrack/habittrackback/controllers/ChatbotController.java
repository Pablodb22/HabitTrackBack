package com.habittrack.habittrackback.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.habittrack.habittrackback.dtos.ChatRequestDTO;
import com.habittrack.habittrackback.dtos.ChatResponseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ChatbotController {

    private final com.habittrack.habittrackback.services.ChatbotService chatbotService;

    @PostMapping("/ask")
    public ChatResponseDTO preguntarChatbot(@RequestBody ChatRequestDTO request) {
        String respuesta = chatbotService.getGeminiResponse(request.getMessage());
        return new ChatResponseDTO(respuesta);
    }
}
