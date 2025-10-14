package com.neriidev.pix.infrastructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neriidev.pix.application.ports.in.InternalTransferUseCase;
import com.neriidev.pix.application.ports.in.PixUseCase;
import com.neriidev.pix.application.ports.in.WebhookUseCase;
import com.neriidev.pix.domain.enums.TransactionStatus;
import com.neriidev.pix.domain.model.Transaction;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.InternalTransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WebhookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PixController.class)
public class PixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PixUseCase pixService;

    @MockBean
    private InternalTransferUseCase internalTransferUseCase;

    @MockBean
    private WebhookUseCase webhookUseCase;

    @Test
    public void testInternalTransfer() throws Exception {
        InternalTransferRequest transferRequest = new InternalTransferRequest("source@test.com", "target@test.com", new BigDecimal("200.00"));
        Transaction transaction = new Transaction(1L, "e2e-id", new Wallet(1L, new BigDecimal("100.00")), new Wallet(2L, new BigDecimal("100.00")), new BigDecimal("200.00"), TransactionStatus.PENDING, LocalDateTime.now());

        when(internalTransferUseCase.internalTransfer(anyString(), anyString(), anyString(), any(BigDecimal.class))).thenReturn(transaction);

        mockMvc.perform(post("/pix/transfers")
                .header("Idempotency-Key", "some-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.endToEndId").value("e2e-id"));
    }

    @Test
    public void testWebhook() throws Exception {
        WebhookRequest webhookRequest = new WebhookRequest();
        webhookRequest.setEventId("event-id");
        webhookRequest.setEndToEndId("e2e-id");
        webhookRequest.setStatus("CONFIRMED");

        mockMvc.perform(post("/pix/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(webhookRequest)))
                .andExpect(status().isOk());
    }
}
