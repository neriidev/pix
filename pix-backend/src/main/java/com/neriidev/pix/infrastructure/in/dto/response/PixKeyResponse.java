package com.neriidev.pix.infrastructure.in.dto.response;

import com.neriidev.pix.domain.model.PixKey;

public class PixKeyResponse {
    private Long id;
    private String key;
    private PixKey.PixKeyType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PixKey.PixKeyType getType() {
        return type;
    }

    public void setType(PixKey.PixKeyType type) {
        this.type = type;
    }
}
