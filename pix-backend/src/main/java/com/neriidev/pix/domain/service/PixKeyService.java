package com.neriidev.pix.domain.service;

import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;


public interface PixKeyService {
    PixKey registerKey(Long walletId, PixKeyRequest pixKeyRequest);
}
