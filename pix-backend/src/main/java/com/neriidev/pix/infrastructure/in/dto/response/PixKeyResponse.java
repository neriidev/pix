package com.neriidev.pix.infrastructure.in.dto.response;

import com.neriidev.pix.domain.model.PixKey;

public record PixKeyResponse(Long id, String key, PixKey.PixKeyType type) {}