package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.*;
import com.neriidev.pix.domain.model.*;
import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InternalTransferUseCaseImplTest {

    @Mock
    private IdempotencyKeyRepositoryPort idempotencyKeyRepository;

    @Mock
    private PixKeyRepositoryPort pixKeyRepository;

    @Mock
    private WalletRepositoryPort walletRepository;

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @Mock
    private LedgerRepositoryPort ledgerRepository;

    @InjectMocks
    private InternalTransferUseCaseImpl internalTransferUseCase;

    @Test
    public void testInternalTransfer() {
        Wallet fromWallet = new Wallet(1L, new BigDecimal("100.00"));
        Wallet toWallet = new Wallet(2L, new BigDecimal("100.00"));

        PixKey fromPixKey = new PixKey(1L, "fromKey", PixKeyType.EMAIL, fromWallet);

        PixKey toPixKey = new PixKey(2L, "toKey", PixKeyType.EMAIL, toWallet);

        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.empty());
        when(pixKeyRepository.findByKey("fromKey")).thenReturn(Optional.of(fromPixKey));
        when(pixKeyRepository.findByKey("toKey")).thenReturn(Optional.of(toPixKey));

        Transaction transaction = internalTransferUseCase.internalTransfer("idem-key", "fromKey", "toKey", new BigDecimal("50.00"));

        assertNotNull(transaction);
        verify(walletRepository, times(2)).save(any(Wallet.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(ledgerRepository, times(2)).save(any(Ledger.class));
        verify(idempotencyKeyRepository, times(1)).save(any(IdempotencyKey.class));
    }

    @Test
    public void testInternalTransferIdempotencyKeyExists() {
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.of(new IdempotencyKey("idem-key", "internal-transfer", LocalDateTime.now(), "response")));

        assertThrows(RuntimeException.class, () -> {
            internalTransferUseCase.internalTransfer("idem-key", "fromKey", "toKey", new BigDecimal("50.00"));
        });
    }

    @Test
    public void testInternalTransferFromPixKeyNotFound() {
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.empty());
        when(pixKeyRepository.findByKey("fromKey")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            internalTransferUseCase.internalTransfer("idem-key", "fromKey", "toKey", new BigDecimal("50.00"));
        });
    }

    @Test
    public void testInternalTransferToPixKeyNotFound() {
        when(idempotencyKeyRepository.findByKeyAndScope(anyString(), anyString())).thenReturn(Optional.empty());
        when(pixKeyRepository.findByKey("fromKey")).thenReturn(Optional.of(new PixKey(1L, "fromKey", PixKeyType.EMAIL, new Wallet(1L, new BigDecimal("100.00")))));
        when(pixKeyRepository.findByKey("toKey")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            internalTransferUseCase.internalTransfer("idem-key", "fromKey", "toKey", new BigDecimal("50.00"));
        });
    }
}
