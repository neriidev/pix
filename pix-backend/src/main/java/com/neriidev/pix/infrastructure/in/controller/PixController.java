package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.application.ports.in.InternalTransferUseCase;
import com.neriidev.pix.application.ports.in.PixUseCase;
import com.neriidev.pix.application.ports.in.WebhookUseCase;
import com.neriidev.pix.domain.model.Transaction;
import com.neriidev.pix.infrastructure.in.dtos.request.InternalTransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WebhookRequest;
import com.neriidev.pix.infrastructure.in.dtos.response.InternalTransferResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    private final InternalTransferUseCase internalTransferUseCase;
    private final WebhookUseCase webhookUseCase;
    private final PixUseCase pixService;

    public PixController(InternalTransferUseCase internalTransferUseCase, WebhookUseCase webhookUseCase, PixUseCase pixService) {
        this.internalTransferUseCase = internalTransferUseCase;
        this.webhookUseCase = webhookUseCase;
        this.pixService = pixService;
    }

    @PostMapping("/transfers")
    public ResponseEntity<InternalTransferResponse> internalTransfer(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody InternalTransferRequest request) {
        Transaction transaction = internalTransferUseCase.internalTransfer(idempotencyKey, request.fromPixKey(), request.toPixKey(), request.amount());
        InternalTransferResponse response = new InternalTransferResponse();
        response.setId(transaction.getId());
        response.setEndToEndId(transaction.getEndToEndId());
        response.setAmount(transaction.getAmount());
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody WebhookRequest request) {
        webhookUseCase.processWebhook(request.getEventId(), request.getEndToEndId(), request.getStatus());
        return ResponseEntity.ok().build();
    }
}
