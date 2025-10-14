package com.neriidev.pix.domain.service;

import com.neriidev.pix.domain.model.PixKeyEntity;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;


public interface PixKeyService {
    PixKeyEntity registerKey(Long walletId, PixKeyRequest pixKeyRequest);
}
