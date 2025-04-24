package com.kafka_websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_websocket.model.CheckRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CheckProducer {
    private static final String REQUEST_TOPIC = "check-requests";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EncryptionService encryptionService;
    private final ObjectMapper objectMapper;

    public CheckProducer(KafkaTemplate<String, String> kafkaTemplate, EncryptionService encryptionService, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.encryptionService = encryptionService;
        this.objectMapper = objectMapper;
    }

    public void sendCheckRequest(CheckRequest request) throws Exception {
        String requestJson = objectMapper.writeValueAsString(request);
        String encryptedRequest = encryptionService.encrypt(requestJson);
        kafkaTemplate.send(REQUEST_TOPIC, request.getCheckId(), encryptedRequest);
    }
}