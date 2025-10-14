package com.neriidev.pix.infrastructure.in.dtos.request;

import com.neriidev.pix.domain.model.PixKeyEntity;

public record PixKeyRequest(String key, PixKeyEntity.PixKeyType type) {}
