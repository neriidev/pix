package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.application.ports.in.*;
import com.neriidev.pix.infrastructure.in.dtos.request.DepositRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletUseCase walletUseCase;

    @Autowired
    private PixKeyUseCase pixKeyUseCase;

    @Autowired
    private DepositUseCase depositUseCase;

    @Autowired
    private BalanceUseCase balanceUseCase;

    @Autowired
    private BalanceHistoryUseCase balanceHistoryUseCase;

    @PostMapping
    public ResponseEntity<Object> createWallet(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletUseCase.create(request));
    }

    @PostMapping("/{id}/pix-keys")
    public ResponseEntity<Object> registerPixKey(@PathVariable Long id, @RequestBody PixKeyRequest request) {
        return ResponseEntity.ok(pixKeyUseCase.registerKey(id, request));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime at) {
        BigDecimal balance;
        if (at != null) {
            balance = balanceHistoryUseCase.getBalanceAt(id, at);
        } else {
            balance = balanceUseCase.getBalance(id);
        }
        BalanceResponse response = new BalanceResponse(balance);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody DepositRequest request) {
        depositUseCase.deposit(id, request.amount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody TransferRequest.AmountRequest request) {
        walletUseCase.withdraw(id, request);
        return ResponseEntity.ok().build();
    }
}
