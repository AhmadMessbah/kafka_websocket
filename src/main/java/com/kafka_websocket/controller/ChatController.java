package com.kafka_websocket.controller;

import com.kafka_websocket.model.ChatMessage;
import com.kafka_websocket.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/message")
    public String chat() {
        return "message";
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage message) throws Exception {
        chatService.sendMessage(message);
    }
}