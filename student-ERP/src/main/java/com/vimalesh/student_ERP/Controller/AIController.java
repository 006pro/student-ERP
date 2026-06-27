package com.vimalesh.student_ERP.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI Assistant", description = "AI-powered chat using Ollama")
@SecurityRequirement(name = "bearerAuth")
public class AIController {

    private final ChatClient chatClient;

    public AIController(OllamaChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    @Operation(summary = "Send a message to the AI assistant and get a response")
    @GetMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message) {
        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response = chatResponse.getResult().getOutput().getText();

        return ResponseEntity.ok(response);
    }
}
