
package com.neriidev.pix.application.usecases;

import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    public void testCreateWallet() {
        WalletRequest walletRequest = new WalletRequest(null, new BigDecimal("100.00"));
        WalletEntity walletEntity = new WalletEntity(1L, new BigDecimal("100.00"));

        when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        WalletEntity createdWallet = walletService.create(walletRequest);

        assertNotNull(createdWallet);
        assertEquals(1L, createdWallet.getId());
        assertEquals(new BigDecimal("100.00"), createdWallet.getBalance());
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
    }

    @Test
    public void testGetBalance() {
        WalletEntity walletEntity = new WalletEntity(1L, new BigDecimal("100.00"));
        when(walletRepository.findById(1L)).thenReturn(Optional.of(walletEntity));

        BalanceResponse balanceResponse = walletService.getBalance(1L, LocalDateTime.now());

        assertNotNull(balanceResponse);
        assertEquals(new BigDecimal("100.00"), balanceResponse.balance());
        verify(walletRepository, times(1)).findById(1L);
    }

    @Test
    public void testWithdraw() {
        WalletEntity walletEntity = new WalletEntity(1L, new BigDecimal("100.00"));
        TransferRequest.AmountRequest amountRequest = new TransferRequest.AmountRequest(new BigDecimal("50.00"));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(walletEntity));

        walletService.withdraw(1L, amountRequest);

        verify(walletRepository, times(1)).findById(1L);
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
    }
}
