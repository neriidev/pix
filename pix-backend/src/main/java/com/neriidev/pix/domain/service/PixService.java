package com.neriidev.pix.domain.service;

import com.neriidev.pix.infrastructure.in.dto.request.TransferRequest;

public interface PixService {
    void internalTransfer(String idempotencyKey, TransferRequest request);
}
