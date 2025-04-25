package com.kafka_websocket.controller;

import com.kafka_websocket.model.ChatMessage;
import com.kafka_websocket.service.KafkaService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    private final KafkaService kafkaService;

    public ChatController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping("/message")
    public String chat() {
        return "message";
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage message) throws Exception {
        kafkaService.sendMessage(message);
    }
}