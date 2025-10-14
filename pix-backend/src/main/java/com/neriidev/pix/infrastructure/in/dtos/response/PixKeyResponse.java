package com.neriidev.pix.infrastructure.in.dtos.response;

import com.neriidev.pix.domain.model.PixKey;

public record PixKeyResponse(Long id, String key, PixKey.PixKeyType type) {}