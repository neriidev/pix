package com.neriidev.pix.domain.service;

import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;


public interface PixKeyService {
    PixKeyEntity registerKey(Long walletId, PixKeyRequest pixKeyRequest);
}
