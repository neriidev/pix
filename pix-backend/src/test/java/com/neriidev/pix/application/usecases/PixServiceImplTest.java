package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.IdempotencyKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.IdempotencyKey;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PixServiceImplTest {

    @Mock
    private PixKeyRepositoryPort pixKeyRepository;

    @Mock
    private WalletRepositoryPort walletRepository;

    @Mock
    private IdempotencyKeyRepositoryPort idempotencyKeyRepository;

    @InjectMocks
    private PixServiceImpl pixService;

    @Test
    public void testInternalTransfer() {
        TransferRequest request = new TransferRequest("source-key", "target-key", new BigDecimal("100.00"));
        String idempotencyKey = "test-key";

        Wallet sourceWallet = new Wallet(1L, new BigDecimal("200.00"));
        PixKey sourcePixKey = new PixKey(1L, "source-key", PixKeyType.EMAIL, sourceWallet);

        Wallet targetWallet = new Wallet(2L, new BigDecimal("50.00"));
        PixKey targetPixKey = new PixKey(2L, "target-key", PixKeyType.EMAIL, targetWallet);

        when(idempotencyKeyRepository.findByKeyAndScope(idempotencyKey, "internal-transfer")).thenReturn(Optional.empty());
        when(pixKeyRepository.findByKey("source-key")).thenReturn(Optional.of(sourcePixKey));
        when(pixKeyRepository.findByKey("target-key")).thenReturn(Optional.of(targetPixKey));

        pixService.internalTransfer(idempotencyKey, request);

        verify(walletRepository, times(1)).save(sourceWallet);
        verify(walletRepository, times(1)).save(targetWallet);
    }

    @Test
    public void testInternalTransferIdempotencyKeyUsed() {
        TransferRequest request = new TransferRequest("source-key", "target-key", new BigDecimal("100.00"));
        String idempotencyKey = "test-key";

        when(idempotencyKeyRepository.findByKeyAndScope(idempotencyKey, "internal-transfer")).thenReturn(Optional.of(new IdempotencyKey("test-key", "internal-transfer", LocalDateTime.now(), "response")));

        assertThrows(RuntimeException.class, () -> pixService.internalTransfer(idempotencyKey, request));
    }

    @Test
    public void testInternalTransferSourcePixKeyNotFound() {
        TransferRequest request = new TransferRequest("source-key", "target-key", new BigDecimal("100.00"));
        String idempotencyKey = "test-key";

        when(idempotencyKeyRepository.findByKeyAndScope(idempotencyKey, "internal-transfer")).thenReturn(Optional.empty());
        when(pixKeyRepository.findByKey("source-key")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pixService.internalTransfer(idempotencyKey, request));
    }

    @Test
    public void testInternalTransferTargetPixKeyNotFound() {
        TransferRequest request = new TransferRequest("source-key", "target-key", new BigDecimal("100.00"));
        String idempotencyKey = "test-key";

        PixKey sourcePixKey = new PixKey(1L, "source-key", PixKeyType.EMAIL, new Wallet(1L, new BigDecimal("200.00")));

        when(idempotencyKeyRepository.findByKeyAndScope(idempotencyKey, "internal-transfer")).thenReturn(Optional.empty());
        when(pixKeyRepository.findByKey("source-key")).thenReturn(Optional.of(sourcePixKey));
        when(pixKeyRepository.findByKey("target-key")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pixService.internalTransfer(idempotencyKey, request));
    }
}
