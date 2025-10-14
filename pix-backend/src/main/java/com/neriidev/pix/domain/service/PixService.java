package com.neriidev.pix.domain.service;

import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;

public interface PixService {
    void internalTransfer(String idempotencyKey, TransferRequest request);
}
