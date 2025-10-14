package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.WebhookUseCase;
import com.neriidev.pix.application.ports.out.IdempotencyKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.TransactionRepositoryPort;
import com.neriidev.pix.domain.model.IdempotencyKey;
import com.neriidev.pix.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WebhookUseCaseImpl implements WebhookUseCase {

    private final IdempotencyKeyRepositoryPort idempotencyKeyRepository;
    private final TransactionRepositoryPort transactionRepository;

    public WebhookUseCaseImpl(IdempotencyKeyRepositoryPort idempotencyKeyRepository, TransactionRepositoryPort transactionRepository) {
        this.idempotencyKeyRepository = idempotencyKeyRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void processWebhook(String eventId, String endToEndId, String status) {
        idempotencyKeyRepository.findByKeyAndScope(eventId, "webhook").ifPresent(key -> {
            throw new RuntimeException("Event already processed");
        });

        Transaction transaction = transactionRepository.findByEndToEndId(endToEndId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (status.equals("CONFIRMED")) {
            transaction.confirm();
        } else if (status.equals("REJECTED")) {
            transaction.reject();
        }

        transactionRepository.save(transaction);
        idempotencyKeyRepository.save(new IdempotencyKey(eventId, "webhook", LocalDateTime.now(), null));
    }
}
