package com.neriidev.pix.infrastructure.in.dtos.request;

import com.neriidev.pix.domain.model.PixKey;

public record PixKeyRequest(String key, PixKey.PixKeyType type) {}
