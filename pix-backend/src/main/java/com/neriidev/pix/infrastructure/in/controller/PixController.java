package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.infrastructure.in.dto.request.PixKeyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    @PostMapping("/transfers")
    public ResponseEntity<Object> internalTransfer(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody PixKeyRequest request) {
        return null;
    }
}
