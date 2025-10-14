package com.neriidev.pix.infrastructure.in.dtos.request;

import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;

public record PixKeyRequest(String key, PixKeyEntity.PixKeyType type) {}
