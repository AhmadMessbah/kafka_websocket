package com.kafka_websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_websocket.model.CheckResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CheckConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final EncryptionService encryptionService;
    private final ObjectMapper objectMapper;

    public CheckConsumer(SimpMessagingTemplate messagingTemplate, EncryptionService encryptionService, ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.encryptionService = encryptionService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "check-responses", groupId = "check-group")
    public void consume(String encryptedResponse) throws Exception {
        String decryptedResponse = encryptionService.decrypt(encryptedResponse);
        CheckResponse response = objectMapper.readValue(decryptedResponse, CheckResponse.class);
        messagingTemplate.convertAndSend("/topic/check-responses", response);
    }
}