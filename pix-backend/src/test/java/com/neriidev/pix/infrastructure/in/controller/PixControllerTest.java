package com.neriidev.pix.infrastructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neriidev.pix.application.ports.in.PixUseCase;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PixController.class)
public class PixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PixUseCase pixService;

    @Test
    public void testInternalTransfer() throws Exception {
        TransferRequest transferRequest = new TransferRequest("source@test.com", "target@test.com", new BigDecimal("200.00"));

        mockMvc.perform(post("/pix/transfers")
                .header("Idempotency-Key", "some-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk());
    }
}
