package com.kafka_websocket.model;

import java.time.LocalDateTime;

public class CheckRequest {
    private String checkId;
    private String accountNumber;
    private double amount;
    private LocalDateTime timestamp;

    // Getters, Setters, Constructors
    public CheckRequest() {}

    public CheckRequest(String checkId, String accountNumber, double amount, LocalDateTime timestamp) {
        this.checkId = checkId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getCheckId() { return checkId; }
    public void setCheckId(String checkId) { this.checkId = checkId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}