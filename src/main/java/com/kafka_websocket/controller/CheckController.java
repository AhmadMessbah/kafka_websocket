package com.kafka_websocket.controller;

import com.kafka_websocket.model.CheckRequest;
import com.kafka_websocket.service.CheckProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class CheckController {
    private final CheckProducer checkProducer;

    public CheckController(CheckProducer checkProducer) {
        this.checkProducer = checkProducer;
    }

    @GetMapping
    public String home(){
        return "index";
    }

    @MessageMapping("/check")
    public void handleCheckRequest(CheckRequest request) throws Exception {
        request.setTimestamp(LocalDateTime.now());
        checkProducer.sendCheckRequest(request);
    }
}