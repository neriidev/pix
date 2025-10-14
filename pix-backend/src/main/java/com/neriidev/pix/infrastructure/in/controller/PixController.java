package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.application.ports.in.PixUseCase;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    @Autowired
    private PixUseCase pixService;

    @PostMapping("/transfers")
    public ResponseEntity<Object> internalTransfer(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody TransferRequest request) {
        pixService.internalTransfer(idempotencyKey, request);
        return ResponseEntity.ok().build();
    }
}
