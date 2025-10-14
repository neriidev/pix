package com.neriidev.pix.application.ports.in;

import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;


public interface PixKeyUseCase {
    PixKey registerKey(Long walletId, PixKeyRequest pixKeyRequest);
}
