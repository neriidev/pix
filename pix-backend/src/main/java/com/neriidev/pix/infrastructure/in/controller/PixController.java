package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.application.ports.in.InternalTransferUseCase;
import com.neriidev.pix.application.ports.in.PixUseCase;
import com.neriidev.pix.application.ports.in.WebhookUseCase;
import com.neriidev.pix.domain.model.Transaction;
import com.neriidev.pix.infrastructure.in.dtos.request.InternalTransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WebhookRequest;
import com.neriidev.pix.infrastructure.in.dtos.response.InternalTransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    private static final Logger log = LoggerFactory.getLogger(PixController.class);

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
        log.info("Iniciando transferência interna de {} para {} no valor de {}", request.fromPixKey(), request.toPixKey(), request.amount());
        Transaction transaction = internalTransferUseCase.internalTransfer(idempotencyKey, request.fromPixKey(), request.toPixKey(), request.amount());
        InternalTransferResponse response = new InternalTransferResponse();
        response.setId(transaction.getId());
        response.setEndToEndId(transaction.getEndToEndId());
        response.setAmount(transaction.getAmount());
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt());
        log.info("Transferência interna realizada com sucesso com o id da transação: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody WebhookRequest request) {
        log.info("Processando webhook para o evento: {}", request.getEventId());
        webhookUseCase.processWebhook(request.getEventId(), request.getEndToEndId(), request.getStatus());
        log.info("Webhook para o evento {} processado com sucesso", request.getEventId());
        return ResponseEntity.ok().build();
    }
}
