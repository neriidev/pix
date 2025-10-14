
package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.LedgerRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepositUseCaseImplTest {

    @Mock
    private WalletRepositoryPort walletRepository;

    @Mock
    private LedgerRepositoryPort ledgerRepository;

    @InjectMocks
    private DepositUseCaseImpl depositUseCase;

    @Test
    public void testDeposit() {
        Wallet wallet = new Wallet(1L, new BigDecimal("100.00"));
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        depositUseCase.deposit(1L, new BigDecimal("50.00"));

        verify(walletRepository, times(1)).findById(1L);
        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(ledgerRepository, times(1)).save(any());

        assertEquals(new BigDecimal("150.00"), wallet.getBalance());
    }

    @Test
    public void testDepositWalletNotFound() {
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            depositUseCase.deposit(1L, new BigDecimal("50.00"));
        });

        verify(walletRepository, times(1)).findById(1L);
        verify(walletRepository, never()).save(any());
        verify(ledgerRepository, never()).save(any());
    }
}
