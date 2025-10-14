package com.neriidev.pix.infrastructure.in.dtos.response;

import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;

public record PixKeyResponse(Long id, String key, PixKeyEntity.PixKeyType type) {}