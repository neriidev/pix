package com.neriidev.pix.infrastructure.in.dtos.response;


import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;

public record PixKeyResponse(Long id, String key, PixKeyType type) {}