package com.neriidev.pix.infrastructure.in.controller;

import com.neriidev.pix.application.ports.in.*;
import com.neriidev.pix.infrastructure.in.dtos.request.DepositRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

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
        log.info("Criando carteira para o usuário: {}", request.getDocument());
        return ResponseEntity.ok(walletUseCase.create(request));
    }

    @PostMapping("/{id}/pix-keys")
    public ResponseEntity<Object> registerPixKey(@PathVariable Long id, @RequestBody PixKeyRequest request) {
        log.info("Registrando chave pix para a carteira: {}", id);
        return ResponseEntity.ok(pixKeyUseCase.registerKey(id, request));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime at) {
        log.info("Buscando saldo para a carteira: {}", id);
        BigDecimal balance;
        if (at != null) {
            balance = balanceHistoryUseCase.getBalanceAt(id, at);
        } else {
            balance = balanceUseCase.getBalance(id);
        }
        BalanceResponse response = new BalanceResponse(balance);
        log.info("Saldo para a carteira {} é: {}", id, response.getBalance());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody DepositRequest request) {
        log.info("Depositando {} na carteira: {}", request.amount(), id);
        depositUseCase.deposit(id, request.amount());
        log.info("Depósito para a carteira {} realizado com sucesso", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody TransferRequest.AmountRequest request) {
        log.info("Sacando {} da carteira: {}", request.getAmount(), id);
        walletUseCase.withdraw(id, request);
        log.info("Saque da carteira {} realizado com sucesso", id);
        return ResponseEntity.ok().build();
    }
}
