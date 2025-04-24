package com.kafka_websocket.model;

public class CheckResponse {
    private String checkId;
    private String status; // "APPROVED" or "REJECTED"
    private String message;

    // Getters, Setters, Constructors
    public CheckResponse() {}

    public CheckResponse(String checkId, String status, String message) {
        this.checkId = checkId;
        this.status = status;
        this.message = message;
    }

    public String getCheckId() { return checkId; }
    public void setCheckId(String checkId) { this.checkId = checkId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}