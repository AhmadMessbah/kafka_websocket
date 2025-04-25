package com.kafka_websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_websocket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private static final String TOPIC = "chat-messages";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(ChatMessage message) throws Exception {
        String messageJson = objectMapper.writeValueAsString(message);
        kafkaTemplate.send(TOPIC, message.getSenderId(), messageJson);
    }

    @KafkaListener(topics = TOPIC, groupId = "chat-group")
    public void consumeMessage(String messageJson) throws Exception {
        ChatMessage message = objectMapper.readValue(messageJson, ChatMessage.class);
        if (message.getRecipientId() == null) {
            // پیام برای همه
            messagingTemplate.convertAndSend("/topic/public", message);
        } else {
            // پیام برای کاربر خاص
            messagingTemplate.convertAndSendToUser(
                    message.getRecipientId(),
                    "/topic/private",
                    message
            );
        }
    }
}