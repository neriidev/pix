package com.neriidev.pix.infrastructure.in.dtos.request;

import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;
import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;

public record PixKeyRequest(String key, PixKeyType type) {}
