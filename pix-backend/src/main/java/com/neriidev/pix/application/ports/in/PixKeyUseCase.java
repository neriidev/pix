package com.neriidev.pix.application.ports.in;

import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;


public interface PixKeyUseCase {
    PixKeyEntity registerKey(Long walletId, PixKeyRequest pixKeyRequest);
}
