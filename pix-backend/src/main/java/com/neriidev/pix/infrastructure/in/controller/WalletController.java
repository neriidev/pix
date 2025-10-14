package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.infrastructure.in.dto.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.in.dto.request.WalletRequest;
import com.neriidev.pix.infrastructure.in.dto.request.WithdrawRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallets")
public class WalletController {



    @PostMapping
    public ResponseEntity<Object> createWallet(@RequestBody WalletRequest request) {
        return null;
    }

    @PostMapping("/{id}/pix-keys")
    public ResponseEntity<Object> registerPixKey(@PathVariable Long id, @RequestBody PixKeyRequest request) {
        return null;
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime at) {
        return null;
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody WithdrawRequest request) {
        return null;
    }
}
