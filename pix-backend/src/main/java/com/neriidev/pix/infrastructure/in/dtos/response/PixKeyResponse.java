package com.neriidev.pix.infrastructure.in.dtos.response;

import com.neriidev.pix.domain.model.PixKeyEntity;

public record PixKeyResponse(Long id, String key, PixKeyEntity.PixKeyType type) {}