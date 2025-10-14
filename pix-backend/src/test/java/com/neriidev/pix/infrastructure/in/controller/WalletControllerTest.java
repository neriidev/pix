package com.neriidev.pix.infrastructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.domain.service.PixKeyService;
import com.neriidev.pix.domain.service.WalletService;
import com.neriidev.pix.infrastructure.in.dto.request.AmountRequest;
import com.neriidev.pix.infrastructure.in.dto.response.BalanceResponse;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
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
    private WalletService walletService;

    @MockBean
    private PixKeyService pixKeyService;

    @Test
    public void testCreateWallet() throws Exception {
        WalletRequest walletRequest = new WalletRequest(null, new BigDecimal("100.00"));
        Wallet wallet = new Wallet(1L, new BigDecimal("100.00"));

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
        PixKeyRequest pixKeyRequest = new PixKeyRequest("test@test.com", PixKey.PixKeyType.EMAIL);
        PixKey pixKey = new PixKey(1L, "test@test.com", PixKey.PixKeyType.EMAIL, null);

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
        BalanceResponse balanceResponse = new BalanceResponse(new BigDecimal("100.00"), LocalDateTime.now());

        when(walletService.getBalance(anyLong(), any())).thenReturn(balanceResponse);

        mockMvc.perform(get("/wallets/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    public void testWithdraw() throws Exception {
        AmountRequest amountRequest = new AmountRequest(new BigDecimal("50.00"));

        mockMvc.perform(post("/wallets/1/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amountRequest)))
                .andExpect(status().isOk());
    }
}
