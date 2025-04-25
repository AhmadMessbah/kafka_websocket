package com.kafka_websocket.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic chatTopic() {
        return TopicBuilder.name("chat-messages")
                .partitions(1)
                .replicas(1)
                .build();
    }
}