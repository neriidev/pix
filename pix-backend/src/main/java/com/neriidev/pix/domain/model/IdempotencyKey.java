package com.neriidev.pix.domain.model;

import java.time.LocalDateTime;

public class IdempotencyKey {

    private String key;

    private String scope;

    private LocalDateTime createdAt;

    private String response;

    public IdempotencyKey(String key, String scope, LocalDateTime createdAt, String response) {
        this.key = key;
        this.scope = scope;
        this.createdAt = createdAt;
        this.response = response;
    }

    public String getKey() {
        return key;
    }

    public String getScope() {
        return scope;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getResponse() {
        return response;
    }
}

