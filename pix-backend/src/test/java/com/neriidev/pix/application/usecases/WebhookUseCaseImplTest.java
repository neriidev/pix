package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.IdempotencyKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.TransactionRepositoryPort;
import com.neriidev.pix.domain.enums.TransactionStatus;
import com.neriidev.pix.domain.model.IdempotencyKey;
import com.neriidev.pix.domain.model.Transaction;
import com.neriidev.pix.domain.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WebhookUseCaseImplTest {

    @Mock
    private IdempotencyKeyRepositoryPort idempotencyKeyRepository;

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @InjectMocks
    private WebhookUseCaseImpl webhookUseCase;

    @Test
    public void testProcessWebhookConfirmed() {
        Transaction transaction = new Transaction(1L, "e2e-id", new Wallet(1L, new BigDecimal("100.00")), new Wallet(2L, new BigDecimal("100.00")), new BigDecimal("50.00"), TransactionStatus.PENDING, LocalDateTime.now());
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.empty());
        when(transactionRepository.findByEndToEndId(anyString())).thenReturn(Optional.of(transaction));

        webhookUseCase.processWebhook("event-id", "e2e-id", "CONFIRMED");

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(idempotencyKeyRepository, times(1)).save(any(IdempotencyKey.class));
    }

    @Test
    public void testProcessWebhookRejected() {
        Transaction transaction = new Transaction(1L, "e2e-id", new Wallet(1L, new BigDecimal("100.00")), new Wallet(2L, new BigDecimal("100.00")), new BigDecimal("50.00"), TransactionStatus.PENDING, LocalDateTime.now());
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.empty());
        when(transactionRepository.findByEndToEndId(anyString())).thenReturn(Optional.of(transaction));

        webhookUseCase.processWebhook("event-id", "e2e-id", "REJECTED");

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(idempotencyKeyRepository, times(1)).save(any(IdempotencyKey.class));
    }

    @Test
    public void testProcessWebhookEventAlreadyProcessed() {
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.of(new IdempotencyKey("event-id", "webhook", LocalDateTime.now(), "response")));

        assertThrows(RuntimeException.class, () -> {
            webhookUseCase.processWebhook("event-id", "e2e-id", "CONFIRMED");
        });
    }

    @Test
    public void testProcessWebhookTransactionNotFound() {
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.empty());
        when(transactionRepository.findByEndToEndId(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            webhookUseCase.processWebhook("event-id", "e2e-id", "CONFIRMED");
        });
    }
}
