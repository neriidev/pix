package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.domain.service.PixKeyService;
import com.neriidev.pix.domain.service.WalletService;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private PixKeyService pixKeyService;

    @PostMapping
    public ResponseEntity<Object> createWallet(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.create(request));
    }

    @PostMapping("/{id}/pix-keys")
    public ResponseEntity<Object> registerPixKey(@PathVariable Long id, @RequestBody PixKeyRequest request) {
        return ResponseEntity.ok(pixKeyService.registerKey(id, request));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime at) {
        return ResponseEntity.ok(walletService.getBalance(id, at));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody TransferRequest.AmountRequest request) {
        walletService.withdraw(id, request);
        return ResponseEntity.ok().build();
    }
}
