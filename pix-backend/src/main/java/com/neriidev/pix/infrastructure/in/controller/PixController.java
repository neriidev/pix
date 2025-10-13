package com.neriidev.pix.infrastructure.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    @PostMapping("/transfers")
    public ResponseEntity<Object> internalTransfer(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody Object request) {
        return null;
    }
}
