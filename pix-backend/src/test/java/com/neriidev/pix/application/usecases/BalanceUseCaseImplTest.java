
package com.neriidev.pix.application.usecases;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceUseCaseImplTest {

    @Mock
    private WalletRepositoryPort walletRepository;

    @InjectMocks
    private BalanceUseCaseImpl balanceUseCase;

    @Test
    public void testGetBalance() {
        Wallet wallet = new Wallet(1L, new BigDecimal("100.00"));
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        BigDecimal balance = balanceUseCase.getBalance(1L);

        assertEquals(new BigDecimal("100.00"), balance);
        verify(walletRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBalanceWalletNotFound() {
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            balanceUseCase.getBalance(1L);
        });

        verify(walletRepository, times(1)).findById(1L);
    }
}
