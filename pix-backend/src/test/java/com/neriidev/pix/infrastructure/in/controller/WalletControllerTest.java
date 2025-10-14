package com.neriidev.pix.infrastructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neriidev.pix.application.ports.in.*;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.in.dtos.request.DepositRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletUseCase walletService;

    @MockBean
    private PixKeyUseCase pixKeyService;

    @MockBean
    private DepositUseCase depositUseCase;

    @MockBean
    private BalanceUseCase balanceUseCase;

    @MockBean
    private BalanceHistoryUseCase balanceHistoryUseCase;

    @Test
    public void testCreateWallet() throws Exception {
        WalletRequest walletRequest = new WalletRequest(null, new BigDecimal("100.00"));
        WalletEntity wallet = new WalletEntity(1L, new BigDecimal("100.00"));

        when(walletService.create(any(WalletRequest.class))).thenReturn(wallet);

        mockMvc.perform(post("/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    public void testRegisterPixKey() throws Exception {
        PixKeyRequest pixKeyRequest = new PixKeyRequest("test@test.com", PixKeyType.EMAIL);
        PixKey pixKey = new PixKey(1L, "test@test.com", PixKeyType.EMAIL, null);

        when(pixKeyService.registerKey(anyLong(), any(PixKeyRequest.class))).thenReturn(pixKey);

        mockMvc.perform(post("/wallets/1/pix-keys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pixKeyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.key").value("test@test.com"));
    }

    @Test
    public void testGetBalance() throws Exception {
        when(balanceUseCase.getBalance(1L)).thenReturn(new BigDecimal("100.00"));

        mockMvc.perform(get("/wallets/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    public void testGetBalanceAt() throws Exception {
        LocalDateTime at = LocalDateTime.now();
        when(balanceHistoryUseCase.getBalanceAt(1L, at)).thenReturn(new BigDecimal("50.00"));

        mockMvc.perform(get("/wallets/1/balance").param("at", at.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(50.00));
    }

    @Test
    public void testDeposit() throws Exception {
        DepositRequest depositRequest = new DepositRequest(new BigDecimal("50.00"));

        mockMvc.perform(post("/wallets/1/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithdraw() throws Exception {
        TransferRequest.AmountRequest amountRequest = new TransferRequest.AmountRequest(new BigDecimal("50.00"));

        mockMvc.perform(post("/wallets/1/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amountRequest)))
                .andExpect(status().isOk());
    }
}

