package com.neriidev.pix.application.ports.in;

import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;

public interface PixUseCase {
    void internalTransfer(String idempotencyKey, TransferRequest request);
}
